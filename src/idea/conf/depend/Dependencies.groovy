package idea.conf.depend

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference
import idea.conf.Visitable;


/**
 *
 *
 * @author tomichj
 */
class Dependencies implements Visitable
{
    def dependencies = []
    Project project;

    /**
     * Set string that source properties must begin with to be automagically
     * identified as source for a corresponging library. Other ant properties
     * beginning with this string will be treated as a source path for a library
     * jar of the name: sourceProperty + "." + path-to-jar.
     * <p/>
     * Example: sourceProperty is set to "source". A jar in the classpath has
     * the path "/lib/some-library-1.0.jar". If another property exists with the
     * name "source./lib/some-library-1.0.jar", the source property will be
     * treated as the source accompanying "some-library-1.0.jar".
     */
    String sourceProperty
    
    String javadocProperty
    String javadocUrlProperty


    public Dependencies(Project project)
    {
        this.project = project
    }


    int size()
    {
        return dependencies.size()
    }


    List<Visitable> getChildren()
    {
        return dependencies
    }


    IdeaLibrary createIdeaLib()
    {
        IdeaLibrary lib = new IdeaLibrary(project);
        dependencies.add(lib);
        return lib;
    }


    ModuleLibrary createModuleLibrary()
    {
        ModuleLibrary lib = new ModuleLibrary(project);
        dependencies.add(lib);
        return lib;
    }


    ProjectLibrary createProjectLibrary()
    {
        ProjectLibrary lib = new ProjectLibrary();
        dependencies.add(lib);
        return lib;
    }


    GlobalLibrary createGlobalLibrary()
    {
        GlobalLibrary lib = new GlobalLibrary();
        dependencies.add(lib);
        return lib;
    }


    Jdk createJdk()
    {
        Jdk jdk = new Jdk();
        dependencies.add(jdk);
        return jdk;
    }


    ModuleSource createModuleSource()
    {
        ModuleSource source = new ModuleSource();
        dependencies.add(source);
        return source;
    }


    Module createModule()
    {
        Module module = new Module();
        dependencies.add(module);
        return module;
    }


    Classpath createClasspath()
    {
        Classpath cp = new Classpath(project);
        dependencies.add(cp);
        return cp;
    }


    String toString()
    {
        return "Dependencies{" +
                "sourceProperty=" + sourceProperty +
                ", javadocProperty=" + javadocProperty +
                ", javadocUrlProperty=" + javadocUrlProperty +
                ", dependencies.size()=" + dependencies.size() +
                "}"
    }



}