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
import configurator.model.rootmgr.ordered.ModuleLibrary;

import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.Project;

import java.io.File;
import java.util.List;
import java.util.ArrayList;


/**
 * All module libraries extend BaseModuleLibrary.
 *
 * @author Justin Tomich
 */
public abstract class BaseModuleLibrary implements Dependency
{
    protected boolean exported = false;
    protected Path sources;
    protected Path javadocs;
    protected List<String> javadocUrls = new ArrayList<String>();


    public BaseModuleLibrary(Project project)
    {
        sources = new Path(project);
        javadocs = new Path(project);
    }


    public void setExported(boolean exported)
    {
        this.exported = exported;
    }


    public void setSource(File sources)
    {
        this.sources.setLocation(sources);
    }


    public void setJavadoc(File javadoc)
    {
        this.javadocs.setLocation(javadoc);
    }


    public void setJavadocUrl(String javadocUrl)
    {
        javadocUrls.add(javadocUrl);
    }


    public Path createSource()
    {
        return sources.createPath();
    }


    public Path createJavadoc()
    {
        return javadocs.createPath();
    }


    public JavadocUrl createJavadocUrl()
    {
        return new JavadocUrl();
    }


    public class JavadocUrl
    {
        public void setUrl(String url) { javadocUrls.add(url); }
    }


    protected String[] array(Path path)
    {
        if (path == null) return new String[] {};
        if (path.size() == 0) return new String[] {};
        return path.list();
    }

    public abstract void configure(NewModuleRootManagerComponent java);

    protected void configure(NewModuleRootManagerComponent java, String classes)
    {
        String[] jdUrl = javadocUrls.toArray(new String[0]);
        java.add(new ModuleLibrary(classes, array(sources),
                array(javadocs), jdUrl, exported));
    }

}
