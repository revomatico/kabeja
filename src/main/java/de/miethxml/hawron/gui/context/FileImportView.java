/*
   Copyright 2005 Simon Mieth

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package de.miethxml.hawron.gui.context;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;

import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import com.jgoodies.forms.builder.ButtonBarBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import de.miethxml.toolkit.conf.LocaleImpl;
import de.miethxml.toolkit.conf.LocaleListener;
import de.miethxml.toolkit.gui.LocaleButton;
import de.miethxml.toolkit.gui.LocaleLabel;
import de.miethxml.toolkit.io.FileModel;
import de.miethxml.toolkit.io.IOListener;
import de.miethxml.toolkit.io.Utilities;
import de.miethxml.toolkit.repository.Reloadable;
import de.miethxml.toolkit.repository.RepositoryModel;
import de.miethxml.toolkit.repository.RepositorySelectionListener;

import org.apache.avalon.framework.service.ServiceManager;


/**
 * @author <a href="mailto:simon.mieth@gmx.de">Simon Mieth </a>
 *
 *
 *
 */
public class FileImportView implements IOListener, LocaleListener,
    RepositorySelectionListener {
    private JDialog dialog;
    private ServiceManager manager;
    private List files;
    private Utilities ioUtilities;
    private File to;
    private JProgressBar progressBar;
    private boolean interrupt = false;
    private JLabel count;
    private JLabel step;
    private int fileCount;
    private String currentDirectory;
    private RepositoryModel model;

    public FileImportView(RepositoryModel model) {
        super();
        ioUtilities = new Utilities();
        ioUtilities.addIOListener(this);
        model.addRepositorySelectionListener(this);
        this.model = model;
    }

    public void initialize() {
        dialog = new JDialog();

        //GUI build here
        dialog.getContentPane().setLayout(new BorderLayout());

        FormLayout layout = new FormLayout("3dlu,pref,2dlu,fill:120dlu:grow,3dlu",
                "3dlu,p,2dlu,p,2dlu,p,3dlu");
        PanelBuilder builder = new PanelBuilder(layout);
        CellConstraints cc = new CellConstraints();
        LocaleLabel label = new LocaleLabel("view.fileimport.label.import");
        builder.add(label, cc.xy(2, 2));
        count = new JLabel("");
        builder.add(count, cc.xy(4, 2));
        label = new LocaleLabel("view.fileimport.label.step");
        builder.add(label, cc.xy(2, 4));
        step = new JLabel("");
        builder.add(step, cc.xy(4, 4));
        label = new LocaleLabel("view.fileimport.label.progress");
        builder.add(label, cc.xy(2, 6));
        progressBar = new JProgressBar();
        builder.add(progressBar, cc.xy(4, 6));
        dialog.getContentPane().add(builder.getPanel(), BorderLayout.CENTER);

        JButton[] buttons = new JButton[1];
        buttons[0] = new LocaleButton("common.button.cancel");
        buttons[0].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    interrupt();
                    dialog.setVisible(false);
                }
            });

        ButtonBarBuilder bbuilder = new ButtonBarBuilder();
        bbuilder.addRelatedGap();
        bbuilder.addGlue();
        bbuilder.addGriddedButtons(buttons);
        dialog.getContentPane().add(bbuilder.getPanel(), BorderLayout.SOUTH);
        dialog.pack();
    }

    public void setImportFileList(List files) {
        this.files = files;
        interrupt = false;
        to = new File(currentDirectory);

        //gui reporting
        count.setText(LocaleImpl.getInstance().getString("view.fileimport.report.prepare"));
        step.setText("");
        progressBar.setValue(0);
        fileCount = 0;

        //do it
        dialog.setVisible(true);
        invokeThread();
    }

    private void invokeThread() {
        Runnable r = new Runnable() {
                public void run() {
                    Iterator i = files.iterator();

                    while (i.hasNext() && !interrupt) {
                        File from = (File) i.next();

                        //not write to self
                        //System.out.println("TO:" + to.getAbsolutePath());
                        //System.out.println("FROM:" + from.getAbsolutePath());
                        ioUtilities.copy(from, to);
                        model.reload();
                    }
                }
            };

        Thread t = new Thread(r);
        t.start();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.miethxml.io.IOListener#completeStoring()
     *
     */
    public void completeWriting() {
        if (fileCount == 0) {
            progressBar.setIndeterminate(false);
        }

        fileCount++;
        progressBar.setValue(fileCount);
    }

    /*
     * (non-Javadoc)
     *
     * @see de.miethxml.io.IOListener#copy(int)
     *
     */
    public void copy(int files) {
        count.setText(files + " "
            + LocaleImpl.getInstance().getString("view.fileimport.report.filecount"));
        progressBar.setMinimum(0);
        progressBar.setMaximum(files);
        progressBar.setIndeterminate(true);
    }

    /*
     * (non-Javadoc)
     *
     * @see de.miethxml.io.IOListener#startStoring(int)
     *
     */
    public void startWriting(
        String name,
        long length) {
        step.setText(LocaleImpl.getInstance().getString("view.fileimport.report.copy")
            + " " + name);
    }

    /*
     * (non-Javadoc)
     *
     * @see de.miethxml.io.IOListener#store(int)
     *
     */
    public void wrote(
        long count,
        long total) {
    }

    /*
     * (non-Javadoc)
     *
     * @see de.miethxml.io.IOListener#complete()
     *
     */
    public void complete() {
        dialog.setVisible(false);
    }

    public void interrupt() {
        interrupt = true;
        ioUtilities.interrupt();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.miethxml.toolkit.conf.LocaleListener#langChanged()
     *
     */
    public void langChanged() {
    }

    public void directorySelected(
        Reloadable model,
        FileModel directory) {
        currentDirectory = directory.getPath();
    }

    public void fileSelected(
        Reloadable model,
        FileModel file) {
        currentDirectory = file.getParent().getPath();
    }

    public void unselect() {
        currentDirectory = null;
    }
}
