package idea.conf.build

import idea.conf.Visitable


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