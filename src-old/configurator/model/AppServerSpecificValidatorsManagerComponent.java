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

package configurator.model;

import configurator.util.ElementCreator;

import org.w3c.dom.Element;
import org.w3c.dom.Document;


/**
 * @author Justin Tomich
 */
public class AppServerSpecificValidatorsManagerComponent implements Component
{
    public void addToDom(Element parent)
    {
//        Document dom = parent.getOwnerDocument();
//        Element e = dom.createElement("component");
//        parent.appendChild(e);
        
        Element e = new ElementCreator("component", parent).create();
        e.setAttribute("name", "AppServerSpecificValidatorsManager");
    }
}