package idea.conf.build

import idea.conf.Visitable

/**
 * 
 */

public class GlobalLibraryContainer implements Visitable
{
    String name

    public List<Visitable> getChildren()
    {
        return null;
    }

    String toString()
    {
        "GlobalLibrary{name=${name}}"
    }
}