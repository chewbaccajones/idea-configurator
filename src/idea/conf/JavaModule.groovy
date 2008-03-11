package idea.conf

import org.apache.tools.ant.Task;

/**
 *
 *
 * @author tomichj
 */
class JavaModule extends Task
{
    private File rootDir;
    private File moduleFile;
    private boolean relativePaths = true; // defaults to true yo!


    void setProject(Project project)
    {
    }

    void setRootDir(File rootDir)
    {
        this.rootDir = rootDir;
    }

    void setModuleFile(File moduleFile)
    {
        if (!moduleFile.getAbsolutePath().endsWith(".iml"))
        {
            String p = moduleFile.getAbsolutePath() + ".iml";
            moduleFile = new File(p);
        }

        this.moduleFile = moduleFile;
    }

    void setRelativePaths(boolean relativePaths)
    {
        this.relativePaths = relativePaths;
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

    
}