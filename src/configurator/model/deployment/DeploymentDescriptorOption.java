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
import configurator.util.ElementCreator;

import org.w3c.dom.Element;
import org.w3c.dom.Document;


/**
 * An option in an "unknown" deployment descriptor.
 * <p/>
 * <option name="DEFAULT_DIR" value="WEB-INF"/>
 *
 * @author Justin Tomich
 */
public class DeploymentDescriptorOption implements XmlElement
{
    public enum Name
    {
        DEFAULT_DIR, NAME, DESCRIPTION
    }

    private Name name;
    private String value;


    public DeploymentDescriptorOption(Name name, String value)
    {
        this.name = name;
        this.value = value;
    }


    public void addToDom(Element parent)
    {
//        Document dom = parent.getOwnerDocument();
//        Element option = dom.createElement("option");
//        parent.appendChild(option);

        Element option = new ElementCreator("option", parent).create();
        option.setAttribute("name", name.toString());
        option.setAttribute("value", value);
    }
}
