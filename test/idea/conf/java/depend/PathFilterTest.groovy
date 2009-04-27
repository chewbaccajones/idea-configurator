package idea.conf.java.depend

import org.apache.tools.ant.Project
import org.apache.tools.ant.types.Path
import idea.conf.ProjectTestCase

/**
 * 
 * User: tomichj
 * Date: Feb 11, 2009
 * Time: 9:31:06 AM
 */

public class PathFilterTest extends ProjectTestCase
{
    static final String JAR = "/foo/bar/baz.jar"
    static final String JAR2 = "/foo/bar/bizzle.jar"
    static final String JAR3 = "/foo/bar/bazzle.jar"

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

    void testFilterLongPath()
    {
        Project project = []
        Path testPath = new Path(project, JAR)
        testPath.createPathElement().setPath(JAR2)
        testPath.createPathElement().setPath(JAR3)

        // strip off JAR
        PathFilter filter = new PathFilter(".*baz.jar")
        Path result = filter.filter(testPath)
        
        assertEquals 2, result.list().length
        assertEquals JAR2, result.list()[0]
        assertEquals JAR3, result.list()[1]
    }
}