/**
 IDEA Configurator
 Copyright (C) 2006 Justin Tomich<tomichj at gmail dot com>

 This program is free software; you can redistribute it and/or
 modify it under the terms of the GNU General Public License as
 published by the Free Software Foundation; either version 2 of the
 License, or (at your option) any later version.

 This program is distributed in the hope that it will be useful, but
 WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package configurator.model.url;

import org.apache.tools.ant.BuildException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;


/**
 * Generate a Url as a path relative to the supplied rootDir. Relative paths are
 * generated only for paths-on-disk.
 *
 * @author Justin Tomich
 */
public class UrlFactoryImpl implements UrlFactory
{
    public static final String MODULE_DIR = "$MODULE_DIR$";

    private File rootDir;
    private boolean relativeOutsideRoot;


    public UrlFactoryImpl(File rootDir, boolean relativeOutsideRoot)
    {
        this.rootDir = rootDir;
        this.relativeOutsideRoot = relativeOutsideRoot;
    }


    public FileUrl fileUrl(File path)
    {
        return fileUrl(path.getAbsolutePath());
    }


    public FileUrl fileUrl(String path)
    {
        String relative = relativePath(path);
        return new FileUrl(relative);
    }


    public JarUrl jarUrl(File file)
    {
        return jarUrl(file.getAbsolutePath());
    }


    public JarUrl jarUrl(String path)
    {
        // looks like these are always full paths, never relative
        return new JarUrl(path);
    }


    public HttpUrl httpUrl(String path)
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
    protected String computePath(String path)
    {
        String relativePath = relativePath(path);
        if (relativeOutsideRoot) return relativePath;
        if (isOutsideModuleDir(relativePath)) return path;
        return relativePath;
    }


    protected String relativePath(String path)
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
    protected String relativePath(File home, File file)
    {
        List<String> homelist;
        List<String> filelist;

        homelist = getPathList(home);
        filelist = getPathList(file);
        return matchPathLists(homelist, filelist);
    }


    /**
     * Turn File into a List<String> of the parts of the path to the file or
     * directory. Note that the root directory will be listed as an empty
     * string, at least on unix systems.
     *
     * @param f file or directory to create path list for.
     * @return List<String> of components forming path
     */
    private List<String> getPathList(File f)
    {
        List<String> pathParts = new ArrayList<String>();
        try
        {
            File r = f.getCanonicalFile();
            while (r != null)
            {
                pathParts.add(r.getName());
                r = r.getParentFile();
            }
        }
        catch (IOException e)
        {
            throw new BuildException("Bad path problem!", e);
        }

        return pathParts;
    }


    /**
     * figure out a string representing the relative path of filePath with
     * respect to homePath.
     * <p/>
     * todo this is a horror. For the love of all that is holy, do something!
     *
     * @param homePath home path
     * @param filePath path of file
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
        for (; homePathSize >= 0; homePathSize--)
        {
            relative += "../";
        }

        // for each level in the file path, add the path
        for (; filePathSize >= 1; filePathSize--)
        {
            relative += filePath.get(filePathSize) + '/';
        }

        // file name
        relative += filePath.get(filePathSize);
        return relative;
    }


}
