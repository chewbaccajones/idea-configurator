package idea.conf.facets

import org.apache.tools.ant.BuildFileTest
import idea.conf.BaseBuildFileTester

/**
 * 
 * User: tomichj
 * Date: Apr 21, 2009
 * Time: 5:48:27 PM
 */

public class GwtIntegrationTest extends BaseBuildFileTester
{

    /**

     from real iml, selected gwt sdk

     <facet type="gwt" name="GWT">
       <configuration>
         <setting name="additionalCompilerParameters" value="" />
         <setting name="compilerMaxHeapSize" value="128" />
         <setting name="compilerOutputPath" value="" />
         <setting name="gwtScriptOutputStyle" value="DETAILED" />
         <setting name="gwtSdkUrl" value="file:///lib/gwt-mac-1.5.0" />
         <setting name="runGwtCompilerOnMake" value="true" />
       </configuration>
     </facet>

     also adds...

     <orderEntry type="library" name="gwt-user" level="project" />

     gwt-library contains classes and source poiting to:
        /lib/gwt-mac-1.5.0/gwt-user.jar

     */

    public void testGwtDefault()
    {
        executeTarget "gwt.default"
        assertSetting('additionalCompilerParameters', '')
        assertSetting('compilerMaxHeapSize', '128')
        assertSetting('compilerOutputPath', '')
        assertSetting('gwtScriptOutputStyle', '')
        assertSetting('gwtSdkUrl', 'file://')
        assertSetting('runGwtCompilerOnMake', 'true')
        assertNull facet('GWT').setting.find { it.'@name' == 'webFacet' }
        assertNull moduleManager().orderEntry.find { it.'@type' == 'library' &&
                it.'@name' == 'gwt-lib' }
    }

    def assertSetting(name, value)
    {
        def found = facet('GWT').setting.find { it.'@name' == name }.'@value'
        assertEquals value, found
    }

    public void testGwtWithProjectLib()
    {
        executeTarget "gwt.with.lib";

        // type 'library' for project and global libraries
        assertEquals 'gwt-lib',
                moduleManager().orderEntry.find { it.'@type' == 'library' }.'@name'
    }

    public void testGwtWithParams()
    {
        executeTarget "gwt.with.params";
        assertNull moduleManager().orderEntry.find { it.'@type' == 'library' }
        assertSetting('additionalCompilerParameters', '')
        assertSetting('compilerMaxHeapSize', '128')
        assertSetting('compilerOutputPath', '')
        assertSetting('gwtScriptOutputStyle', 'DETAILED')
        assertSetting('gwtSdkUrl', 'file:///path/to/gwt-1.5')
        assertSetting('runGwtCompilerOnMake', 'true')
        assertSetting('webFacet', 'web-facet-name')
    }

}