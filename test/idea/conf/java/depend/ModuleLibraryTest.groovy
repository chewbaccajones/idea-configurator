package idea.conf.java.depend

import org.apache.tools.ant.Project
import org.apache.tools.ant.types.Path

/**
*
*
* @author tomichj
*/
class ModuleLibraryTest extends GroovyTestCase
{
    void testFindsSource()
    {
        final String fooJar = "/a/foo.jar"
        final String fooSrcJar = "/a/foo.src.jar"

        Project project = new Project()
        project.setInheritedProperty("foo.jar", fooJar)
        project.setInheritedProperty("source.foo.jar", fooSrcJar)

        ModuleLibGenerator libGen = new ModuleLibGenerator(project)

        ModuleLibrary lib = new ModuleLibrary(project)
        lib.createClasses().setLocation(new File(fooJar))
        lib.addLibraryAssets(libGen)
        
        Path classes = lib.getClasses()
        assertEquals(1, classes.list().size())
        assertNotNull(classes.list().find { it.equals(fooJar) })

        Path sources = lib.getSources()
        assertEquals(1, sources.list().size())
        assertNotNull(sources.list().find { it.equals(fooSrcJar) })
    }

    void testPathAdd()
    {
        Project p = new Project();
        Path a = new Path(p, "/foo")
        Path b = new Path(p, "/foo")
        a.add(b)
//        println a
    }
}
