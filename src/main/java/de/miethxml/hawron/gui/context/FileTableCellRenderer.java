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

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.table.DefaultTableCellRenderer;


/**
 *
 * @author <a href="mailto:simon.mieth@gmx.de">Simon Mieth </a>
 *
 *
 *
 */
public class FileTableCellRenderer extends DefaultTableCellRenderer {
    public static int ICON_SIZE = 16;
    private String imgSrc;
    private Image fileImg;
    private Image dirImg;
    private Image icon;
    private int height;
    private int width;

    /**
     *
     *
     *
     */
    public FileTableCellRenderer() {
        init();
        height = ICON_SIZE;
        width = ICON_SIZE;

        setSize(new Dimension(width, height));
        setPreferredSize(new Dimension(width, height));
    }

    public void init() {
        Thread t = new Thread(new Runnable() {
                    public void run() {
                        fileImg = Toolkit.getDefaultToolkit().getImage("icons/file.gif");

                        dirImg = Toolkit.getDefaultToolkit().getImage("icons/dir.gif");
                    }
                });

        t.start();
    }

    public void setValue(Object value) {
        Boolean b = (Boolean) value;

        if (!b.booleanValue()) {
            icon = dirImg;
        } else {
            icon = fileImg;
        }
    }

    protected void paintComponent(Graphics g) {
        g.drawImage(icon, 0, 0, width, height, null);
    }
}
