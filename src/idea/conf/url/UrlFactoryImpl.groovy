package idea.conf.url

import org.apache.tools.ant.BuildException

/**
 * A factory for fun URL bajizzle. Knows how to do relative paths. Knows how to create
 * IDEA's fame $MODULE_DIR$ var. Etc.
 *
 * The relative path generation is hairy.
 *
 * @author tomichj
 */
class UrlFactoryImpl implements UrlFactory
{

    public static final String MODULE_DIR = '$MODULE_DIR$'
    public static final String APPLICATION_LIB_DIR = '$APPLICATION_HOME_DIR$/lib/'

    private File rootDir;
    private boolean relativeOutsideRoot;


    UrlFactoryImpl(File rootDir, boolean relativeOutsideRoot)
    {
        this.rootDir = rootDir;
        this.relativeOutsideRoot = relativeOutsideRoot;
    }

    public String ideaLib(String libName)
    {
        return APPLICATION_LIB_DIR + libName
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



    private FileUrl fileUrl(File path)
    {
        if (!path) return fileUrl("")
        return fileUrl(path.getAbsolutePath());
    }


    private FileUrl fileUrl(String path)
    {
        String computed = computePath(path)
        return new FileUrl(computed)
    }


    private JarUrl jarUrl(File file)
    {
        return jarUrl(file.getAbsolutePath());
    }


    private JarUrl jarUrl(String path)
    {
        // looks like these are always full paths, never relative
        return new JarUrl(path);
    }


    private HttpUrl httpUrl(String path)
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
    private String computePath(String path)
    {
        if (!path) return ""
        String relativePath = relativePath(path);
        if (relativeOutsideRoot) return relativePath;
        if (isOutsideModuleDir(relativePath)) return path;
        return relativePath;
    }


    private String relativePath(String path)
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
    private String relativePath(File home, File file)
    {
        List<String> homelist = pathParts(home);
        List<String> filelist = pathParts(file);
        return relativePath(homelist, filelist);
    }


    /**
     * Turn File into a List<String> of the parts of the path to the file or
     * directory.
     *
     * Note that the root directory will be listed as an empty
     * string, at least on unix systems.
     *
     * Path parts are returned in reverse order. So /foo/bar/baz is returned as
     * ["baz", "bar", "foo", ""].
     *
     * @param f file or directory to create path list for.
     * @return List < String >  of components forming path
     */
    private List<String> pathParts(File f)
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
     * Compute relative path from homePath to filePath.
     *
     * This is reasonably complicated. Anyone have a simpler algorithm?
     *
     * @param homePath home path
     * @param filePath full path to file
     * @return relative path to file
     */
    private String relativePath(List<String> homePath, List<String> filePath)
    {
        // start at the beginning of the lists
        // iterate while both lists are equal
        int homePathIndex = homePath.size() - 1;
        int filePathIndex = filePath.size() - 1;
        String relative = "";

        // first eliminate common root
        while ((homePathIndex >= 0) && (filePathIndex >= 0) &&
                (homePath.get(homePathIndex).equals(filePath.get(filePathIndex))))
        {
            homePathIndex--;
            filePathIndex--;
        }

        // for each remaining level in the home path, add a ".."
        while (homePathIndex >= 0)
        {
            relative += "../"
            homePathIndex--
        }

        // for each level in the file path, add the path
        while (filePathIndex >= 1)
        {
            relative += filePath.get(filePathIndex) + '/'
            filePathIndex--
        }

        // file name
        if (filePathIndex >= 0) relative += filePath.get(filePathIndex)
        return relative
    }


}

