package idea.conf.java.depend

/**
 *
 *
 * @author tomichj
 */
class DependenciesTest extends GroovyTestCase
{
    void testHasJdk()
    {
        def d = new Dependencies(null)
        assertFalse(d.hasJdk())

        d.createJdk()

        assertTrue(d.hasJdk())
    }

    void testPutBefore()
    {
        def d = new Dependencies(null)
        d.createModuleSource()
        d.createJdk()
        assertEquals(2, d.dependencies.size())

        Module module = new Module()
        d.putBefore(Jdk, module)
        assertEquals(1, d.dependencies.indexOf(module))
    }

    void testPutAfter()
    {
        def d = new Dependencies(null)
        d.createModuleSource()
        d.createJdk()
        assertEquals(2, d.dependencies.size())

        Module module = new Module()
        d.putAfter(Jdk, module)
        assertEquals(2, d.dependencies.indexOf(module))
    }
}

