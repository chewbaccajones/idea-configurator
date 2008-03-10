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


/**
 * @author Justin Tomich
 */
public class CopyModuleTest extends TestCase
{
    public void testConfigure()
    {
        String name = "foo-module";
        String path = "/archive/classes";

        CopyModule copy = new CopyModule();
        copy.setName(name);
        copy.setArchivePath(path);

        DeploymentComponent component =
                EasyMock.createMock(DeploymentComponent.class);
        component.copyModuleOutput(name, path);

        EasyMock.replay(component);
        copy.configure(component);
        EasyMock.verify(component);
    }
}
