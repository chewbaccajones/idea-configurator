package idea.conf.render

import idea.conf.Visitable

/**
* Default implementation that doesn't actually do anything, other than count it's depth.
*
* @author tomichj
*/
class DefaultVisitor
{
    /** how deep are we into the structure we're visiting? use for indentation, etc. */
    int depth = 0;


    void visit(Visitable v)
    {
        doIteration(v);
    }

    void doIteration(Visitable v)
    {
        depth++
        v?.children.each { visit(it) };
        depth--
    }
    
}

