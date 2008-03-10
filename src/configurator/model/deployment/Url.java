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
import configurator.model.url.JarUrl;
import configurator.util.ElementCreator;

import org.w3c.dom.Element;
import org.w3c.dom.Document;


/**
 * <url>jar://$APPLICATION_HOME_DIR$/lib/j2ee.jar!/</url>
 *
 * @author Justin Tomich
 */
public class Url implements XmlElement
{
    private final JarUrl jar;


    public Url(JarUrl url)
    {
        this.jar = url;
    }


    public void addToDom(Element parent)
    {
//        Document dom = parent.getOwnerDocument();
//        Element url = dom.createElement("url");
//        parent.appendChild(url);

        Element url = new ElementCreator("url", parent).create();
        jar.setText(url);
    }


    public String toString()
    {
        return "Url{" +
               "jar=" + jar +
               '}';
    }
}
