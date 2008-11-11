package idea.conf.java.depend

import idea.conf.Visitable
import org.apache.tools.ant.BuildException
import org.apache.tools.ant.types.Path

/**
 * Specifies a jdk 1.5 regex pattern used to remove any matching classes from a
 * <classpath>.
 *
 * filters are now applied by the renderers, not the model
 *
 * todo remove IdeaLibrary entries as well.
 *
 * @author tomichj
 */
class ClasspathFilter implements Dependency
{
    String pattern;
    

    /**
     * Remove any 'classes' that matches the given pattern. The pattern uses the
     * jdk 1.5 java.util.regex.Pattern syntax.
     *
     * @param pattern matching 'classes' Roots are removed.
     * @see java.util.regex.Pattern
     */
    Path filter(Path classes)
    {
        //println "before=" + classes.list()
        def filtered = classes.list().findAll { !matches(it) }
        //println "after=" + filtered
        Path path = new Path(classes.getProject())
        filtered.each { path.createPathElement().setPath(it) }
        return path
    }
    
    private boolean matches(String jar)
    {
        return jar.matches(pattern)
    }
    
    List<Visitable> getChildren()
    {
        return null;
    }

    void validate()
    {
        if (!pattern) throw new BuildException("ClasspathFilter pattern cannot be null!")
    }


    String toString()
    {
        "ClasspathFilter{pattern=${pattern}}"
    }
}