package idea.conf

import org.apache.tools.ant.types.Path;


/**
 *
 *
 * @author tomichj
 */
class ModuleLibrary implements Dependency
{
    File classes

    boolean exported = false

    private Path sources;
    private Path javadocs;
    private List<String> javadocUrls = []


    void setSource(File sources)
    {
        this.sources.setLocation(sources)
    }


    void setJavadoc(File javadoc)
    {
        this.javadocs.setLocation(javadoc)
    }


    void setJavadocUrl(String javadocUrl)
    {
        javadocUrls.add(javadocUrl)
    }


    Path createSource()
    {
        return sources.createPath()
    }


    Path createJavadoc()
    {
        return javadocs.createPath()
    }


    List<Visitable> getChildren()
    {
        return null;
    }

    String toString()
    {
        "ModuleLibrary{exported=" << exported <<
                ", sources=" << sources <<
                ", javadocs=" << javadocs <<
                ", javadocUrls=" << javadocUrls << "}"
    }
}