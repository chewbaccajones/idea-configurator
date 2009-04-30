package idea.conf.facets

import org.apache.tools.ant.BuildFileTest
import idea.conf.BaseBuildFileTester

/**
 * 
 * User: tomichj
 * Date: Apr 17, 2009
 * Time: 10:23:56 AM
 */

public class GroovyFacetIntegrationTest extends BaseBuildFileTester
{
    public void testGroovyNoSdk()
    {
        executeTarget "groovy.plain"
        def groovy = facet('Groovy')
        assertEquals 1, groovy.configuration.size()
        assertEquals 0, groovy.configuration[0].attributes().size()
        assertEquals 'application',
                moduleManager().orderEntry.find { it.'@name' == 'GROOVY' }.'@level'
    }


    public void testGrovyWithSdk()
    {
        executeTarget "groovy.sdk"
        def groovy = facet('Groovy')
        assertEquals 1, groovy.configuration.size()
        assertEquals 0, groovy.configuration[0].attributes().size()
        assertEquals 'application',
                moduleManager().orderEntry.find { it.'@name' == 'groovy-1.5.6' }.'@level'
    }

// fixme
    public void testGrovyNoSdk()
    {
        executeTarget "groovy.sdk.none"
        def groovy = facet('Groovy')
        assertEquals 1, groovy.configuration.size()
        assertEquals 0, groovy.configuration[0].attributes().size()
        assertNull moduleManager().orderEntry.find { it.'@type' == 'library' }
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

