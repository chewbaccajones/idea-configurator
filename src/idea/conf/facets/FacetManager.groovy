package idea.conf.facets

import idea.conf.Visitable

/**
 * Created by IntelliJ IDEA.
 * User: tomichj
 * Date: Jul 2, 2008
 * Time: 6:19:37 PM
 * To change this template use File | Settings | File Templates.
 */
class FacetManager implements Visitable
{
    def facets = []

    
    def createGroovy()
    {
        def groovy = new GroovyFacet()
        facets << groovy
        return groovy
    }
    

    public List<Visitable> getChildren()
    {
        return facets;
    }

}