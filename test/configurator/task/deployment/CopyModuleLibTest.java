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

import java.io.File;

import org.easymock.EasyMock;


/**
 * @author Justin Tomich
 */
public class CopyModuleLibTest extends TestCase
{
    public void testConfigure()
    {
        final File jar = new File("/project/foo.jar");
        final String archivePath = "/WEB-INF/lib/foo.jar";

        CopyModuleLib lib = new CopyModuleLib();
        lib.setJar(jar);
        lib.setArchivePath(archivePath);

        DeploymentComponent component =
                EasyMock.createMock(DeploymentComponent.class);
        component.copyModuleLibrary(jar, archivePath, false);

        EasyMock.replay(component);
        lib.configure(component);
        EasyMock.verify(component);
    }
}
