package idea.conf.java.depend

import idea.conf.Visitable
import org.apache.tools.ant.BuildException
import org.apache.tools.ant.types.Path
import static idea.conf.Validator.*


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
class ClasspathFilter implements Dependency, Filter
{
    String pattern;
    private PathFilter pathFilter

    
    public Path filter(Path classes)
    {
        if (pathFilter == null) pathFilter = new PathFilter(pattern)
        return pathFilter.filter(classes)
    }

    boolean matches(Path jar)
    {
        if (pathFilter == null) pathFilter = new PathFilter(pattern)
        return pathFilter.matches(jar)
    }

    ///**
    // * Remove any entries from the path that matches the given pattern. The pattern uses
    // * the jdk 1.5 java.util.regex.Pattern syntax.
    // *
    // * @param pattern matching 'classes' Roots are removed.
    // * @see java.util.regex.Pattern
    // */
    //Path filter(Path classes)
    //{
    //    def filtered = classes.list().findAll { !matches(it) }
    //    Path filteredPath = new Path(classes.getProject())
    //    filtered.each { filteredPath.createPathElement().setPath(it) }
    //    return filteredPath
    //}
    //
    //private boolean matches(String jar)
    //{
    //    if (pattern == null) return false
    //    return jar.matches(pattern)
    //}

    List<Visitable> getChildren()
    {
        return null;
    }

    void validate()
    {
        // consider allowing pattern to be null
        //if (!pattern) throw new BuildException("ClasspathFilter pattern cannot be null!")
        notNull(pattern, "ClasspathFilter pattern cannot be null!")
    }


    String toString()
    {
        "ClasspathFilter{pattern=${pattern}}"
    }

}
