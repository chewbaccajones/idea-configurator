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

import org.easymock.EasyMock;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * Supports testing the addToDom(Document,Element) method on XmlElement,
 * ensuring that the XmlElement implementation actually creates the expected
 * Element with the correct attributes.
 * <p/>
 * Creates mock xml DOM, parent element, and a mock "subject" element. The
 * XmlElement impl is expected to ask the "parent" element for it's dom, ask the
 * dom to create an element (the "subject" element), optionally add attributes
 * to the subject, and then add the subject to the parent element.
 *
 * @author Justin Tomich
 */
public class XmlElementFixture
{
    Document dom;
    Element parent;
    Element expectedElement;


    /**
     * Create the test instance. Instantiates a mock dom, a mock parent element,
     * and a mock "subject" element.
     *
     * @param expectedElementName
     */
    public XmlElementFixture(String expectedElementName)
    {
        // an XmlElement impl expects that these already exist
        dom = EasyMock.createMock(Document.class);
        parent = EasyMock.createMock(Element.class);
        expectedElement = EasyMock.createMock(Element.class);

        // constructor includes some free expectations every test will have!
        // yay! free expectations!

        // XmlElement impl will ask the parent for it's owning DOM
        EasyMock.expect(parent.getOwnerDocument()).andReturn(dom);

        // XmlElement impl will create an element, with expectedEelemntName
        EasyMock.expect(dom.createElement(expectedElementName)).
                andReturn(expectedElement);

        // XmlElement impl will add that element to the parent
        EasyMock.expect(parent.appendChild(expectedElement)).andReturn(
                expectedElement);
    }


    public Element subject()
    {
        return expectedElement;
    }


    /**
     * The test should set up any additional expectations it has before calling
     * replay. For example, the test might expect that a particular attribute
     * and value will be set on the subject.
     */
    public void replay()
    {
        EasyMock.replay(dom, parent, expectedElement);
    }


    public void verify()
    {
        EasyMock.verify(dom, parent, expectedElement);
    }


    public void testAddToDom(XmlElement xmlElement)
    {
        // consider adding verify() here
        xmlElement.addToDom(parent);
        verify();
    }
}
