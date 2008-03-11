/**
 IDEA Configurator
 Copyright (C) 2006 Justin Tomich<tomichj at gmail dot com>

 This program is free software; you can redistribute it and/or
 modify it under the terms of the GNU General Public License as
 published by the Free Software Foundation; either version 2 of the
 License, or (at your option) any later version.

 This program is distributed in the hope that it will be useful, but
 WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package configurator.model.rootmgr;

import configurator.model.XmlElement;
import configurator.model.url.FileUrl;
import configurator.model.url.Url;
import configurator.util.ElementCreator;

import org.w3c.dom.Element;
import org.w3c.dom.Document;


/**
 * Base behavior for SourceFolder and TestFolder.
 *
 * @author Justin Tomich
 */
public abstract class BaseSource implements XmlElement
{
    // tag name extracted to make it accessable from tests...
    static final String ELEMENT_TAG = "sourceFolder";

    protected final FileUrl fileUrl;
    protected final boolean isTest;


    protected BaseSource(FileUrl file, boolean test)
    {
        this.fileUrl = file;
        isTest = test;
    }


    public Url getFileUrl()
    {
        return fileUrl;
    }


    public boolean isTest()
    {
        return isTest;
    }


    public void addToDom(Element parent)
    {
//        Document dom = parent.getOwnerDocument();
//        Element source = dom.createElement(ELEMENT_TAG);
//        parent.appendChild(source);

        Element source = new ElementCreator(ELEMENT_TAG, parent).create();
        fileUrl.setAttribute(source);
        source.setAttribute("isTestSource", String.valueOf(isTest));
    }


    public String toString()
    {
        return "BaseSource{" +
               "file=" + fileUrl +
               ", isTest=" + isTest +
               "}";
    }
}
