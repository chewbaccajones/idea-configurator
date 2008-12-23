package idea.conf.facets

import idea.conf.Visitable

/**
 * Bundle of spring files.
 *
 * @todo should we add an "include"?
 * @todo should filesets be able to contain filesets? Would eliminate parentFilesetName.
 *
 * @author tomichj
 */
public class SpringFileset implements Visitable
{
    String name              // required
    String parentFilesetName

    String springFiles       // this or files

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
        def list
        if (springFiles)
        {
            def springFilesList = springFiles.split(",")
            list = springFilesList.collect {
                def f = new SpringFile()
                f.location = new File(it.trim())
                return f
            }
        }
        return files + list
    }

    String toString()
    {
        "SpringFileset{name=${name}, parentFilesetName=${parentFilesetName}," +
            "files.size=${files.size()}}"
    }
}
