package idea.conf.facets

import idea.conf.Visitable

/**
 *
 *
 * @author tomichj
 */

public class SpringFacet implements Visitable 
{
    /**
     * <spring>
     *   <fileset name="">
     *     <file location="">
     *     <parent filesetName="">
     */

    def filesets = []

    
    void addConfiguredFiles(SpringFileset fileset)
    {
        filesets << fileset
        fileset.id = getId(fileset)
        fileset.parentFilesetId = getId(fileset.parentFilesetName)
    }

    String getId(SpringFileset fileset)
    {
        int counter = filesets.indexOf(fileset) + 1
        return "fileset" + counter
    }

    String getId(String filesetName)
    {
        if (filesetName == null) return null
        SpringFileset fileset = filesets.find{ it.name == filesetName }
        return getId(fileset)
    }

    public List<Visitable> getChildren()
    {
        return filesets
    }

    String toString()
    {
        "SpringFacet{filesets.size=" + filesets.size() + "}"
    }
}