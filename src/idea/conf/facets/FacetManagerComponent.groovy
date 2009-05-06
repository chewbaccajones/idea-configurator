package idea.conf.facets

import idea.conf.Visitable
import idea.conf.java.depend.Dependencies
import idea.conf.facets.web.WebFacet
import idea.conf.JavaModule

/**
 * FacetManager might disappear. It's an extra layer we probably don't need.
 * @author tomichj
 */
class FacetManagerComponent implements Visitable
{
    def facets = []
    JavaModule javaModule
    Dependencies dependencies

    public FacetManagerComponent(JavaModule javaModule)
    {
        this.javaModule = javaModule;
        this.dependencies = javaModule.java.dependencies
    }


    public void addConfiguredGroovy(GroovyFacet groovy)
    {
        facets << groovy
        groovy.injectDependencies(dependencies)
    }

    public void addConfiguredGwt(GwtFacet gwt)
    {
        facets << gwt
        gwt.injectDependencies(dependencies)
    }

    public void addConfiguredSpring(SpringFacet spring)
    {
        facets << spring
    }

    public void addConfiguredWeb(WebFacet web)
    {
        facets << web
        web.setJavaModule(javaModule)
        web.setupDefaults()
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
