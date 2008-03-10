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

import junit.framework.TestCase;
import org.w3c.dom.Element;
import org.easymock.EasyMock;


/**
 * @author Justin Tomich
 */
public class HttpUrlTest extends TestCase
{
    public void testNoTrailingSlash()
    {
        HttpUrl u2 = new HttpUrl("http://test.com/foo");
        assertTrue(u2.path.endsWith("/"));
    }

    public void testWithTrailingSlash()
    {
        HttpUrl u2 = new HttpUrl("http://test.com/foo/");
        assertTrue(u2.path.endsWith("/"));
        assertFalse(u2.path.endsWith("//"));
    }

    public void testNonHttpUrl()
    {
        try
        {
            new HttpUrl("test.com/foo");
            fail("Bad url! should have hucked");
        }
        catch(RuntimeException e){}

    }

    public void testAddToDom()
    {
        String path = "http://test.com/foo/bar/";

        Element setting = EasyMock.createMock(Element.class);
        setting.setAttribute("url", path);
        EasyMock.replay(setting);

        HttpUrl h = new HttpUrl(path);
        h.setAttribute(setting);

        EasyMock.verify(setting);
    }
}
