package idea.conf.build

import idea.conf.Visitable


public class ProjectLibraryContainer implements Packaging
{
    String name

    public List<Visitable> getChildren()
    {
        return null;
    }

    String toString()
    {
        "ProjectLibrary{name=${name}}"
    }
}