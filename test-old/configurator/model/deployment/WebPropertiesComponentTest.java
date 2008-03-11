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

import configurator.model.DomFactory;
import configurator.model.DomSerializer;
import configurator.model.url.UrlFactory;
import configurator.model.url.UrlFactoryImpl;

import junit.framework.TestCase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;


/**
 * @author Justin Tomich
 */
public class WebPropertiesComponentTest extends TestCase
{
    private UrlFactory url()
    {
        return new UrlFactoryImpl(new File("/foo"), true);
    }


    public void testIgnoreModule()
    {
        WebPropertiesComponent webProperties =
                new WebPropertiesComponent(url());
        webProperties.ignoreModule("foo");

        String[] ignored = new String[] {
                "<containerElement name=\"foo\" type=\"module\">",
                "<attribute name=\"method\" value=\"0\"/>",
                "<attribute name=\"URI\" value=\"&lt;N/A&gt;\"/>",
                "</containerElement>"};

        assertContains(webProperties, ignored);
    }


    // todo use xpath validation? something else?
    public void testCopyModuleOutput()
    {
        WebPropertiesComponent webProperties =
                new WebPropertiesComponent(url());
        webProperties.copyModuleOutput("foo", "/WEB-INF/classes");

        String[] ignored = new String[] {
                "<containerElement name=\"foo\" type=\"module\">",
                "<attribute name=\"method\" value=\"1\"/>",
                "<attribute name=\"URI\" value=\"/WEB-INF/classes\"/>",
                "</containerElement>"
        };

        assertContains(webProperties, ignored);
    }


    private void assertContains(WebPropertiesComponent webProperties,
                                String... requrements)
    {
        Document document = squirtIntoDom(webProperties);

        String domStr = DomSerializer.toString(document);
        for (String requirement : requrements)
        {
            assertTrue("should contain: " + requirement + " actual:" + domStr,
                    domStr.contains(requirement));
        }
    }


    private Document squirtIntoDom(WebPropertiesComponent webProperties)
    {
        Document dom = DomFactory.createDom();
        Element parent = dom.createElement("parent");
        dom.appendChild(parent);

        webProperties.addToDom(parent);

        return dom;
    }


}
