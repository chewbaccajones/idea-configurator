package idea.conf.build

import idea.conf.Visitable

/**
 * Created by IntelliJ IDEA.
 * User: tomichj
 * Date: Jan 6, 2009
 * Time: 2:26:31 PM
 * To change this template use File | Settings | File Templates.
 */

public class ModuleLibraryContainer implements Package
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
        "ModuleLibraryContainer{name=${name}}"
    }
}