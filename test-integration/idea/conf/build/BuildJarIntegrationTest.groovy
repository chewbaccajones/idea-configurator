package idea.conf.build

import idea.conf.BaseBuildFileTester

/**
 * 
 * User: tomichj
 * Date: Apr 24, 2009
 * Time: 5:28:33 PM
 */

public class BuildJarIntegrationTest extends BaseBuildFileTester
{
    final def LIBRARY_1 = 'jar://$MODULE_DIR$/lib/lib1.jar!/'
    final def LIBRARY_2 = 'jar://$MODULE_DIR$/lib/lib2.jar!/'
    final def MODULE = 'fooModule'
    final def MODULE_LIBRARY = 'someNamedModuleLibrary'
    final def PROJECT_LIBRARY = 'someProjectLibrary'
    final def GLOBAL_LIBRARY = 'someGlobalLibrary'

    void testBuildJar()
    {
        executeTarget 'build.jar'
        println getOutput()
        assertContainerElement(null, 'library', 'module', LIBRARY_1)
        assertContainerElement(null, 'library', 'module', LIBRARY_2)
        assertContainerElement(MODULE, 'module', null, null)
        assertContainerElement(MODULE_LIBRARY, 'library', 'module', null)
        assertContainerElement(PROJECT_LIBRARY, 'library', 'project', null)
        assertContainerElement(GLOBAL_LIBRARY, 'library', 'application', null)
    }

    def assertContainerElement(name, type, level, url)
    {
        def searchCriterion = []
        if (name) searchCriterion <<  { it.'@name' == name }
        if (type) searchCriterion <<  { it.'@type' == type }
        if (level) searchCriterion << { it.'@level' == level }
        if (url) searchCriterion <<   { it.url.text() == url }
        
        def elements = component('BuildJarSettings').containerElement
        def element = elements.find {  elem ->
            searchCriterion.every  { test -> test(elem) }
        }
        assertNotNull element
        assertElementAttributes(element)
    }

    def assertElementAttributes(element)
    {
        // right now all build elements have the same method and URI attributes  
        element.attribute.find { attr -> attr.'@name' == 'method' }.'@value' == '1'
        element.attribute.find { attr -> attr.'@name' == 'URI' }.'@value' == '/'
    }

}