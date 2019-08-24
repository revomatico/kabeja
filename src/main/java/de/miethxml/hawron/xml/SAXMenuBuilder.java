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
package de.miethxml.hawron.xml;

import java.io.IOException;

import java.util.Stack;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import de.miethxml.toolkit.ui.EditableMenuBar;
import de.miethxml.toolkit.ui.MenuBarManager;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;


/**
 * @author <a href="mailto:simon.mieth@gmx.de">Simon Mieth
 *         </a>.mieth@mieth-xml.de This class will build an JMenuBar, which is
 *
 * described in an XML-file
 *
 *
 *
 * @deprecated move to de.miethxml.toolkit.ui.builder
 *
 */
public class SAXMenuBuilder implements ContentHandler {
    MenuBarManager menubar;
    JMenu menu;
    JMenuItem menuitem;
    StringBuffer content;
    boolean build;
    String parentrole;
    Stack parents;

    /**
     *
     *
     *
     */
    public SAXMenuBuilder() {
        super();
        build = false;
    }

    public void setMenuBar(MenuBarManager mb) {
        this.menubar = mb;
    }

    public MenuBarManager buildMenu(String xmlconfig) {
        if (menubar == null) {
            menubar = new EditableMenuBar();
        }

        build = false;
        parents = new Stack();

        try {
            //			XMLReader saxparser = XMLReaderFactory
            //					.createXMLReader(ConfigManager.getInstance().getProperty(
            //							"SAXParser"));
            XMLReader saxparser = XMLReaderFactory.createXMLReader();
            saxparser.setContentHandler(this);
            saxparser.parse(xmlconfig);
        } catch (SAXException e) {
            System.err.println(e.getMessage());
        } catch (IOException ioe) {
        }

        return menubar;
    }

    /*
     *
     * (non-Javadoc)
     *
     * @see org.xml.sax.ContentHandler#characters(char[], int, int)
     *
     */
    public void characters(
        char[] arg0,
        int arg1,
        int arg2) throws SAXException {
    }

    /*
     *
     * (non-Javadoc)
     *
     * @see org.xml.sax.ContentHandler#endDocument()
     *
     */
    public void endDocument() throws SAXException {
    }

    /*
     *
     * (non-Javadoc)
     *
     * @see org.xml.sax.ContentHandler#endElement(java.lang.String,
     *
     * java.lang.String, java.lang.String)
     *
     */
    public void endElement(
        String arg0,
        String arg1,
        String arg2) throws SAXException {
        if (arg1.equals("menubar")) {
            build = false;
        }

        if (arg1.equals("menu")) {
            parents.pop();
        }
    }

    /*
     *
     * (non-Javadoc)
     *
     * @see org.xml.sax.ContentHandler#endPrefixMapping(java.lang.String)
     *
     */
    public void endPrefixMapping(String arg0) throws SAXException {
    } /*
    *
    * (non-Javadoc)
    *
    * @see org.xml.sax.ContentHandler#ignorableWhitespace(char[], int, int)
    *
    */
    public void ignorableWhitespace(
        char[] arg0,
        int arg1,
        int arg2) throws SAXException {
    }

    /*
     *
     * (non-Javadoc)
     *
     * @see org.xml.sax.ContentHandler#processingInstruction(java.lang.String,
     *
     * java.lang.String)
     *
     */
    public void processingInstruction(
        String arg0,
        String arg1) throws SAXException {
    }

    /*
     *
     * (non-Javadoc)
     *
     * @see org.xml.sax.ContentHandler#setDocumentLocator(org.xml.sax.Locator)
     *
     */
    public void setDocumentLocator(Locator arg0) {
    }

    /*
     *
     * (non-Javadoc)
     *
     * @see org.xml.sax.ContentHandler#skippedEntity(java.lang.String)
     *
     */
    public void skippedEntity(String arg0) throws SAXException {
    }

    /*
     *
     * (non-Javadoc)
     *
     * @see org.xml.sax.ContentHandler#startDocument()
     *
     */
    public void startDocument() throws SAXException {
    }

    /*
     *
     * (non-Javadoc)
     *
     * @see org.xml.sax.ContentHandler#startElement(java.lang.String,
     *
     * java.lang.String, java.lang.String, org.xml.sax.Attributes)
     *
     */
    public void startElement(
        String arg0,
        String arg1,
        String arg2,
        Attributes arg3) throws SAXException {
        if (arg1.equals("menubar")) {
            build = true;
            parents.push("");
        }

        if (build && arg1.equals("menu")) {
            String role = arg3.getValue(arg3.getIndex("role"));
            String icon = arg3.getValue(arg3.getIndex("icon"));

            if ((icon != null) && (icon.length() > 0)) {
                menubar.addMenu((String) parents.peek(), role, icon);
            } else {
                menubar.addMenu((String) parents.peek(), role);
            }

            parents.push(role);
        }

        if (build && arg1.equals("menuitem")) {
            String role = arg3.getValue(arg3.getIndex("role"));
            String icon = arg3.getValue(arg3.getIndex("icon"));

            if ((icon != null) && (icon.length() > 0)) {
                menubar.addMenuItem((String) parents.peek(), role, icon);
            } else {
                menubar.addMenuItem((String) parents.peek(), role);
            }
        }

        if (build && arg1.equals("separator")) {
            menubar.addSeparator((String) parents.peek());
        }
    }

    /*
     *
     * (non-Javadoc)
     *
     * @see org.xml.sax.ContentHandler#startPrefixMapping(java.lang.String,
     *
     * java.lang.String)
     *
     */
    public void startPrefixMapping(
        String arg0,
        String arg1) throws SAXException {
    }
}
