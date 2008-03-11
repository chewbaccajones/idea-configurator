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

import org.apache.tools.ant.BuildException;


/**
 * @author Justin Tomich
 */
public class IgnoreModule implements Module
{
    private String name;


    public void setName(String name)
    {
        this.name = name;
    }


    public void configure(DeploymentComponent component)
    {
        if (name == null || name.equals(""))
        {
            throw new BuildException("name must not be null");
        }
        component.ignoreModule(name);
    }
}
