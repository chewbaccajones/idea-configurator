package idea.conf.depend

import idea.conf.Visitable

/**
*
*
* @author tomichj
*/
class ClasspathFilter implements Dependency
{
    String pattern;


//    void filter(ModuleLibrary lib)
//    {
//
//    }


    List<Visitable> getChildren()
    {
        return null;
    }

    void validate() {
        // check full null pattern?
    }


    String toString()
    {
        "ClasspathFilter{" << "pattern=" << pattern << "}"
    }
}