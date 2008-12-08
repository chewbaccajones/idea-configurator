package idea.conf.java.depend

import org.apache.tools.ant.Project

/**
 *
 *
 * @author tomichj
 */

public class IdeaLibraryTest extends GroovyTestCase
{
    static final String NAME = 'foo.jar'

    void testAsModule()
    {
        Project project = new Project()
        ModuleLibGenerator libGen = new ModuleLibGenerator(project)

        IdeaLibrary idealib = new IdeaLibrary(project)
        idealib.setName(NAME)
        idealib.setLibGenerator(libGen) 

        ModuleLibraryType modlib = idealib.asModuleLibrary()
        assertNotNull(modlib)
        assertTrue(modlib.ideaLibs[0] == NAME)
        assertEquals(0, modlib.sources.size())
    }
    
    void testAsModuleWithSourceProperty()
    {
        final String fooJar = "/a/foo.jar"
        final String fooSrcJar = "/a/foo.src.jar"

        Project project = new Project()
        project.setInheritedProperty(NAME, fooJar)
        project.setInheritedProperty("source.foo.jar", fooSrcJar)

        ModuleLibGenerator libGen = new ModuleLibGenerator(project)
        IdeaLibrary idea = new IdeaLibrary(project)
        idea.setName(NAME)
        idea.setLibGenerator(libGen) 

        ModuleLibraryType modlib = idea.asModuleLibrary()
        assertNotNull(modlib)
        assertTrue(modlib.ideaLibs[0] == NAME)
        assertTrue(modlib.getSources().size() == 1)
        assertTrue(modlib.getSources().list()[0] == fooSrcJar)
    }
}
