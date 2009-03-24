package idea.conf.java.depend

import org.apache.tools.ant.Project
import org.apache.tools.ant.types.Path

/**
 * 
 * User: tomichj
 * Date: Feb 11, 2009
 * Time: 9:31:06 AM
 */

public class PathFilterTest extends GroovyTestCase
{
    static final String JAR = "/foo/bar/baz.jar"
    static final String JAR2 = "/foo/bar/bizzle.jar"
    
    void testMatchesTrueCase()
    {
        Project project = []
        Path path = new Path(project, JAR)
        PathFilter filter = new PathFilter(".*baz.jar")

        assertTrue(filter.matches(path))
    }

    void testMatchesFalseCase()
    {
        Project project = []
        Path path = new Path(project, JAR2)
        PathFilter filter = new PathFilter(".*baz.jar")

        assertFalse(filter.matches(path))
    }

}