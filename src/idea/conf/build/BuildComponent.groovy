package idea.conf.build

import org.apache.tools.ant.types.Path
import org.apache.tools.ant.Project
import idea.conf.Visitable

/**
 *
 *
 * @author tomichj
 */

public class BuildComponent implements Visitable
{
    // default jar to moduleName.jar in module root dir 
    File jar

    String mainClass

    // modules to jar - list of modules
    boolean buildOnMake

    // as children:
    // modules and libraries

    // <modules name="foo, bar, baz"/>
    // <library>

    String modules
    Path libraries


    BuildComponent(Project project)
    {
        this.libraries = new Path(project)
    }

    void setLibraries(Path libraries)
    {
        libraries.createPath().append(libraries) 
    }

    Path createLibraries()
    {
        return libraries.createPath()
    }


    // add an "all dependencies" flag?
    // add a filter to all dependencies?
    
    List<Visitable> getChildren()
    {
        return null; // turn setting and libraries into children?
    }

    String toString()
    {
        "BuildComponent{jar=${jar},mainClass=${mainClass},buildOnMake=${buildOnMake}}"
    }
}