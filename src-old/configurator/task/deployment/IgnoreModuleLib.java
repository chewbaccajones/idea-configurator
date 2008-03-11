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

import configurator.util.Validator;
import configurator.model.deployment.DeploymentComponent;

import java.io.File;


/**
 * @author Justin Tomich
 */
public class IgnoreModuleLib implements Library
{
    private File jar;


    public void setJar(File jar)
    {
        this.jar = jar;
    }


    public void configure(DeploymentComponent component)
    {
        validate();
        component.ignoreModuleLibrary(jar);
    }


    private void validate()
    {
        Validator.notEmpty(jar, "jar");
    }
}
