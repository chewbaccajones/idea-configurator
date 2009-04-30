package idea.conf.facets.web

import idea.conf.Visitable

/**
 * 
 * User: tomichj
 * Date: Apr 29, 2009
 * Time: 4:48:52 PM
 */

public class WebRoots implements Visitable
{
    def contents = []

    def leftShift(thing)
    {
        contents << thing
    }

    def size()
    {
        return contents.size
    }

    public List<Visitable> getChildren()
    {
        return contents;
    }
}