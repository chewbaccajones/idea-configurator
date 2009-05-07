package idea.conf.build

import idea.conf.Visitable

/**
 *
 * User: tomichj
 * Date: May 6, 2009
 * Time: 1:25:02 PM
 */

public class Attribute implements Visitable
{
    def name
    def value

    public Attribute(name, PackageMethod method)
    {
        this.name = name
        this.value = method.getCode()
    }

    public Attribute(name, value)
    {
        this.name = name
        this.value = value
    }

    public List<Visitable> getChildren()
    {
        return null;
    }

    public String toString()
    {
        return "Attribute{" +
                "name=" + name +
                ", value=" + value +
                '}';
    }
}