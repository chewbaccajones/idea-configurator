package idea.conf.build

import idea.conf.Visitable

/**
 * 
 */

public class GlobalLibraryContainer implements Package
{
    String name
    PackageMethod method
    String relativePath
    

    public List<Visitable> getChildren()
    {
        return [new Attribute("method", method),
                new Attribute("URI", relativePath)]
    }

    String toString()
    {
        "GlobalLibraryContainer{name=${name}}"
    }
}