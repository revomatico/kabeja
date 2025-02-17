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
package de.miethxml.hawron.io;


/**
 *
 * @author <a href="mailto:simon.mieth@gmx.de">Simon Mieth </a>
 *
 *
 *
 * To change this generated comment go to
 *
 * @deprecated
 *
 */
public class FTPsite {
    String ID;
    String title;
    String user;
    String password;
    String uri;
    String destDir;

    /**
     *
     *
     *
     */
    public FTPsite() {
        title = "";

        user = "";

        password = "";

        String uri = "";

        destDir = "";

        ID = "";
    }

    /**
     *
     * @return
     */
    public String getDestDir() {
        return destDir;
    }

    /**
     *
     * @param destDir
     *
     */
    public void setDestDir(String destDir) {
        this.destDir = destDir;
    }

    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     *
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     */
    public String getUri() {
        return uri;
    }

    /**
     *
     * @param uri
     *
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     *
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     */
    public String getUser() {
        return user;
    }

    /**
     *
     * @param user
     *
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     *
     * @return Returns the iD.
     *
     */
    public String getID() {
        return ID;
    }

    /**
     *
     * @param id
     *            The iD to set.
     *
     */
    public void setID(String id) {
        ID = id;
    }
}
