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

package configurator.task.deployment;

import junit.framework.TestCase;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference;


/**
 * @author Justin Tomich
 */
public class CopyModuleLibPathTest extends TestCase
{
    public void testPathSetters()
    {
        Project p = new Project();

        Path path1 = new Path(p);
        path1.createPathElement().setPath("/foo/bar/path1");

        Path path2 = new Path(p);
        path2.createPathElement().setPath("/foo/bar/path2");
        p.addReference("whatever", path2);
        Reference r2 = new Reference(p, "whatever");

        CopyModuleLibPath cmlp = new CopyModuleLibPath(p);
        cmlp.setPath(path1);
        try
        {
            cmlp.setRefid(r2);
            fail("should explode!");
        }
        catch (Exception e)
        {
            // this is expected
        }
    }


    public void testconfigure()
    {
        
    }
}
