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

    public void testOneModuleLib()
    {
        executeTarget 'jar.to.module'
        def module = new XmlParser().parseText(getOutput())
        def moduleLibs = module.component.orderEntry.findAll {
            it.'@type' == 'module-library'
        }
        assertEquals 1, moduleLibs.size()
        assertEquals 'jar://$MODULE_DIR$/survives.jar!/',
                moduleLibs[0].library.CLASSES.root[0].'@url'
    }

    public void testModules()
    {
        executeTarget 'jar.to.module'
        def module = new XmlParser().parseText(getOutput())
        def moduleDeps = module.component.orderEntry.findAll { it.'@type' == 'module' }
        assertEquals(['garble', 'dumbModule2', 'dumbModule'], moduleDeps.'@module-name')
        assertEquals ([null, "", null], moduleDeps.'@exported')
    }

}