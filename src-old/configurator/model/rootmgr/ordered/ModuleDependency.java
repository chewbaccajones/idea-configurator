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

import configurator.util.ElementCreator;

import org.w3c.dom.Element;
import org.w3c.dom.Document;


/**
 * Indicates this module depends on another IDEA module.
 *
 * @author Justin Tomich
 */
public class ModuleDependency implements OrderEntry
{
    private String moduleName;
    private boolean exported;


    public ModuleDependency(String moduleName, boolean exported)
    {
        this.moduleName = moduleName;
        this.exported = exported;
    }


    public ModuleDependency(String moduleName)
    {
        this.moduleName = moduleName;
        this.exported = false;
    }


    public void addToDom(Element parent)
    {
//        Document dom = parent.getOwnerDocument();
//        Element module = dom.createElement("orderEntry");
//        parent.appendChild(module);

        Element e = new ElementCreator("orderEntry", parent).create();
        e.setAttribute("type", "module");
        e.setAttribute("module-name", moduleName);
        if (exported)
        {
            e.setAttribute("exported", "");
        }
    }


    public String toString()
    {
        return "ModuleDependency{" +
               "moduleName='" + moduleName + "'" +
               ", exported=" + exported +
               "}";
    }
}
