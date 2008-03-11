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
package configurator.model.rootmgr;

import configurator.model.url.FileUrl;
import configurator.model.rootmgr.TestFolder;
import configurator.model.rootmgr.BaseSource;

import junit.framework.TestCase;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


/**
 * A confounding name... a test for the TestContent class.
 * 
 * @author Justin Tomich
 */
public class TestContentTest extends TestCase
{
    public void testTestContent()
    {
        String testDir = "$MODULE_DIR$/test";
        TestFolder test = new TestFolder(new FileUrl(testDir));
        assertTrue(test.isTest());
    }

    public void testAddToDom() throws ParserConfigurationException
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document dom = builder.newDocument();
        Element module = dom.createElement("module");

        TestFolder test = new TestFolder(new FileUrl("$MODULE_DIR$/test"));
        test.addToDom(module);

        NodeList list = module.getElementsByTagName(BaseSource.ELEMENT_TAG);
        assertNotNull(list);
        assertTrue(list.getLength() > 0);
    }
}
