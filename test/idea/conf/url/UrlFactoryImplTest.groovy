package idea.conf.url

/**
 * @author tomichj
 */
class UrlFactoryImplTest extends GroovyTestCase
{
    void testRelativePath()
    {
        File home = new File("/vobs/ssp/cci");
        File file1 = new File("/vobs/ssp/cci/main/src");
        File file2 = new File("/vobs/ssp/cci/main/src/core/web/stuff");
        File file3 = new File("/vobs/rws/adobefacade/web");

        UrlFactoryImpl f = new UrlFactoryImpl(home, true);

        assertEquals("main/src", f.relativePath(home, file1));
        assertEquals("main/src/core/web/stuff", f.relativePath(home, file2));
        assertEquals("../../rws/adobefacade/web", f.relativePath(home, file3));
    }


    void testRelativeOutsideRoot()
    {
        File home = new File("/vobs/ssp");
        String file = "/vobs/rws";

        UrlFactoryImpl f = new UrlFactoryImpl(home, false);

        // should be relative... (we're using protected api, always relative)
        String path = f.relativePath(file);
        assertEquals('$MODULE_DIR$/../rws', path) 

        // computePath should NOT be relative, constructor specifies no relative paths
        // outside of root directory.
        String computed = f.computePath(file);
        assertEquals(file, computed)
    }
    

    void testRelativeOutsideRootSwitch()
    {
        File home = new File("/vobs/ssp/cci");
        File file1 = new File("/vobs/ssp/cci/web");
        File file2 = new File("/vobs/ssp/dumbo/web");
        File file3 = new File("/vobs/rws/adobefacade/web");

        UrlFactoryImpl f = new UrlFactoryImpl(home, false);

        String path1 = f.file(file1)
        assertEquals('file://$MODULE_DIR$/web', path1)

        String path2 = f.file(file2)
        assertEquals('file:///vobs/ssp/dumbo/web', path2)

        String path3 = f.file(file3)
        assertEquals('file:///vobs/rws/adobefacade/web', path3)
    }


    void testPathParts()
    {
        File home = new File("/vobs/ssp/cci");
        File file1 = new File("/vobs/ssp/cci/web");

        UrlFactoryImpl f = new UrlFactoryImpl(home, false);

        assertEquals(["web", "cci", "ssp", "vobs", ""], f.pathParts(file1))
    }
    

    void testMathPathLists()
    {
        File home = new File("/vobs/ssp/cci");
        UrlFactoryImpl f = new UrlFactoryImpl(home, false);

        def p1 = f.pathParts(home)
        def p2 = f.pathParts(new File("/vobs/ssp/cci/web"))
        def p3 = f.pathParts(new File("/vobs/ssp"))
        def p4 = f.pathParts(new File("/vobs/ssp/toby"))
        def p5 = f.pathParts(new File("/trent/was/here"))


        assertEquals("web", f.matchPathLists(p1, p2))
        assertEquals("../", f.matchPathLists(p1, p3))
        assertEquals("../toby", f.matchPathLists(p1, p4))
        assertEquals("../../../trent/was/here", f.matchPathLists(p1, p5))
    }



}