package idea.conf.build

import org.apache.tools.ant.types.Path
import idea.conf.Visitable


public class ClasspathContainer extends Path implements Package
{
    PackageMethod defaultMethod
    String defaultRelativePath

    PackageMethod method
    String relativePath

    
    def ClasspathContainer(project, defaultMethod, defaultRelativePath)
    {
        super(project);
        this.defaultMethod = defaultMethod
        this.defaultRelativePath = defaultRelativePath
    }

    def defaultedMethod()
    {
        if (method) return method
        return defaultMethod
    }

    def defaultedRelativePath()
    {
        if (relativePath) return relativePath
        return defaultRelativePath
    }

    List<Visitable> getChildren()
    {
        return [new Attribute("method", defaultedMethod()),
                new Attribute("URI", defaultedRelativePath())]
    }

    String toString()
    {
        "ClasspathContainer{list=${list()}}"
    }
}
