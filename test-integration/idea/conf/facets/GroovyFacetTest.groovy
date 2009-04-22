package idea.conf.facets

import org.apache.tools.ant.BuildFileTest

/**
 * 
 * User: tomichj
 * Date: Apr 17, 2009
 * Time: 10:23:56 AM
 */

public class GroovyFacetTest extends BuildFileTest
{
    public void setUp()
    {
        configureProject("build-test.xml")
    }
    

    public void testGroovyNoSdk()
    {
        executeTarget "groovy.plain"
        def module = new XmlParser().parseText(getOutput())
        def groovy = module.component.find { it['@name'] == 'FacetManager' }.facet[0]

        assertEquals 'Groovy', groovy['@type']
        assertEquals 'Groovy', groovy['@name']
        assertEquals 1, groovy.configuration.size()
        assertEquals 0, groovy.configuration[0].attributes().size()
    }


    public void testGrovyWithSdk()
    {
        executeTarget "groovy.sdk"
        def module = new XmlParser().parseText(getOutput())

        def groovy = module.component.find { it['@name'] == 'FacetManager' }.facet[0]

        assertEquals 'Groovy', groovy['@type']
        assertEquals 'Groovy', groovy['@name']
        assertEquals 1, groovy.configuration.size()
        assertEquals 0, groovy.configuration[0].attributes().size()
    }


    /**

     if set to no sdk, no lib added to deps and set compile to false

     compile groovy files: empty <configuration/>

     
     
     <component name="FacetManager">
       <facet type="Groovy" name="Groovy">
         <configuration compile="false" />
       </facet>
     </component>
     
     */
}

