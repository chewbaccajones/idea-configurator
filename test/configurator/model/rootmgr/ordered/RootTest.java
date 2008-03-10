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

package configurator.model.rootmgr.ordered;

import configurator.model.url.FileUrl;
import configurator.model.XmlElementFixture;

import junit.framework.TestCase;


/**
 * @author Justin Tomich
 */
public class RootTest extends TestCase
{
    public void testMatches()
    {
        FileUrl url = new FileUrl("/foo/bar/baz");
        Root root = new Root(url);
        assertTrue(root.matches("/.*bar.*"));
    }


    public void testAddToDom()
    {
        final String path = "/foo/bar/baz";

        // set up our sweet instance of Root to test
        FileUrl url = new FileUrl(path);
        Root root = new Root(url);

        // mock up, setting up extra test expectations
        XmlElementFixture tester = new XmlElementFixture("root");
        tester.subject().setAttribute("url", "file://" + path);
        tester.replay();

        tester.testAddToDom(root);
    }
}
