package idea.conf.java.depend

import org.apache.tools.ant.BuildFileTest

/**
 * 
 * User: tomichj
 * Date: Apr 10, 2009
 * Time: 4:34:50 PM
 */

public class JarToModuleTest extends BuildFileTest
{
    public void setUp()
    {
        configureProject("build-test.xml")
    }

    public void testManyJarToModulesElements()
    {
        executeTarget 'jar.to.module'
        def module = new XmlParser().parseText(getOutput())

        // test the module libs... should only be one
        def moduleLibs = module.component.orderEntry.findAll {
            it.'@type' == 'module-library'
        }
        assertEquals 1, moduleLibs.size()
        assertEquals 'jar://$MODULE_DIR$/survives.jar!/',
                moduleLibs[0].library.CLASSES.root[0].'@url'

        // test the module declarations... should be one for each of the filtered jars
        def moduleDeps = module.component.orderEntry.findAll { it.'@type' == 'module' }
        assertEquals(['garble', 'dumbModule2', 'dumbModule'], moduleDeps.'@module-name')
        assertEquals ([null, "", null], moduleDeps.'@exported')
    }
    

    public void testTopLevelJarsToModules()
    {
        executeTarget 'jarsToModules.top.level.declaration'
        def module = new XmlParser().parseText(getOutput())
        def moduleDeps = module.component.orderEntry.findAll { it.'@type' == 'module' }
        assertEquals(['dumb', 'garble'], moduleDeps.'@module-name')
        assertEquals ([null, null], moduleDeps.'@exported')
    }

}