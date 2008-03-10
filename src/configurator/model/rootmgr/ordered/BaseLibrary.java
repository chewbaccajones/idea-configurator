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

import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * Basic behavior shared by module, project, and global library
 * implementations.
 *
 * @author Justin Tomich
 */
public abstract class BaseLibrary implements OrderEntry
{
    protected String name;
    protected boolean exported;


    protected abstract String level();


    public void addToDom(Element parent)
    {
        Element lib = new ElementCreator("orderEntry", parent).create();

        lib.setAttribute("type", "library");
        if (exported)
        {
            lib.setAttribute("exported", "");
        }
        lib.setAttribute("name", name);
        lib.setAttribute("level", level());

    }
}
