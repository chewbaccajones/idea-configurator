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

import java.io.File;
import java.io.IOException;


/**
 * @author Justin Tomich
 */
public class UrlFactoryImplTest extends TestCase
{
    public void testRelativePath()
    {
        File home = new File("/vobs/ssp/cci");
        File file1 = new File("/vobs/ssp/cci/main/src");
        File file2 = new File("/vobs/ssp/cci/main/src/core/web/stuff");
        File file3 = new File("/vobs/rws/adobefacade/web");

        UrlFactoryImpl f = new UrlFactoryImpl(home, true);

        assertEquals("main/src", f.relativePath(home, file1));
        assertEquals("main/src/core/web/stuff", f.relativePath(home, file2));
        assertEquals("../../rws/adobefacade/web", f.relativePath(home, file3));
    }


    public void testRelativeOutsideRoot()
    {
        File home = new File("/vobs/ssp/cci");
        String file = "/vobs/rws/adobefacade/web";

        UrlFactoryImpl f = new UrlFactoryImpl(home, false);

        // should be relative... (we're using protected api, always relative)
        String path = f.relativePath(file);
        assertTrue(path.contains(".."));

        // should NOT be relative, constructor specifies no relative paths
        // outside of root directory.
        String computed = f.computePath(file);
        assertFalse(computed.contains(".."));
    }
}
