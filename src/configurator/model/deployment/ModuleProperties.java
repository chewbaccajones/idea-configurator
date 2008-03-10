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

import java.util.ArrayList;
import java.util.List;


/**
 * Basic module deployment implementation. Module-specific implementations all
 * delegate to this puppy.
 *
 * @author Justin Tomich
 */
final class ModuleProperties implements XmlElement
{
    private List<ContainerElement> containers =
            new ArrayList<ContainerElement>();

    private List<DeploymentDescriptor> descriptors =
            new ArrayList<DeploymentDescriptor>();

    private List<Root> roots = new ArrayList<Root>();

    private final String name;


    public ModuleProperties(String name)
    {
        this.name = name;
    }


    public void add(ContainerElement container)
    {
        containers.add(container);
    }


    public void add(DeploymentDescriptor descriptor)
    {
        descriptors.add(descriptor);
    }


    public void add(Root root)
    {
        roots.add(root);
    }


    public void addToDom(Element parent)
    {
//        Document dom = parent.getOwnerDocument();
//        Element properties = dom.createElement("component");
//        parent.appendChild(properties);

        Element properties = new ElementCreator("component", parent).create();
        properties.setAttribute("name", name);

        for (ContainerElement container : containers)
        {
            container.addToDom(properties);
        }

        for (DeploymentDescriptor descriptor : descriptors)
        {
            descriptor.addToDom(properties);
        }


        if (roots.size() > 0)
        {
//            Element webroots = dom.createElement("webroots");
//            properties.appendChild(webroots);

            Element e = new ElementCreator("webroots", properties).create();

            for (Root root : roots)
            {
                root.addToDom(e);
            }

        }

    }
}
