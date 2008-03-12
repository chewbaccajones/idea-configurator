package idea.conf
/**
 *
 *
 * @author tomichj
 */
class IdeaLibrary
{
    String name


    public List<Visitable> getChildren()
    {
        return null;
    }

    String toString()
    {
        return "IdeaLibrary{" << "name=" << name << "}"
    }
}