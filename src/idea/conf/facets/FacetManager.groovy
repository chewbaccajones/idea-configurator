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

    FacetManager(Dependencies dependencies)
    {
        this.dependencies = dependencies;
    }

    public void addConfiguredGroovy(GroovyFacet groovy)
    {
        facets << groovy
        groovy.injectGroovyLibrary(dependencies)
    }

    public void addConfiguredGwt(GwtFacet gwt)
    {
        facets << gwt
    }

    public void addConfiguredSpring(SpringFacet spring)
    {
        facets << spring
    }
    
    public List<Visitable> getChildren()
    {
        return facets;
    }

    public String toString()
    {
        return "FacetManager{facets.size()=${facets.size()}}"
    }
}
