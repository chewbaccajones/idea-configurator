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

import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * Attribute (ironically, an element) is a nested element of ContainerElement.
 * <p/>
 * <p/>
 * <pre>
 * <attribute name="method" value="0" />
 * </pre>
 *
 * @author Justin Tomich
 * @see configurator.model.deployment.ContainerElement
 */
public class Attribute implements XmlElement
{
    public static final String NA_VALUE = "<N/A>";
    public static final Attribute NA_URI = attributeURI(NA_VALUE);

    public static final Attribute DO_NOT_PACKAGE = attributeMethod("0");

    // modules
    public static final Attribute COPY_MODULE_OUTPUT = attributeMethod("1");
    public static final Attribute JAR_MODULE_AND_COPY =
            attributeMethod("5");
    public static final Attribute JAR_MODULE_AND_COPY_AND_LINK_IN_MANIFEST =
            attributeMethod("6");

    // libraries
    public static final Attribute COPY_LIBRARY_FILES = attributeMethod("1");
    public static final Attribute LINK_IN_MANIFEST_AND_COPY_LIBRARY_FILES =
            attributeMethod("2");


    static Attribute attributeMethod(String value)
    {
        return new Attribute(Name.method, value);
    }


    public static Attribute attributeURI(String value)
    {
        return new Attribute(Name.URI, value);
    }


    public static Attribute attributeContextRoot(String value)
    {
        return new Attribute(Name.contextRoot, value);
    }


    public enum Name
    {
        method, URI, contextRoot
    }

    // at last! instance variables!

    private final Name name;
    private final String value;


    public Attribute(Name name, String value)
    {
        this.name = name;
        this.value = value;
    }


    public void addToDom(Element parent)
    {
        Element attribute = new ElementCreator("attribute", parent).create();

//        Document dom = parent.getOwnerDocument();
//        Element attribute = dom.createElement("attribute");
//        parent.appendChild(attribute);

        attribute.setAttribute("name", name.toString());
        attribute.setAttribute("value", value);
    }


    public String toString()
    {
        return "Attribute{" +
               "name=" + name +
               ", value='" + value + '\'' +
               '}';
    }
}
