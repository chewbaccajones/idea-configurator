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

    // can be null, in which case lib is DEFAULT
    def String sdk

    public void setSdk(String sdk)
    {
        println "setSdk(${sdk}) called!"
        this.sdk = sdk
    }

    def injectGroovyLibrary(Dependencies dependencies)
    {
        println "groovy sdk name=${sdk}"
        if (sdk == NONE) return;
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

