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
    void testAdd()
    {
        Project project = new Project()

        Classpath classpath = new Classpath(project, "source", null, null)
        classpath.createPath().setLocation(new File("/a"))

        ModuleLibrary lib = new ModuleLibrary(project)
        lib.createClasses().setLocation(new File("/b"))
        lib.add(classpath)

        Path classes = lib.getClasses()
        assertNotNull(classes.list().find { it.equals("/b") })
        assertNotNull(classes.list().find { it.equals("/a") })
    }
}
