package idea.conf

import org.apache.tools.ant.BuildFileTest

/**
 * 
 * User: tomichj
 * Date: Apr 22, 2009
 * Time: 5:50:49 PM
 */

public abstract class BaseBuildFileTester extends BuildFileTest
{

    public void setUp()
    {
        configureProject("build-test.xml")
    }

    def component(String componentName)
    {
        def module = new XmlParser().parseText(getOutput())
        return module.component.find { it.'@name' == componentName }
    }

    def moduleManager()
    {
        return component('NewModuleRootManager')
    }

    def facetManager()
    {
        return component('FacetManager')
    }

    def facet(String facetName)
    {
        return facetManager().facet.find { it.'@name' == facetName }
    }

    def orderEntries(String type)
    {
        return moduleManager().orderEntry.findAll { it.'@type' == type }
    }
}