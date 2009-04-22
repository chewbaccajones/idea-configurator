package idea.conf.java.depend

import org.apache.tools.ant.Project
import org.apache.tools.ant.types.Path
import idea.conf.Logger

/**
 *
 *
 * @author tomichj
 */

public class ClasspathFilterTest extends GroovyTestCase
{
    static final String JAR = "/foo/bar/baz.jar"
    static final String JAR2 = "/foo/bar/bizzle.jar"

    protected void setUp()
    {
        Logger.init new Project()  
    }



    void testNullPattern()
    {
        Project project = []
        Path path = new Path(project, JAR)
        ClasspathFilter f = []

        Path result = f.filter(path)
        
        assertEquals(JAR, result.toString())
    }


    void testWithNonMatchingPattern()
    {
        Project project = []
        Path path = new Path(project, JAR)
        ClasspathFilter f = []
        f.setPattern(".*gibbly.jar") 

        Path result = f.filter(path)

        assertEquals(JAR, result.toString())
    }

    
    void testWithMatchingPattern()
    {
        Project project = []
        Path path = new Path(project, JAR)
        ClasspathFilter f = []
        f.setPattern(".*baz.jar")

        Path result = f.filter(path)

        assertEquals("", result.toString())
    }


    void testWithMultiplePathsOneMatching()
    {
        Project project = []
        Path path = new Path(project, JAR)
        path.createPathElement().setPath(JAR2)

        ClasspathFilter f = []
        f.setPattern(".*baz.jar")

        Path result = f.filter(path)

        assertEquals(JAR2, result.toString())
    }

    void testWithMultiplePathsAllMatching()
    {
        Project project = []
        Path path = new Path(project, JAR)
        path.createPathElement().setPath(JAR2)

        ClasspathFilter f = []
        f.setPattern(".*.jar")

        Path result = f.filter(path)

        assertEquals("", result.toString())
    }
}