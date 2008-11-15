package idea.conf.java

import org.apache.tools.ant.BuildException
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.Path
import org.apache.tools.ant.types.Reference

import idea.conf.java.depend.Dependencies
import idea.conf.java.depend.ClasspathFilter
import idea.conf.Visitable
import idea.conf.java.depend.ModuleLibGenerator;



/**
 * The innards of the basic java configuration. To be used by multiple module types.
 *
 * @author tomichj
 */
class JavaComponent implements Visitable
{
    File outputDir
    File testsOutputDir
    boolean excludeOutputPaths
    boolean excludeExploded
    boolean inheritCompilerOutput

    Path sources
    Path tests
    Path excludes

    Dependencies dependencies
    ModuleLibGenerator moduleLibGenerator

    
    JavaComponent(Project project)
    {
        sources = new Path(project);
        tests = new Path(project);
        excludes = new Path(project);
        moduleLibGenerator = new ModuleLibGenerator(project)
        dependencies = new Dependencies(project, moduleLibGenerator);
    }

    
    List<Visitable> getChildren()
    {
        return [dependencies];
    }


    Dependencies createDependencies()
    {
        return dependencies
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
    void setSourceProperty(String sourceProperty)
    {
        moduleLibGenerator.setSourceProperty(sourceProperty);
    }


    /**
     * Set string javadoc properties must begin with to be automagically picked
     * up.
     *
     * @param javadocProperty javadoc property names must begin with this value
     * @see #setSourceProperty(String)
     */
    void setJavadocProperty(String javadocProperty)
    {
        moduleLibGenerator.setJavadocProperty(javadocProperty);
    }


    /**
     * Set the string javadoc url properties must begin with.
     *
     * @param javadocUrlProperty javadocUrl properties must begin with this value
     * @see #setSourceProperty(String)
     */
    void setJavadocUrlProperty(String javadocUrlProperty)
    {
        moduleLibGenerator.setJavadocUrlProperty(javadocUrlProperty);
    }

    
    void setSrcDir(File sourceDir)
    {
        sources.setLocation(sourceDir);
    }


    void setSrcPathRef(Reference srcPathRef)
    {
        sources.append(path(srcPathRef))
    }


    Path createSrc()
    {
        return sources.createPath();
    }


    void setTestsDir(File testDir)
    {
        tests.setLocation(testDir);
    }


    void setTestsPathRef(Reference testsPathRef)
    {
        tests.append(path(testsPathRef));
    }


    Path createTests()
    {
        return tests.createPath();
    }


    void setExcludesPathRef(Reference excludesPathRef)
    {
        excludes.append(path(excludesPathRef));
    }
    

    void setExcludes(Path excludes)
    {
        this.excludes.append(excludes)
    }


    Path createExcludes()
    {
        return excludes.createPath()
    }


    void setClasspath(Path classpath)
    {
        dependencies.createClasspath().append(classpath)
    }


    Path createClasspath()
    {
        dependencies.createClasspath()
    }

    private Path path(Reference ref)
    {
        return (Path) ref.referencedObject
    }

    private void validateInheritOrSetOutput()
    {
        if (inheritCompilerOutput && (output != null || outputTest != null))
        {
            throw new BuildException("Set either inheritCompilerOutput or " +
                                     "output/outputTest, not both.")
        }
    }


    String toString()
    {
        return "JavaComponent{" +
               "excludeOutputPaths=" + excludeOutputPaths +
               ", excludeExploded=" + excludeExploded +
               ", sources=" + sources +
               ", tests=" + tests +
               ", excludes=" + excludes +
               ", outputDir=" + outputDir +
               ", testsOutputDir=" + testsOutputDir +
               ", inheritCompilerOutput=" + inheritCompilerOutput +
               ", dependencies.size()=" + dependencies.size() +
               '}'
    }

}

