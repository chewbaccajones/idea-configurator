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

package configurator.task.java;

import configurator.model.rootmgr.NewModuleRootManagerComponent;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;


/**
 * Create a module library entry for a jar in IDEA's internal lib directory.
 * <p/>
 * Example: <idealibrary name="j2ee.jar"/>
 *
 * @author Justin Tomich
 */
public class IdeaLibrary extends BaseModuleLibrary
{
    protected String name;


    public IdeaLibrary(Project project)
    {
        super(project);
    }


    public void setName(String name)
    {
        if (this.name != null) throw new BuildException("Set name only once.");
        this.name = name;
    }


    public Name createName()
    {
        return new Name();
    }


    public class Name
    {
        public void setName(String libName)
        {
            if (name != null) throw new BuildException("Set name only once.");
            name = libName;
        }
    }


    public void configure(NewModuleRootManagerComponent java)
    {
        configure(java, "$APPLICATION_HOME_DIR$/lib/" + name);
    }

}
