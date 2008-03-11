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

package configurator.model.deployment;

import configurator.model.XmlElement;
import configurator.model.url.FileUrl;
import configurator.util.ElementCreator;

import org.w3c.dom.Element;
import org.w3c.dom.Document;


/**
 * A root directory mapping in the archive. Maps a directory in the project to a
 * directory in the web archive.
 * <p/>
 * In the .iml file: <root url="file://$MODULE_DIR$/resources" relative="/" />
 *
 * @author Justin Tomich
 */
public class Root implements XmlElement
{
    private FileUrl url;
    private String relative;


    public Root(FileUrl url, String relative)
    {
        this.url = url;
        this.relative = relative;
    }


    public void addToDom(Element parent)
    {
//        Document dom = parent.getOwnerDocument();
//        Element root = dom.createElement("root");
//        parent.appendChild(root);

        Element root = new ElementCreator("root", parent).create();
        
        url.setAttribute(root);
        root.setAttribute("relative", relative);
    }


    public String toString()
    {
        return "Root{" +
               "url=" + url +
               ", relative='" + relative + '\'' +
               '}';
    }
}
