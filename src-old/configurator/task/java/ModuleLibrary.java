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

import org.apache.tools.ant.Project;
import org.apache.tools.ant.BuildException;

import java.io.File;


/**
 * Create a normal module library entry.
 * <p/>
 *
 * @author Justin Tomich
 */
public class ModuleLibrary extends BaseModuleLibrary
{
    protected File jar;


    public ModuleLibrary(Project project)
    {
        super(project);
    }


    public void setClasses(File classes)
    {
        this.jar = classes;
    }


    public Classes createClasses()
    {
        return new Classes();
    }


    public class Classes
    {
        public void setFile(File classes)
        {
            if (jar != null) throw new BuildException("Set classes only once.");
            jar = classes;
        }
    }


    public void configure(NewModuleRootManagerComponent java)
    {
        configure(java, jar.getAbsolutePath());
    }

//    public void addTo(NewModuleRootManagerComponent java)
//    {
//        String[] jdUrl = javadocUrls.toArray(new String[0]);
//        java.add(new ModuleLibrary(jar.getAbsolutePath(), array(sources),
//                array(javadocs), jdUrl, exported));
//    }


    public String toString()
    {
        return "ModuleLibrary{" +
               "exported=" + exported +
               ", jar=" + jar +
               ", sources=" + sources +
               ", javadocs=" + javadocs +
               ", javadocUrls=" + javadocUrls +
               '}';
    }
}