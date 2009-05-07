package idea.conf.build

import idea.conf.BaseBuildFileTester


public class BuildJarIntegrationTest extends BaseBuildFileTester
{

    final def LIBRARY_1 = 'jar://$MODULE_DIR$/lib/lib1.jar!/'
    final def LIBRARY_2 = 'jar://$MODULE_DIR$/lib/lib2.jar!/'
    final def MODULE = 'fooModule'
    final def MODULE_LIBRARY = 'someNamedModuleLibrary'
    final def PROJECT_LIBRARY = 'someProjectLibrary'
    final def GLOBAL_LIBRARY = 'someGlobalLibrary'

    void testBuildJarAndCopy()
    {
        executeTarget 'build.jar.module.jarandcopy'
        println getOutput()
        def element = containerElement('fooModule', 'module', null, null)
        assertNotNull element
        assertAttribute(element, 'method', '5')
        assertAttribute(element, 'URI', '/fooModule.jar')
    }


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
        def element = containerElement(name,type, level, url)
        assertNotNull element
        assertStandardElementAttributes(element) 
    }

    def containerElement(name, type, level, url)
    {
        def searchCriterion = []
        if (name) searchCriterion << { it.'@name' == name }
        if (type) searchCriterion << { it.'@type' == type }
        if (level) searchCriterion << { it.'@level' == level }
        if (url) searchCriterion << { it.url.text() == url }

        def elements = component('BuildJarSettings').containerElement
        def element = elements.find { elem ->
            searchCriterion.every {test -> test(elem) }
        }
        return element
    }

    def assertAttribute(element, name, value)
    {
        def attributes = element.attribute
        def attr = attributes.find { it.'@name' == name }
        assertNotNull attr
        assertEquals value, attr.'@value'
    }

    def assertStandardElementAttributes(element)
    {
        // right now all build elements have the same method and URI attributes
        assertAttribute(element, 'method', '1')
        assertAttribute(element, 'URI', '/')
    }

}