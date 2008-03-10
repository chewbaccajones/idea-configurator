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

import org.w3c.dom.Element;


/**
 * Set an attribute on an xml Element.
 *
 * @author Justin Tomich
 */
public interface XmlAttribute
{
    /**
     * Set an attribute using the default attribute name.
     *
     * @param e eleemnt to set attribute upon
     */
    void setAttribute(Element e);


    /**
     * Set the attribute upon the element, using the given attribute name.
     *
     * @param e eleemnt to set attribute upon
     * @param name give attribute this name
     */
    void setAttribute(Element e, String name);
}
