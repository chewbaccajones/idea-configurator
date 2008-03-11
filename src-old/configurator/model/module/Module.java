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

package configurator.model.module;

import configurator.model.Component;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


/**
 * Generic innards of a module. Each of the "typed" modules (JavaModule,
 * WebModule, etc) delegates most of their state management to a Module
 * instance.
 *
 * @author Justin Tomich
 */
final class Module
{
    // attributes
    private String name; // used as filename
    private final int version;
    private final String type;
    private final boolean relativePathsOutsideModule;

    // child elements
    private List<Component> components = new ArrayList<Component>();


    Module(String name, String type, int version,
           boolean relativePathsOutsideModule, Component... components)
    {
        this.name = name;
        this.type = type;
        this.version = version;
        this.relativePathsOutsideModule = relativePathsOutsideModule;
        this.components.addAll(Arrays.asList(components));
    }


    void addComponent(Component component)
    {
        components.add(component);
    }


    Document toDom() throws ParserConfigurationException
    {
        // make the dom
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document dom = builder.newDocument();

        // make the root module element
        Element module = dom.createElement("module");
        module.setAttribute("version", String.valueOf(version));
        module.setAttribute("relativePaths",
                String.valueOf(relativePathsOutsideModule));
        module.setAttribute("type", type);
        dom.appendChild(module);

        for (Component component : components)
        {
            component.addToDom(module);
        }

        return dom;
    }


    public String toString()
    {
        return "Module{" +
               "name='" + name + '\'' +
               ", version=" + version +
               ", type='" + type + '\'' +
               ", relativePathsOutsideModule=" + relativePathsOutsideModule +
               ", components=" + components +
               '}';
    }
}
