package idea.conf.java.depend

import org.apache.tools.ant.BuildFileTest

public class PathFilterIntegrationTest extends BuildFileTest
{
    public void setUp()
    {
        configureProject("build-test.xml")
    }

    public void testManyJarToModulesElements()
    {
        executeTarget 'path.filter'
        def module = new XmlParser().parseText(getOutput())
        def libs = module.component.find { it.'@name' == 'NewModuleRootManager'}.
            orderEntry.findAll { it.'@type' == 'module-library' }
        def urls = libs.library.CLASSES.root.collect { it.'@url'[0] }

        assertTrue urls.any { it.contains ('garble.jar') }
        assertTrue urls.any { it.contains ('dumb.jar') }
        assertTrue urls.any { it.contains ('survives.jar') }
        assertFalse urls.any { it.contains ('module2.jar') }
    }
}
