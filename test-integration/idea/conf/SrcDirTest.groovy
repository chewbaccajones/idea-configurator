package idea.conf

import org.apache.tools.ant.BuildFileTest

/**
 * 
 */
public class SrcDirTest extends BuildFileTest
{
    public void setUp()
    {
        configureProject("build-test.xml")
    }

    public void testSingleFolder()
    {
        executeTarget "source.folder"
        def module = new XmlParser().parseText(getOutput())
        def srcNodes = module.component.content.sourceFolder
        assertEquals 1, srcNodes.size()

        def srcNode = srcNodes[0]
        assertEquals "false", srcNode.'@isTestSource'
        assertTrue srcNode.'@url'.endsWith('/src')
    }

    public void testTwoFolders()
    {
        executeTarget "source.folders"
        def module = new XmlParser().parseText(getOutput())
        def srcNodes = module.component.content.sourceFolder

        assertEquals 2, srcNodes.size()
        assertTrue srcNodes[0].'@url'.endsWith('/src1')
        assertTrue srcNodes[1].'@url'.endsWith('/src2')
    }

    public void testSrcTestFolders()
    {
        executeTarget "source.tests.folders"
        def module = new XmlParser().parseText(getOutput())
        def srcNodes = module.component.content.sourceFolder
        assertEquals "false", srcNodes[0].'@isTestSource';
        assertEquals "true", srcNodes[1].'@isTestSource';
    }
}
