package idea.conf.java.depend

import idea.conf.Visitable
import org.apache.tools.ant.Project
import org.apache.tools.ant.types.Path

/**
 * Containes all Dependency instances, squirts some logic into the processing of them.
 *
 * @author tomichj
 */
class Dependencies implements Visitable
{
    Project project;
    def deps = []


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
    ModuleLibGenerator moduleLibGenerator
    

    public Dependencies(Project project, ModuleLibGenerator moduleLibGenerator)
    {
        this.project = project
        this.moduleLibGenerator = moduleLibGenerator
    }

    private Path list()
    {
        Path path = new Path(project)
        list(this, path)
        return path
    }

    private void list(Visitable obj, Path path)
    {
        if (obj == null) return
        if (obj instanceof JarToModule) return
        if (obj instanceof Classpathed) path.add(obj.getClasspath())
        def kids = obj.getChildren()
        kids?.each { list(it, path) }
    }


    int size()
    {
        return deps.size()
    }


    List<Visitable> getChildren()
    {
        completeJdkAndSourceOrderEntries()
        deps.each { it.validate() }
        return deps
    }

    
    JarToModule createJarToModule()
    {
        add new JarToModule(this)
    }
    

    void completeJdkAndSourceOrderEntries()
    {
        if (hasJdk() && hasSource()) return;
        if (hasSource())
        {
            putAfter(ModuleSource, new Jdk())
        }
        else if (hasJdk())
        {
            putBefore(Jdk, new ModuleSource())
        }
        else
        {
            deps.add(0, new ModuleSource())
            deps.add(1, new Jdk())
        }
    }


    def hasJdk()
    {
        deps.any { it instanceof Jdk }
    }


    def hasSource()
    {
        deps.any { it instanceof ModuleSource }
    }


    private void putAfter(Class clazz, Dependency insert)
    {
        def entry = deps.find { clazz.isInstance(it) }
        int index = deps.indexOf(entry)
        deps.add(index + 1, insert)
    }


    private void putBefore(Class clazz, Dependency insert)
    {
        def entry = deps.find { clazz.isInstance(it) }
        int index = deps.indexOf(entry)
        deps.add(index, insert)
    }


    ClasspathFilter createFilter()
    {
        add new ClasspathFilter()
    }


    IdeaLibrary createIdeaLib()
    {
        IdeaLibrary lib = new IdeaLibrary(project)
        lib.setLibGenerator(moduleLibGenerator)
        add lib
    }


    void addConfiguredModuleLibrary(ModuleLibrary lib)
    {
        lib.addLibraryAssets(moduleLibGenerator)
        deps << lib
    }

    
    ProjectLibrary createProjectLibrary()
    {
        add new ProjectLibrary()
    }


    GlobalLibrary createGlobalLibrary()
    {
        add new GlobalLibrary()
    }


    Jdk createJdk()
    {
        add new Jdk()
    }


    ModuleSource createModuleSource()
    {
        add new ModuleSource()
    }


    Module createModule()
    {
        add new Module()
    }


    Classpath createClasspath()
    {
        add new Classpath(project, moduleLibGenerator)
    }

    def filters()
    {
        deps.findAll { it instanceof Filter }
    }

    def add(dependency)
    {
        deps << dependency
        return dependency
    }

    String toString()
    {
        return "Dependencies{" +
                "deps.size()=" + deps.size() +
                "}"
    }


}