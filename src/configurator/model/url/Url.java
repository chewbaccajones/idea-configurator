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

package configurator.model.url;

import configurator.model.XmlAttribute;

import org.w3c.dom.Element;


/**
 * Encapsulate a path to a resource that IDEA will be configured to use.
 * <p/>
 * Can tell you if it matches a path, or contains a part of a path. Finally, it
 * is an XmlAttribute, and can set itself as an attribute onto an Element.
 *
 * @author Justin Tomich
 */
public interface Url extends XmlAttribute
{
    boolean matches(String pattern);
    boolean contains(String candidatePath);


    /**
     * Set the url as a 
     * @param e
     */
    void setText(Element e);
}
