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
    void testPropertyTypes()
    {
        final String fooJar = "/lib/foo.jar"
        Project proj = new Project()
        Path path = new Path(proj, fooJar)
        PathInspector inspector = new PathInspector(proj, null, null, null)
        def libs = inspector.moduleLibsForPath(path, false)
        SimpleModuleLibrary lib = libs[0]
        assertEquals(fooJar, lib.classes.list()[0])
        assertNotNull(lib.sources)
        assertEquals(0, lib.sources.size())
        assertNotNull(lib.javadocs)
        assertEquals(0, lib.javadocs.size())
        assertNotNull(lib.javadocUrls)
        assertEquals(0, lib.javadocUrls.size())
    }

    void testSourceProperty()
    {
        final String fooJar = "/lib/foo.jar"
        final String fooSrcJar = "/lib/foo.src.jar"
        
        Project proj = new Project()
        proj.setInheritedProperty("foo.jar", fooJar)
        proj.setInheritedProperty("source.foo.jar", fooSrcJar)

        Path path = new Path(proj, fooJar)

        PathInspector inspector = new PathInspector(proj, "source", null, null)
        def libs = inspector.moduleLibsForPath(path, true)
        def lib = libs[0]
        assertEquals(fooJar, lib.classes.list()[0])
        assertEquals(fooSrcJar, lib.sources.list()[0])
    }

    void testNoProperty()
    {
        final String fooJar = "foo.jar"
        final String fooJarPath = "/lib/" + fooJar
        Project proj = new Project()
        PathInspector inspector = new PathInspector(proj, "source", null, null)
        assertEquals(fooJar, inspector.findPropertyNameForJar(fooJarPath))
    }

    void testFindSourceJarByJarName()
    {
        final String fooJar = "/lib/foo.jar"
        final String fooSrcJar = "/lib/foo.src.jar"

        Project proj = new Project()
        proj.setInheritedProperty("source.foo.jar", fooSrcJar)

        Path path = new Path(proj, fooJar)

        PathInspector inspector = new PathInspector(proj, "source", null, null)
        def modules = inspector.moduleLibsForPath(path, false)
        def module = modules[0]

        assertEquals(fooJar, module.classes.toString())
        assertEquals(fooSrcJar, module.sources.toString())
    }
}
