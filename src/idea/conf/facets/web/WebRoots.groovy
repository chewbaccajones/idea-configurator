package idea.conf.facets.web

import idea.conf.Visitable

/**
 * 
 */
public class WebRoots implements Visitable
{
    def contents = []

    def leftShift(WebRoot thing)
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