package idea.conf

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference

import idea.conf.render.DebugVisitor
import idea.conf.render.ImlVisitor
import idea.conf.java.depend.Dependencies
import idea.conf.url.UrlFactory
import idea.conf.url.UrlFactoryImpl
import idea.conf.java.JavaComponent
import idea.conf.facets.FacetManagerComponent
import idea.conf.build.BuildComponent
import static idea.conf.Validator.*

/**
 * The java module type. This is the top level ant task for Java Modules.
 *
 * Most of the methods are proxying to JavaComponent.  
 *
 * @author tomichj
 */
class JavaModule extends Task implements Visitable
{
    File rootDir
    private File moduleFile
    boolean relativePaths = true
    Boolean stdoutOnly

    JavaComponent java // other components don't need member variable references
    def kids = []


    void execute()
    {
        super.execute();
        Logger.init(project)
        stdoutOnly = isStdoutOnly()

        def debuggery = new DebugVisitor()
        debuggery.visit(this)
        Logger.verbose(debuggery.toString() + "\n\n")


        UrlFactory urlFactory = new UrlFactoryImpl(getRootDir(), relativePaths)
        def xml = new ImlVisitor(urlFactory);
        xml.visit(this)

        if (stdoutOnly)
        {
            println xml
            println "\n"
        }
        else
        {
            getModuleFile().write(xml.toString())
        }
    }

    boolean isStdoutOnly()
    {
        Map m = project.getProperties()
        if (m.containsKey("stdout.only")) return true
        return false
    }

    /**
     * <facets>
     *   <groovy/>
     *   <spring/>
     * </facets>
     */
    void setProject(Project project)
    {
        super.setProject(project)
        java = new JavaComponent(project)
        kids << java
    }


    void setModuleFile(File moduleFile)
    {
        notNull(moduleFile, "Null moduleFile!")
        //if (moduleFile == null) huck("Null moduleFile!")
        if (!moduleFile.getAbsolutePath().endsWith(".iml"))
        {
            String p = moduleFile.getAbsolutePath() + ".iml";
            moduleFile = new File(p);
        }

        this.moduleFile = moduleFile;
    }


    File getRootDir()
    {
        if (rootDir != null) return rootDir;
        return project.getBaseDir();
    }


    File getModuleFile()
    {
        if (moduleFile != null) return moduleFile
        return new File(project.getBaseDir(), project.getName() + ".iml")
    }

    String getModuleName()
    {
        return getModuleFile().getName().replace('.iml', '')
    }


    def createFacets()
    {
        def facets = new FacetManagerComponent(this);
        kids << facets
        return facets;
    }

    
    BuildComponent createBuild()
    {
        def build = new BuildComponent(project)
        kids << build
        return build
    }
    

    /////////////////////////////////////////////////////////
    // Java stuff here
    // we could do some crazy property delegation stuff, but this might be simpler


    void setJarsToModules(String jarsToModules)
    {
        java.setJarsToModules jarsToModules 
    }

    void setSourceProperty(String sourceProperty)
    {
        java.setSourceProperty(sourceProperty);
    }


    void setJavadocProperty(String javadocProperty)
    {
        java.setJavadocProperty(javadocProperty);
    }


    void setJavadocUrlProperty(String javadocUrlProperty)
    {
        java.setJavadocUrlProperty(javadocUrlProperty);
    }


    void setSrcDir(File sourceDir)
    {
        java.setSrcDir(sourceDir);
    }


    void setSrcPathRef(Reference srcPathRef)
    {
        java.setSrcPathRef(srcPathRef);
    }


    Path createSrc()
    {
        return java.createSrc();
    }


    void setTestsDir(File testDir)
    {
        java.setTestsDir(testDir);
    }


    void setTestsPathRef(Reference testsPathRef)
    {
        java.setTestsPathRef(testsPathRef);
    }


    Path createTests()
    {
        return java.createTests();
    }


    void setInheritCompilerOutput(boolean inheritCompilerOutput) throws BuildException
    {
        java.setInheritCompilerOutput(inheritCompilerOutput);
    }


    void setExcludeOutputPaths(boolean excludeOutputPaths)
    {
        java.setExcludeOutputPaths(excludeOutputPaths);
    }


    void setOutputDir(File outputDir)
    {
        java.setOutputDir(outputDir);
    }


    void setTestsOutputDir(File testsOutputDir)
    {
        java.setTestsOutputDir(testsOutputDir);
    }


    void setExcludes(Path excludes)
    {
        java.setExcludes(excludes);
    }


    void setExcludesPathRef(Reference excludesPathRef)
    {
        java.setExcludesPathRef(excludesPathRef);
    }


    Path createExcludes()
    {
        return java.createExcludes();
    }


    void setClasspath(Path classpath)
    {
        java.setClasspath(classpath);
    }

    void setClasspathRef(Reference ref)
    {
        java.setClasspathRef(ref);
    }

    Dependencies createDependencies()
    {
        return java.createDependencies();
    }

    
    List<Visitable> getChildren()
    {
        return kids as List<Visitable>;
    }


    String toString()
    {
        "JavaModule{rootDir=${rootDir},moduleFile=moduleFile," +
                "relativePaths=${relativePaths}}"
    }
    
}
