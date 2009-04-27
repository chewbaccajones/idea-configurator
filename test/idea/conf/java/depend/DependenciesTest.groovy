package idea.conf.java.depend

/**
 *
 *
 * @author tomichj
 */
class DependenciesTest extends GroovyTestCase
{
    void testCompleteEntriesEmptyDeps()
    {
        def d = new Dependencies(null, null)
        assertFalse(d.hasSource())
        assertFalse(d.hasJdk())

        d.completeJdkAndSourceOrderEntries()

        assertTrue(d.hasSource())
        assertTrue(d.hasJdk())
        
        assertTrue(d.deps[0] instanceof ModuleSource)
    }

    void testCompleteEntriesWithExistingSource()
    {
        def d = new Dependencies(null, null)
        d.createModuleSource()

        assertTrue(d.hasSource())
        assertFalse(d.hasJdk())

        d.completeJdkAndSourceOrderEntries()

        assertTrue(d.hasSource())
        assertTrue(d.hasJdk())

        assertTrue(d.deps[0] instanceof ModuleSource)
    }


    void testCompleteEntriesWithExistingJdk()
    {
        def d = new Dependencies(null, null)
        d.createJdk()

        assertFalse(d.hasSource())
        assertTrue(d.hasJdk())

        // source gets put in front of jdk
        d.completeJdkAndSourceOrderEntries()

        assertTrue(d.hasSource())
        assertTrue(d.hasJdk())

        assertTrue(d.deps[0] instanceof ModuleSource)
    }



    void testHasSource()
    {
        def d = new Dependencies(null, null)
        assertFalse(d.hasSource())

        d.createModuleSource()

        assertTrue(d.hasSource())
    }

    void testHasJdk()
    {
        def d = new Dependencies(null, null)
        assertFalse(d.hasJdk())

        d.createJdk()

        assertTrue(d.hasJdk())
    }

    void testPutBefore()
    {
        def d = new Dependencies(null, null)
        d.createModuleSource()
        d.createJdk()
        assertEquals(2, d.deps.size())

        Module module = new Module()
        d.putBefore(Jdk, module)
        assertEquals(1, d.deps.indexOf(module))
    }

    void testPutAfter()
    {
        def d = new Dependencies(null, null)
        d.createModuleSource()
        d.createJdk()
        assertEquals(2, d.deps.size())

        Module module = new Module()
        d.putAfter(Jdk, module)
        assertEquals(2, d.deps.indexOf(module))
    }
}

