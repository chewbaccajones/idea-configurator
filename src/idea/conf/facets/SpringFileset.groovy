package idea.conf.facets

import idea.conf.Visitable

/**
 *
 *
 * @author tomichj
 */

public class SpringFileset implements Visitable
{
    String name              // required
    String parentFilesetName
    protected String id
    protected String parentFilesetId
    def files = []

    
    SpringFile createFile()
    {
        def f = new SpringFile()
        files << f
        return f
    }

    public List<Visitable> getChildren()
    {
        return files
    }

    String toString()
    {
        "SpringFileset{name=${name}, parentFilesetName=${parentFilesetName}," +
            "files.size=${files.size()}}"
    }
}
