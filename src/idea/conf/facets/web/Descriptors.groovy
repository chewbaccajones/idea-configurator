package idea.conf.facets.web

import idea.conf.Visitable

/**
 * 
 * User: tomichj
 * Date: Apr 29, 2009
 * Time: 4:43:03 PM
 */

public class Descriptors implements Visitable
{
    def contents = []

    def leftShift(def thing)
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