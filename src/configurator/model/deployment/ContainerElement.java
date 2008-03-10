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
 * Contains references to libraries and modules. Set either the name or the
 * url.
 * <p/>
 * <p/>
 * <pre>
 * <containerElement type="library" name="codec-1.2" level="application">
 *   <attribute name="method" value="1" />
 *   <attribute name="URI" value="/WEB-INF/lib" />
 * </containerElement>
 * <p/>
 * <containerElement type="library" level="module">
 *   <attribute name="method" value="1" />
 *   <attribute name="URI" value="/WEB-INF/lib/ant-apache-bcel.jar" />
 *   <url>jar:///lib/apache-ant-1.6.5/lib/ant-apache-bcel.jar!/</url>
 * </containerElement>
 * </pre>
 *
 * @author Justin Tomich
 */
public class ContainerElement implements XmlElement
{
    public enum Type
    {
        library, module
    }


    public enum Level // levels appear only in Type.library container elems
    {
        module, project, application
    }


    private final Type type;
    private final String name; // optional, used with application|project libs
    private final Level level; //optional
    private final List<Attribute> attributes = new ArrayList<Attribute>();
    private Url url; // optional, used with module libraries


    public ContainerElement(Type type, String name)
    {
        this.type = type;
        this.name = name;
        this.level = null;
    }


    public ContainerElement(Type type, Level level)
    {
        this.type = type;
        this.name = null;
        this.level = level;
    }


    public ContainerElement(Type type, Level level, String name)
    {
        this.type = type;
        this.level = level;
        this.name = name;
    }


    public void addAttribute(Attribute attribute)
    {
        attributes.add(attribute);
    }


    public void setUrl(Url url)
    {
        this.url = url;
        assert name == null && type != null && level != null;
    }


    public void addToDom(Element parent)
    {
//        Document dom = parent.getOwnerDocument();
//        Element container = dom.createElement("containerElement");
//        parent.appendChild(container);

        Element container = new ElementCreator("containerElement", parent).create();
        container.setAttribute("type", type.toString());
        if (name != null) container.setAttribute("name", name);
        if (level != null) container.setAttribute("level", level.toString());

        for (Attribute attribute : attributes)
        {
            attribute.addToDom(container);
        }

        if (url != null) url.addToDom(container);

    }


    public String toString()
    {
        return "ContainerElement{" +
               "type=" + type +
               ", name='" + name + '\'' +
               ", level=" + level +
               ", attributes=" + attributes +
               ", url=" + url +
               '}';
    }
}
