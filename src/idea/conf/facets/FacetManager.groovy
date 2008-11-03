package idea.conf.facets

import idea.conf.Visitable
import idea.conf.java.depend.Dependencies

/**
 * FacetManager might disappear. It's an extra layer we probably don't need.
 * @author tomichj
 */
class FacetManager implements Visitable
{
    def facets = []
    Dependencies dependencies

    def FacetManager(dependencies)
    {
        this.dependencies = dependencies;
    }

    public void addConfiguredGroovy(GroovyFacet groovy)
    {
        println "addGroovy() called!"
        facets << groovy
        groovy.injectGroovyLibrary(dependencies)
    }

    public List<Visitable> getChildren()
    {
        return facets;
    }

    public String toString()
    {
        return "FacetManager{" +
                "number of facets=" + facets.size() +
                "}"
    }
}