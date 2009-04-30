package idea.conf.build

import idea.conf.Visitable

/**
 * Created by IntelliJ IDEA.
 * User: tomichj
 * Date: Jan 6, 2009
 * Time: 2:27:02 PM
 * To change this template use File | Settings | File Templates.
 */

public class ModuleContainer implements Packaging 
{
    String name

    public List<Visitable> getChildren()
    {
        return null;
    }

    String toString()
    {
        "Module{name=${name}}"
    }
}