package idea.conf.facets

import idea.conf.Visitable
import idea.conf.java.depend.Dependencies
import org.apache.tools.ant.Task

/**
 *
 * @author tomichj
 */
class GroovyFacet implements Visitable
{
    public static final String NONE = "NONE";
    private static final String DEFAULT = "GROOVY";

    // can be null, in which case lib is DEFAULT. Or can be any arbitrary thing.
    String sdk
    
    def injectGroovyLibrary(Dependencies dependencies)
    {
        println "groovy sdk name=${sdk}"
        if (NONE.equalsIgnoreCase(sdk)) return;
        if (sdk == null || sdk.equals(""))
        {
            sdk = DEFAULT;
        }

        // consider printing warning to user to add library with this name
        dependencies.createGlobalLibrary().name = sdk
    }

    public List<Visitable> getChildren()
    {
        return null;
    }

    String toString()
    {
        return "GroovyFacet{" +
                "sdkname=" + sdk +
                "}"
    }
}

