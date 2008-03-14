package idea.conf

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference

import idea.conf.render.DebugVisitor
import idea.conf.render.JavaImlVisitor
import idea.conf.depend.Dependencies
import idea.conf.depend.ClasspathFilter
import idea.conf.url.UrlFactory
import idea.conf.url.UrlFactoryImpl

/**
*
*
* @author tomichj
*/
class JavaModule extends Task implements Visitable
{
    File rootDir;
    private File moduleFile;
    boolean relativePaths = false;

    JavaComponent java;


    void execute()
    {
        super.execute();
        def debug = new DebugVisitor()
        debug.visit(this)
        println debug

        UrlFactory urlFactory = new UrlFactoryImpl(getRootDir(), relativePaths)
        def xml = new JavaImlVisitor(urlFactory);
        xml.visit(this)
        println xml
        println "\n"
    }

    
    void setProject(Project project)
    {
        super.setProject(project)
        java = new JavaComponent(project)
    }


    void setModuleFile(File moduleFile)
    {
        if (moduleFile == null) throw new BuildException("Null moduleFile!")
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

        // default is ant project name, .iws, in the base dir.
        return new File(project.getBaseDir(), project.getName() + ".iml")
    }



    /////////////////////////////////////////////////////////
    // Java stuff here
    

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


    Dependencies createDependencies()
    {
        return java.createDependencies();
    }


    ClasspathFilter createClasspathFilter()
    {
        return java.createClasspathFilter();
    }


    List<Visitable> getChildren()
    {
        return [java] as List<Visitable>;
    }

    String toString()
    {
        "JavaModule{" <<
                "rootDir=" << rootDir <<
                ", moduleFile=" << moduleFile << 
                ", relativePaths=" << relativePaths << "}"
    }
    
}