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

package configurator.model.rootmgr.ordered;

import configurator.model.XmlElement;
import configurator.model.url.Url;
import configurator.util.ElementCreator;

import org.w3c.dom.Element;
import org.w3c.dom.Document;


/**
 * A root element wraps a path. A root points to classes (jar or dir), javadoc,
 * or source elements for a library.
 *
 * @author Justin Tomich
 */
public class Root implements XmlElement
{
    private Url url;


    public Root(Url url)
    {
        this.url = url;
    }


    public void addToDom(Element parent)
    {
//        Document dom = parent.getOwnerDocument();
//        Element root = dom.createElement("root");
//        parent.appendChild(root);

        Element root = new ElementCreator("root", parent).create();
        url.setAttribute(root);
    }


    /**
     * Does the Root's url contain the supplied path fragment?
     *
     * @param path candidate path that this Root may contain.
     * @return true if the root contains the supplied path
     * @see String#contains(CharSequence)
     */
    public boolean contains(String path)
    {
        return url.contains(path);
    }


    /**
     * Does the Root's url match the given pattern?
     *
     * @param pattern matches
     * @return true if the url matches the pattern.
     */
    public boolean matches(String pattern)
    {
        return url.matches(pattern);
    }


    public String toString()
    {
        return "Root{" +
               "url=" + url +
               "}";
    }
}
