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

package configurator.task;

import configurator.model.module.JavaModule;
import configurator.model.rootmgr.NewModuleRootManagerComponent;
import configurator.task.java.JavaTask;
import configurator.task.java.ClasspathFilter;
import configurator.task.java.DependenciesTask;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference;

import java.io.File;
import javax.xml.parsers.ParserConfigurationException;


/**
 * @author Justin Tomich
 */
public class IdeaJavaModuleTask extends Task
{
    private ModuleFile moduleFile;
    private JavaTask javaTask;

    // two ways to associate javadoc and source with library jars
    // * subtask, lib -> javadoc
    // * search 4 property name matching jar name, eg javadoc.ajax-1.1.jar
    // ** optionally specify property prepended to javadoc and source properties

    // delegating the java build configuration to JavaBuild


    /**
     * Set the project. Will be done automagicaly by Ant, before other methods
     * are called. Set up attributes dependent upon the Project here.
     */
    public void setProject(Project project)
    {
        super.setProject(project);

        moduleFile = new ModuleFile(getProject());
        javaTask = new JavaTask(getProject());
    }


    public void setRootDir(File rootDir)
    {
        moduleFile.setRootDir(rootDir);
    }


    public void setModuleFile(File moduleFile)
    {
        this.moduleFile.setModuleFile(moduleFile);
    }


    public void setRelativePaths(boolean relativePaths)
    {
        moduleFile.setRelativePaths(relativePaths);
    }


    public void setSourceProperty(String sourceProperty)
    {
        javaTask.setSourceProperty(sourceProperty);
    }


    public void setJavadocProperty(String javadocProperty)
    {
        javaTask.setJavadocProperty(javadocProperty);
    }


    public void setJavadocUrlProperty(String javadocUrlProperty)
    {
        javaTask.setJavadocUrlProperty(javadocUrlProperty);
    }


    public void setSrcDir(File sourceDir)
    {
        javaTask.setSrcDir(sourceDir);
    }


    public void setSrcPathRef(Reference srcPathRef)
    {
        javaTask.setSrcPathRef(srcPathRef);
    }


    public Path createSrc()
    {
        return javaTask.createSrc();
    }


    public void setTestsDir(File testDir)
    {
        javaTask.setTestsDir(testDir);
    }


    public void setTestsPathRef(Reference testsPathRef)
    {
        javaTask.setTestsPathRef(testsPathRef);
    }


    public Path createTests()
    {
        return javaTask.createTests();
    }


    public void setInheritCompilerOutput(boolean inheritCompilerOutput)
            throws BuildException
    {
        javaTask.setInheritCompilerOutput(inheritCompilerOutput);
    }


    public void setExcludeOutputPaths(boolean excludeOutputPaths)
    {
        javaTask.setExcludeOutputPaths(excludeOutputPaths);
    }


    public void setOutputDir(File outputDir)
    {
        javaTask.setOutputDir(outputDir);
    }


    public void setTestsOutputDir(File testsOutputDir)
    {
        javaTask.setTestsOutputDir(testsOutputDir);
    }


    public void setExcludes(Path excludes)
    {
        javaTask.setExcludes(excludes);
    }


    public void setExcludesPathRef(Reference excludesPathRef)
    {
        javaTask.setExcludesPathRef(excludesPathRef);
    }


    public Path createExcludes()
    {
        return javaTask.createExcludes();
    }


    public void setClasspath(Path classpath)
    {
        javaTask.setClasspath(classpath);
    }


    public DependenciesTask createDependencies()
    {
        return javaTask.createDependencies();
    }


    public ClasspathFilter createClasspathFilter()
    {
        return javaTask.createClasspathFilter();
    }


    /**
     * Execute the task, generating an ant module configuration file.
     *
     * @throws BuildException
     */
    public void execute() throws BuildException
    {
        // order matters
        super.execute();
        JavaModule java = new JavaModule(
                moduleFile.moduleFile(),
                moduleFile.rootDir(),
                moduleFile.relativePaths());

        NewModuleRootManagerComponent javaComponent = java.javaComponent();
        javaTask.configure(javaComponent);
        
        try
        {
            moduleFile.writeBuildFile(java.toDom());
        }
        catch (ParserConfigurationException e)
        {
            throw new BuildException(e);
        }
    }


    public String toString()
    {
        return "IdeaJavaModuleTask{" +
               ", javaBuild=" + javaTask +
               '}';
    }
}
