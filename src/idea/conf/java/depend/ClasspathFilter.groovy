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
    

    boolean matches(String jar)
    {
        return jar.matches(pattern)
    }

    /**
     * Remove any 'classes' that matches the given pattern. The pattern uses the
     * jdk 1.5 java.util.regex.Pattern syntax.
     *
     * @param pattern matching 'classes' Roots are removed.
     * @see java.util.regex.Pattern
     */
    Path filter(Path classes)
    {
        //def classesCopy = classes.list() as List
        //ListIterator iter = classesCopy.listIterator()
        //while (iter.hasNext())
        //{
        //    String path = iter.next()
        //    if (filters.any { filter -> filter.matches(path) } ) iter.remove()
        //}
        //classes = new Path( classes.getProject() )
        //classesCopy.each { path -> classes.createPathElement().setPath(path) }

        Path filtered = new Path(classes.getProject())
        List paths = classes.list() as List
        paths.findAll { !matches(it) }.each { filtered.createPathElement().setPath(it) }
        return filtered
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
        "ClasspathFilter{" << "pattern=" << pattern << "}"
    }
}