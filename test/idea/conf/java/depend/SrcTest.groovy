package idea.conf.java.depend

import org.apache.tools.ant.BuildFileTest

/**
 * 
 */

// problem, ant's BuildFileTest is a layer up in the classloader heirarchy, fails
// without junit in ant.lib.dir
public class SrcTest extends BuildFileTest
{
    public void testNothing()
    {
        
    }
    
    public void setUp()
    {
        // initialize Ant
        configureProject("build-test.xml");
    }

    void testEmpty()
    {
        executeTarget("empty");
        assertLogContaining("foo")
    }
}
