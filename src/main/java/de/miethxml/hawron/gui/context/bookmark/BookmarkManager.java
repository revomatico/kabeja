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
package de.miethxml.hawron.gui.context.bookmark;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;


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
public class BookmarkManager {
    private ArrayList bookmarks;
    private BookmarkListModel listModel;
    private String baseURL = "";
    private boolean changed = false;

    /**
     *
     *
     *
     */
    public BookmarkManager() {
        super();
        bookmarks = new ArrayList();
        listModel = new BookmarkListModel();
        baseURL = "";
    }

    public void addBookmark(Bookmark m) {
        bookmarks.add(m);
        changed = true;
        listModel.fireListUpdate();
    }

    public Bookmark getBookmark(int index) {
        return (Bookmark) bookmarks.get(index);
    }

    public void removeBookmark(int index) {
        bookmarks.remove(index);
        changed = true;
        listModel.fireListUpdate();
    }

    public void removeAllBookmarks() {
        bookmarks.clear();
        listModel.fireListUpdate();
    }

    public int getBookmarkCount() {
        return bookmarks.size();
    }

    /**
     *
     * Store the bookmarks to the given file, with "name=src" on each line.
     *
     * @param file
     *
     */
    public void store(String file) {
        //save only changed data
        if (changed) {

            try {
                BufferedWriter out = new BufferedWriter(new FileWriter(file));
                Iterator i = bookmarks.iterator();
  
                while (i.hasNext()) {

                    Bookmark bookmark = (Bookmark) i.next();
                    out.write(bookmark.getName() + "="
                        + getRelativePath(bookmark.getSource()) + "\n");
   
                }

                out.flush();
                out.close();
                out = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
            changed=false;
        }
    }

    /**
     *
     * Load the bookmarks from file with "name=src" on each line.
     *
     * @param file
     *
     */
    public void load(String file) {

        File f = new File(file);
        bookmarks.clear();

        if (f.exists() && f.isFile()) {

            try {
                BufferedReader in = new BufferedReader(new FileReader(f));
                String line = null;

                while ((line = in.readLine()) != null) {
                 	
                    Bookmark bookmark = new Bookmark();
                    String[] data = line.split("=");

                    if (data.length == 2) {
                        bookmark.setName(data[0]);

                        //test if the source is relative
                        File source = new File(data[1]);

                        if (!source.isAbsolute()) {
                            bookmark.setSource(baseURL + File.separator
                                + data[1]);
                        } else {
                            bookmark.setSource(data[1]);
                        }
                        bookmarks.add(bookmark);
                    }
                }

                in.close();
                in = null;
                listModel.fireListUpdate();
                changed = false;
            } catch (FileNotFoundException e) {
                //maybe new project
                return;
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }

            
        }
    }

    /**
     * @return Returns the listModel.
     *
     */
    public ListModel getListModel() {
        return listModel;
    }

    /**
     * @return Returns the baseURL.
     */
    public String getBaseURL() {
        return baseURL;
    }

    /**
     * @param baseURL
     *            The baseURL to set.
     */
    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    private String getRelativePath(String path) {
        if ((baseURL.length() > 0) && path.startsWith(baseURL)) {
        	if(path.length()>baseURL.length()){
            return path.substring(baseURL.length() + 1);
        	}else{
        		return "";
        	}
        }

        return path;
    }

    private class BookmarkListModel implements ListModel {
        private ArrayList listeners = new ArrayList();

        public BookmarkListModel() {
        }

        /*
         *
         * (non-Javadoc)
         *
         * @see javax.swing.ListModel#addListDataListener(javax.swing.event.ListDataListener)
         *
         */
        public void addListDataListener(ListDataListener l) {
            listeners.add(l);
        }

        /*
         *
         * (non-Javadoc)
         *
         * @see javax.swing.ListModel#getElementAt(int)
         *
         */
        public Object getElementAt(int index) {
            Bookmark m = (Bookmark) bookmarks.get(index);

            return m.getName();
        }

        /*
         *
         * (non-Javadoc)
         *
         * @see javax.swing.ListModel#getSize()
         *
         */
        public int getSize() {
            return getBookmarkCount();
        }

        /*
         *
         * (non-Javadoc)
         *
         * @see javax.swing.ListModel#removeListDataListener(javax.swing.event.ListDataListener)
         *
         */
        public void removeListDataListener(ListDataListener l) {
            listeners.remove(l);
        }

        public void fireListUpdate() {
            Iterator i = listeners.iterator();

            while (i.hasNext()) {
                ListDataListener l = (ListDataListener) i.next();
                l.contentsChanged(new ListDataEvent(this,
                        ListDataEvent.CONTENTS_CHANGED, 0, getSize()));
            }
        }
    }
}
