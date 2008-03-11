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
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.Path;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Justin Tomich
 */
public class DependenciesTask extends Task
{
    private final List<Dependency> dependencies =
            new ArrayList<Dependency>();

    private String sourceProperty = null; // default value
    private String javadocProperty = null; // default value
    private String javadocUrlProperty = null; // default value


    public DependenciesTask()
    {
    }


    public List<Dependency> getDependencies()
    {
        return dependencies;
    }


    protected void setSourceProperty(String sourceProperty)
    {
        this.sourceProperty = sourceProperty;
    }


    protected void setJavadocProperty(String javadocProperty)
    {
        this.javadocProperty = javadocProperty;
    }


    protected void setJavadocUrlProperty(String javadocUrlProperty)
    {
        this.javadocUrlProperty = javadocUrlProperty;
    }


    public IdeaLibrary createIdeaLib()
    {
        IdeaLibrary lib = new IdeaLibrary(getProject());
        dependencies.add(lib);
        return lib;
    }


    public ModuleLibrary createModuleLibrary()
    {
        ModuleLibrary lib = new ModuleLibrary(getProject());
        dependencies.add(lib);
        return lib;
    }


    public ProjectLibrary createProjectLibrary()
    {
        ProjectLibrary lib = new ProjectLibrary();
        dependencies.add(lib);
        return lib;
    }


    public GlobalLibrary createGlobalLibrary()
    {
        GlobalLibrary lib = new GlobalLibrary();
        dependencies.add(lib);
        return lib;
    }


    public Jdk createJdk()
    {
        Jdk jdk = new Jdk();
        dependencies.add(jdk);
        return jdk;
    }


    public ModuleSource createModuleSource()
    {
        ModuleSource source = new ModuleSource();
        dependencies.add(source);
        return source;
    }


    public Module createModule()
    {
        Module module = new Module();
        dependencies.add(module);
        return module;
    }


    public Path createClasspath()
    {
        Classpath cp = new Classpath(getProject());
        dependencies.add(cp);
        return cp;
    }


    public void configure(NewModuleRootManagerComponent java)
    {
        for (Dependency entry : dependencies)
        {
            entry.configure(java);
        }
    }


    /**
     * Thin little inner class is a wrapped around the ModuleLibraryClasspath.
     * It provides access to sourceProperty, javadocProperty, and
     * javadocUrlProperty. Those properties won't be set at the time the
     * Classpath is constructed, so we access them when
     * addTo(ModuleBuilder) is called.
     */
    private class Classpath extends Path implements Dependency
    {
        public Classpath(Project project)
        {
            super(project);
            if (project == null) throw new BuildException("null project!");
        }


        public void configure(NewModuleRootManagerComponent java)
        {
            ModuleLibraryClasspath p = new ModuleLibraryClasspath(getProject(),
                    sourceProperty, javadocProperty, javadocUrlProperty);
            p.append(this);
            p.configure(java);
        }
    }


    public String toString()
    {
        return "DependenciesTask{" +
               "dependencies=" + dependencies +
               ", sourceProperty='" + sourceProperty + '\'' +
               ", javadocProperty='" + javadocProperty + '\'' +
               ", javadocUrlProperty='" + javadocUrlProperty + '\'' +
               '}';
    }
}
