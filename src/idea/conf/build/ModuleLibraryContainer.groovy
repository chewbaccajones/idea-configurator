package idea.conf.build

import idea.conf.Visitable


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