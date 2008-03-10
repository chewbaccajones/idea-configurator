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
import org.apache.tools.ant.types.Reference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Guts of the java build configuration for ant, used by both IdeaJavaModuleTask
 * and IdeaWebModuleTask.
 *
 * todo extend Task, go arg-less constructor
 *
 * @author Justin Tomich
 */
public class JavaTask extends Task
{
    private boolean excludeOutputPaths;
    private boolean excludeExploded;
    private Path sources;
    private Path tests;
    private Path excludes;
    private File outputDir;
    private File testsOutputDir;
    private Path classpath;
    private List<ClasspathFilter> filters =
            new ArrayList<ClasspathFilter>();
    private boolean inheritCompilerOutput;
    private final DependenciesTask dependencies = new DependenciesTask();


    public JavaTask(Project project)
    {
        sources = new Path(project);
        tests = new Path(project);
        excludes = new Path(project);
        classpath = new Path(project);
    }


    /**
     * Set string that source properties must begin with to be automagically
     * identified as source for a corresponging library. Other ant properties
     * beginning with this string will be treated as a source path for a library
     * jar of the name: sourceProperty + "." + path-to-jar.
     * <p/>
     * Example: sourceProperty is set to "source". A jar in the classpath has
     * the path "/lib/some-library-1.0.jar". If another property exists with the
     * name "source./lib/some-library-1.0.jar", the source property will be
     * treated as the source accompanying "some-library-1.0.jar".
     *
     * @param sourceProperty source properties must begin with sourceProperty
     */
    public void setSourceProperty(String sourceProperty)
    {
        dependencies.setSourceProperty(sourceProperty);
    }


    /**
     * Set string javadoc properties must begin with to be automagically picked
     * up.
     *
     * @param javadocProperty javadoc properties must begin with
     * javadocProperty
     * @see #setSourceProperty(String)
     */
    public void setJavadocProperty(String javadocProperty)
    {
        dependencies.setJavadocProperty(javadocProperty);
    }


    /**
     * Set the string javadoc url properties must begin with.
     *
     * @param javadocUrlProperty javadocUrl properties begin with this String
     * @see #setSourceProperty(String)
     */
    public void setJavadocUrlProperty(String javadocUrlProperty)
    {
        dependencies.setJavadocUrlProperty(javadocUrlProperty);
    }


    public void setSrcDir(File sourceDir)
    {
        sources.setLocation(sourceDir);
    }


    public void setSrcPathRef(Reference srcPathRef)
    {
        Path p = (Path) srcPathRef.getReferencedObject();
        sources.append(p);
    }


    public Path createSrc()
    {
        return sources.createPath();
    }


    public void setTestsDir(File testDir)
    {
        tests.setLocation(testDir);
    }


    public void setTestsPathRef(Reference testsPathRef)
    {
        Path p = (Path) testsPathRef.getReferencedObject();
        tests.append(p);
    }


    public Path createTests()
    {
        return tests.createPath();
    }


    public void setExcludeExploded(boolean excludeExploded)
    {
        this.excludeExploded = excludeExploded;
    }


    /**
     * Inherit project output paths. You must EITHER set this to true OR set
     * your outputDir and/or testsOutputDir
     *
     * @param inheritCompilerOutput if true, inherit output paths.
     * @throws org.apache.tools.ant.BuildException
     */
    public void setInheritCompilerOutput(boolean inheritCompilerOutput)
            throws BuildException
    {
        this.inheritCompilerOutput = inheritCompilerOutput;
        projectOrModuleOutput();
    }


    /**
     * Not required to be set, defaults to false. If true, output paths will be
     * marked "excluded" by IDEA.
     *
     * @param excludeOutputPaths true if output paths should be excluded
     */
    public void setExcludeOutputPaths(boolean excludeOutputPaths)
    {
        this.excludeOutputPaths = excludeOutputPaths;
        projectOrModuleOutput();
    }


    /**
     * Compiler output path for production .class files.
     *
     * @param outputDir directory as a File
     */
    public void setOutputDir(File outputDir)
    {
        this.outputDir = outputDir;
        projectOrModuleOutput();
    }


    /**
     * Compiler output path for test .class files.
     *
     * @param testsOutputDir tests output directory as a File
     */
    public void setTestsOutputDir(File testsOutputDir)
    {
        this.testsOutputDir = testsOutputDir;
        projectOrModuleOutput();
    }


    private void projectOrModuleOutput()
    {
        if (inheritCompilerOutput &&
            (excludeOutputPaths || outputDir != null || testsOutputDir != null))
        {
            throw new BuildException
                    ("May set EITHER inheritCompilerOutput OR " +
                     "ouputDir/testsOutputDir/excludeOutputPaths");
        }
    }


    public void setExcludes(Path excludes)
    {
        this.excludes.append(excludes);
    }


    public void setExcludesPathRef(Reference excludesPathRef)
    {
        Path p = (Path) excludesPathRef.getReferencedObject();
        excludes.append(p);
    }


    public Path createExcludes()
    {
        return excludes.createPath();
    }


    public void setClasspath(Path classpath)
    {
        this.classpath.append(classpath);
    }


    /**
     * Order entries are set in here.
     *
     * @return OrderEntriesSubtask to receive orderEntry configuration.
     */
    public DependenciesTask createDependencies()
    {
        return dependencies;
    }


    /**
     * Filter any classpath entries matching the given pattern.
     */
    public ClasspathFilter createClasspathFilter()
    {
        ClasspathFilter filter = new ClasspathFilter();
        filters.add(filter);
        return filter;
    }


    /**
     * Execute the task, generating an ant module configuration file.
     *
     * @throws BuildException
     */
    public void configure(NewModuleRootManagerComponent java)
            throws BuildException
    {
        // order matters
        java.setExcludeOutputPaths(excludeOutputPaths);
        java.setExcludeExploded(excludeExploded);
        if (sources.size() > 0) java.content().addSource(sources.list());
        if (tests.size() > 0) java.content().addTest(tests.list());
        if (excludes.size() > 0) java.content().addExclude(excludes.list());
        if (outputDir != null) java.setOutput(outputDir.getAbsolutePath());
        if (testsOutputDir != null)
        {
            java.setOutputTest(testsOutputDir.getPath());
        }

        // convert classpath to a Dependency... it'll get all the
        // magic and be able to add itself to the builder that way.
        if (classpath.size() > 0)
        {
            Path p = dependencies.createClasspath();
            p.append(classpath);
        }

        dependencies.configure(java);

        for (ClasspathFilter filter : filters)
        {
            filter.configure(java);
        }
    }


    public String toString()
    {
        return "JavaBuild{" +
               "excludeOutputPaths=" + excludeOutputPaths +
               "excludeExploded=" + excludeExploded +
               ", sources=" + sources +
               ", tests=" + tests +
               ", excludes=" + excludes +
               ", outputDir=" + outputDir +
               ", testsOutputDir=" + testsOutputDir +
               ", classpath=" + classpath +
               ", filters=" + filters +
               ", inheritCompilerOutput=" + inheritCompilerOutput +
               ", dependencies=" + dependencies +
               '}';
    }
}
