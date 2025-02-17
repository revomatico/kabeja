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

import java.io.File;


/**
 *
 * @author <a href="mailto:simon.mieth@gmx.de">Simon Mieth </a>
 *
 *
 * @deprecated
 */
public class UniqueFile {
    File file;

    /**
     *
     *
     *
     */
    public UniqueFile() {
        file = new File("");
    }

    public UniqueFile(File f) {
        this.file = f;
    }

    public String toString() {
        return file.getName();
    }

    public String getSuffix() {
        return null;
    }

    /**
     *
     * @return
     */
    public File getFile() {
        return file;
    }

    /**
     *
     * @param file
     *
     */
    public void setFile(File file) {
        this.file = file;
    }
}
