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
package de.miethxml.hawron.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import de.miethxml.hawron.ApplicationConstants;

import de.miethxml.toolkit.conf.LocaleImpl;
import de.miethxml.toolkit.conf.LocaleListener;


/**
 * @author <a href="mailto:simon.mieth@gmx.de">Simon Mieth </a>
 *
 *
 *
 *
 *
 *
 *
 */
public class AboutAction extends AbstractAction implements LocaleListener {
    /**
     *
     *
     *
     */
    public AboutAction() {
        super(LocaleImpl.getInstance().getString("menu.help.about"),
            new ImageIcon("icons/about.gif"));
        putValue(SHORT_DESCRIPTION,
            LocaleImpl.getInstance().getString("menu.help.about"));
        LocaleImpl.getInstance().addLocaleListener(this);
    }

    /**
     * @param name
     *
     */
    public AboutAction(String name) {
        super(name);
    }

    /**
     * @param name
     *
     * @param icon
     *
     */
    public AboutAction(
        String name,
        Icon icon) {
        super(name, icon);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     *
     */
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null,
            (LocaleImpl.getInstance().getString("common.prolog")).replaceAll(
                "@VERSION@", ApplicationConstants.APPLICATION_VERSION),
            "About", JOptionPane.INFORMATION_MESSAGE);
    }

    /*
     * (non-Javadoc)
     *
     * @see de.miethxml.toolkit.conf.LocaleListener#langChanged()
     *
     */
    public void langChanged() {
        putValue(SHORT_DESCRIPTION,
            LocaleImpl.getInstance().getString("menu.help.about"));
        putValue(NAME, LocaleImpl.getInstance().getString("menu.help.about"));
    }
}
