package idea.conf.java.depend

import org.apache.tools.ant.Project
import org.apache.tools.ant.types.Path

/**
 *
 *
 * @author tomichj
 */
class PathInspectorTest extends GroovyTestCase
{
    void testSourceProperty()
    {
        final String fooJar = "/lib/foo.jar"
        final String fooSrcJar = "/lib/foo.src.jar"
        
        Project proj = new Project()
        proj.setInheritedProperty("foo.jar", fooJar)
        proj.setInheritedProperty("source.foo.jar", fooSrcJar)

        Path path = new Path(proj, fooJar)

        PathInspector inspector = new PathInspector(proj, "source", null, null)
        def libs = inspector.libs(path)
        def lib = libs[0]
        assertEquals(fooJar, lib.classes())
        assertEquals(fooSrcJar, lib.sources())
        //println lib.sources()
    }
}
