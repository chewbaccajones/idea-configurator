package idea.conf.java.depend

import org.apache.tools.ant.types.Path

/**
 * 
 */
public class PathFilter
{
    String pattern;

    def PathFilter(pattern)
    {
        this.pattern = pattern;
    }
    

    /**
     * Remove any entries from the path that matches the given pattern. The pattern uses
     * the jdk 1.5 java.util.regex.Pattern syntax.
     *
     * @param classes any classes matching pattern are removed from returned Path
     * @return filtered Path
     * @see java.util.regex.Pattern
     */
    Path filter(Path classes)
    {
        def filtered = classes.list().findAll { !matchesJar(it) }
        Path filteredPath = new Path(classes.getProject())
        filtered.each { filteredPath.createPathElement().setPath(it) }
        return filteredPath
    }

    boolean matches(Classpathed classpathed)
    {
        return classpathed.getClasspath().list().any { matchesJar(it) }
    }

    boolean matches(Path path)
    {
        return path.list().any { matchesJar(it) }
    }

    private boolean matchesJar(String jar)
    {
        if (pattern == null) return false
        return jar.matches(pattern)
    }
}