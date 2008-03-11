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

package configurator.model.build;

import configurator.model.url.FileUrl;
import configurator.model.XmlElement;
import configurator.util.ElementCreator;

import org.w3c.dom.Element;


/**
 * @author Justin Tomich
 */
public class ExcludedFile implements XmlElement
{
    private final FileUrl file;


    public ExcludedFile(FileUrl file)
    {
        this.file = file;
    }


    public void addToDom(Element parent)
    {
//        Document dom = parent.getOwnerDocument();
//        Element e = dom.createElement("file");
//        // todo adding it to the parent?
        
        Element e = new ElementCreator("file", parent).create();
        file.setAttribute(e);
    }


    public String toString()
    {
        return "ExcludedFile{" +
               "file=" + file +
               '}';
    }
}