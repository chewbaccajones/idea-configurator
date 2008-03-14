package idea.conf

import org.apache.tools.ant.BuildException
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.Path
import org.apache.tools.ant.types.Reference

import idea.conf.depend.Dependencies
import idea.conf.depend.ClasspathFilter;



/**
 * The innards of the basic java configuration. Used by multiple module types.
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
    List<ClasspathFilter> filters = [] // todo deal with filters


    JavaComponent(Project project)
    {
        sources = new Path(project);
        tests = new Path(project);
        excludes = new Path(project);
        dependencies = new Dependencies(project);
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
        dependencies.setSourceProperty(sourceProperty);
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
        dependencies.setJavadocProperty(javadocProperty);
    }


    /**
     * Set the string javadoc url properties must begin with.
     *
     * @param javadocUrlProperty javadocUrl properties must begin with this value
     * @see #setSourceProperty(String)
     */
    void setJavadocUrlProperty(String javadocUrlProperty)
    {
        dependencies.setJavadocUrlProperty(javadocUrlProperty);
    }


    
    void setSrcDir(File sourceDir)
    {
        sources.setLocation(sourceDir);
    }


    void setSrcPathRef(Reference srcPathRef)
    {
        Path p = (Path) srcPathRef.getReferencedObject();
        sources.append(p);
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
        Path p = (Path) testsPathRef.getReferencedObject();
        tests.append(p);
    }


    Path createTests()
    {
        return tests.createPath();
    }


    void setExcludesPathRef(Reference excludesPathRef)
    {
        Path p = (Path) excludesPathRef.getReferencedObject()
        excludes.append(p);
    }
    

    void setExcludes(Path excludes)
    {
        this.excludes.append(excludes)
    }


    Path createExcludes()
    {
        return excludes.createPath()
    }


    ClasspathFilter createClasspathFilter()
    {
        ClasspathFilter filter = new ClasspathFilter()
        filters.add(filter)
        return filter
    }


    void setClasspath(Path classpath)
    {
        dependencies.createClasspath().append(classpath)
    }


    Path createClasspath()
    {
        dependencies.createClasspath()
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
               ", filters=" + filters +
               ", inheritCompilerOutput=" + inheritCompilerOutput +
               ", dependencies.size()=" + dependencies.size() +
               '}'
    }

}

