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

import configurator.model.deployment.DeploymentComponent;

import junit.framework.TestCase;
import org.easymock.EasyMock;

import java.io.File;


/**
 * @author Justin Tomich
 */
public class AdditionalResourceTest extends TestCase
{
    public void testConfigure()
    {
        File file = new File("foo");
        String archivePath = "/bar";

        DeploymentComponent component =
                EasyMock.createMock(DeploymentComponent.class);
        component.addDescriptorUnknown(file, archivePath);

        EasyMock.replay(component);

        AdditionalResource r1 = new AdditionalResource();
        r1.setFile(file);
        r1.setArchivePath(archivePath);

        r1.configure(component);

        EasyMock.verify(component);
    }


    public void testEqualsSameFileDifferentArchivePath()
    {
        AdditionalResource r1 = new AdditionalResource();
        r1.setFile(new File("foo"));
        r1.setArchivePath("/bar");

        AdditionalResource r2 = new AdditionalResource();
        r2.setFile(new File("foo"));
        r2.setArchivePath("/baz");

        assertEquals(r1, r2);
        assertEquals(r2, r1);
    }


    public void testEqualsDifferentFileSameArchivePath()
    {
        AdditionalResource r1 = new AdditionalResource();
        r1.setFile(new File("foo"));
        r1.setArchivePath("/bar");

        AdditionalResource r2 = new AdditionalResource();
        r2.setFile(new File("baz"));
        r2.setArchivePath("/bar");

        assertFalse(r1.equals(r2));
        assertFalse(r2.equals(r1));
    }


    public void testDuplicate()
    {
        AdditionalResource r1 = new AdditionalResource();
        r1.setFile(new File("foo"));
        r1.setArchivePath("/bar");

        AdditionalResource r2 = new AdditionalResource();
        r2.setFile(new File("foo"));
        r2.setArchivePath("/baz");

        WebDeploymentTask dep = new WebDeploymentTask(null);
        dep.add(r1);
        try
        {
            dep.add(r2);
            fail("should have horked on duplicate AdditionalResource!");
        }
        catch (RuntimeException e)
        {
            // this is good
        }
    }
}
