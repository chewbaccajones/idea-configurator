package idea.conf.render

import idea.conf.Visitable


/**
*
*
* @author tomichj
*/
class DebugVisitor  extends DefaultVisitor
{
    def out = new StringBuffer()

    
    private def indent()
    {
        return '\t' * getDepth();
    }

    void visit(Visitable v)
    {
        out << indent() << v << '\n'
        super.visit(v)
    }

    String toString()
    {
        return out.toString()
    }
}

