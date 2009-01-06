package idea.conf.build

import idea.conf.Visitable

/**
 * Created by IntelliJ IDEA.
 * User: tomichj
 * Date: Jan 6, 2009
 * Time: 2:26:31 PM
 * To change this template use File | Settings | File Templates.
 */

public class ModuleLibraryContainer implements Visitable
{
    String name

    public List<Visitable> getChildren()
    {
        return null;
    }

    String toString()
    {
        "ModuleLibrary{name=${name}}"
    }
}