package idea.conf.url

import org.apache.tools.ant.BuildException

/**
 *
 *
 * @author tomichj
 */
class UrlFactoryImpl implements UrlFactory
{
    public static final String MODULE_DIR = '$MODULE_DIR$'

    private File rootDir;
    private boolean relativeOutsideRoot;


    UrlFactoryImpl(File rootDir, boolean relativeOutsideRoot)
    {
        this.rootDir = rootDir;
        this.relativeOutsideRoot = relativeOutsideRoot;
    }


    public String file(File path)
    {
        return fileUrl(path).url()
    }

    public String file(String path)
    {
        return fileUrl(path).url()
    }

    public String http(String path)
    {
        return httpUrl(path).url()
    }

    public String jar(File path)
    {
        return jarUrl(path).url()
    }

    public String jar(String path)
    {
        return jarUrl(path).url()
    }



    FileUrl fileUrl(File path)
    {
        println "ONE ${path}"
//        if (path == null) throw new BuildException("Null path!")
        if (!path) return fileUrl("")
        return fileUrl(path.getAbsolutePath());
    }


    FileUrl fileUrl(String path)
    {
        println "TWO ${path}"
        String computed = computePath(path)
        return new FileUrl(computed)
//        String relative = relativePath(path);
//        return new FileUrl(relative)
    }


    JarUrl jarUrl(File file)
    {
        return jarUrl(file.getAbsolutePath());
    }


    JarUrl jarUrl(String path)
    {
        // looks like these are always full paths, never relative
        return new JarUrl(path);
    }


    HttpUrl httpUrl(String path)
    {
        return new HttpUrl(path);
    }


    /**
     * Return path. Will test to see if path is outside root dir, and if
     * relativeOutsideRoot is set will return relative path, otherwise will
     * return full path.
     *
     * @param path path to be comptuted as relative path, if appropriate.
     * @return relative or full path, as a String
     */
    String computePath(String path)
    {
        println "path=${path}"
        if (!path) return ""
        String relativePath = relativePath(path);
        if (relativeOutsideRoot) return relativePath;
        if (isOutsideModuleDir(relativePath)) return path;
        return relativePath;
    }


    String relativePath(String path)
    {
        String relative = relativePath(rootDir, new File(path));
        return MODULE_DIR + "/" + relative;
    }


    private boolean isOutsideModuleDir(String relativePath)
    {
        return relativePath.contains("..");
    }


    /**
     * Get relative path of File 'f' with respect to 'home' directory. Always
     * builds a relative path.
     *
     * @param home root directory (not file) to computer relative path from.
     * @param file relative path is generated for this file/directory
     * @return path from home to f as a string
     */
    String relativePath(File home, File file)
    {
        //List<String> homelist;
        //List<String> filelist;
        //
        //homelist = getPathList(home);
        //filelist = getPathList(file);
        List<String> homelist = getPathList(home);
        List<String> filelist = getPathList(file);
        return matchPathLists(homelist, filelist);
    }


    /**
     * Turn File into a List<String> of the parts of the path to the file or
     * directory. Note that the root directory will be listed as an empty
     * string, at least on unix systems.
     *
     * @param f file or directory to create path list for.
     * @return List < String >  of components forming path
     */
    private List<String> getPathList(File f)
    {
        try
        {
            List<String> pathParts = new ArrayList<String>();
            File r = f.getCanonicalFile();
            while (r != null)
            {
                pathParts.add(r.getName());
                r = r.getParentFile();
            }
            return pathParts;
        }
        catch (IOException e)
        {
            throw new BuildException("Bad path problem!", e);
        }
    }


    /**
     * Compute relative path to filePath from homePath.
     *
     * todo this is a horror. For the love of all that is holy, do something!
     *
     * @param homePath home path
     * @param filePath full path to file
     * @return relative path to file
     */
    private String matchPathLists(List<String> homePath, List<String> filePath)
    {
        // start at the beginning of the lists
        // iterate while both lists are equal
        int homePathSize = homePath.size() - 1;
        int filePathSize = filePath.size() - 1;
        String relative = "";

        // first eliminate common root
        while ((homePathSize >= 0) && (filePathSize >= 0) &&
                (homePath.get(homePathSize).equals(filePath.get(filePathSize))))
        {
            homePathSize--;
            filePathSize--;
        }

        // for each remaining level in the home path, add a ".."
        while (homePathSize >= 0)
        {
            relative += "../"
            homePathSize--
        }

        // for each level in the file path, add the path
        while (filePathSize >= 1)
        {
            relative += filePath.get(filePathSize) + '/'
            filePathSize--
        }

        // file name
        relative += filePath.get(filePathSize)
        return relative
    }


}

