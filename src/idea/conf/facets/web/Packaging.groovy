package idea.conf.facets.web

import idea.conf.Visitable
import idea.conf.build.Package

/**
 * 
 * User: tomichj
 * Date: Apr 29, 2009
 * Time: 4:46:57 PM
 */

public class Packaging implements Visitable
{
    def contents = []

    //def hasModule()
    //{
    //    
    //}

    def leftShift(Package thing)
    {
        contents << thing
    }

    def size()
    {
        return contents.size
    }

    public List<Visitable> getChildren()
    {
        return contents
    }
}
