package idea.conf.facets.web

import idea.conf.Visitable

/**
 *
 * User: tomichj
 * Date: Apr 28, 2009
 * Time: 2:27:16 PM
 */

public class Option implements Visitable
{
    String name
    String value


    def Option(name, value)
    {
        this.name = name;
        this.value = value;
    }

    public List<Visitable> getChildren()
    {
        return null;
    }

    public String toString()
    {
        return "Option{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

}
