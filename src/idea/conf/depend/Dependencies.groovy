package idea.conf

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference;


/**
 *
 *
 * @author tomichj
 */
class Dependencies implements Visitable
{
    def dependencies = []
    Project project;
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
        println "got farther!"
        return [dependencies]
    }


    IdeaLibrary createIdeaLib()
    {
        IdeaLibrary lib = new IdeaLibrary();
        dependencies.add(lib);
        return lib;
    }


    ModuleLibrary createModuleLibrary()
    {
        ModuleLibrary lib = new ModuleLibrary();
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
                "dependencies.size()=" + dependencies.size() +
                ", sourceProperty=" + sourceProperty +
                ", javadocProperty=" + javadocProperty +
                ", javadocUrlProperty=" + javadocUrlProperty +
                "}"
    }



}