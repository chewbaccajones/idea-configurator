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

package configurator.task;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.w3c.dom.Document;

import java.io.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


/**
 * Deals with getting the module's configuration rendered to xml and written
 * to disk.
 *
 * @author Justin Tomich
 */
public class ModuleFile
{
    private Project project;

    private File rootDir;
    private File moduleFile;
    private boolean relativePaths = true; // defaults to true yo!


    public ModuleFile(Project project)
    {
        this.project = project;
    }


    /**
     * Set the root directory of the Module. Not a required setting. Defaults to
     * ant's notion of the base dir.
     *
     * @param rootDir root directory of Module, as a File.
     */
    public void setRootDir(File rootDir)
    {
        this.rootDir = rootDir;
    }


    /**
     * Set the File for the module file to be written to. If null, a default
     * will be chosen based on the ant project name.
     *
     * @param moduleFile location of module configuration file to generate.
     */
    public void setModuleFile(File moduleFile)
    {
        if (!moduleFile.getAbsolutePath().endsWith(".iml"))
        {
            String p = moduleFile.getAbsolutePath() + ".iml";
            this.moduleFile = new File(p);
        }
        else
        {
            this.moduleFile = moduleFile;
        }
    }


    /**
     * Use relative paths for files outside module directory?
     *
     * @param relativePaths true if paths outside module dir should be relative
     */
    public void setRelativePaths(boolean relativePaths)
    {
        this.relativePaths = relativePaths;
    }


    public boolean relativePaths()
    {
        return relativePaths;
    }


    protected File rootDir()
    {
        if (rootDir != null) return rootDir;
        return project.getBaseDir();
    }


    protected String moduleFile()
    {
        if (moduleFile != null) return moduleFile.getAbsolutePath();

        // default is ant project name, .iws, in the base dir.
        String name = project.getName();
        File baseDir = project.getBaseDir();
        return new File(baseDir, name + ".iml").getAbsolutePath();
    }


    public void writeBuildFile(Document dom) throws BuildException
    {
        String pathToFile = moduleFile();
        String buildFile = serializeByTransformer(dom);
        writeToFile(buildFile, pathToFile);
    }


    protected File writeToFile(String contents, String pathToFile)
    {
        File f = new File(pathToFile);
        FileWriter writer;
        try
        {
            writer = new FileWriter(f);
            writer.write(contents);
            writer.flush();
            return f;
        }
        catch (IOException e)
        {
            throw new BuildException(e);
        }
    }


    /**
     * Pretty-print xml, as much as it possible with the included DOM
     * implementation. Generally this gets you appropriate newlines but not
     * indentation.
     *
     * @param doc DOM to serialize to text.
     * @return nicely formatted XML as a String
     * @throws org.apache.tools.ant.BuildException
     */
    private String serializeByTransformer(Document doc) throws BuildException
    {
        OutputStream out = new ByteArrayOutputStream();
        TransformerFactory tfactory = TransformerFactory.newInstance();
        Transformer serializer;
        try
        {
            serializer = tfactory.newTransformer();
            //Setup indenting to "pretty print"
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            serializer.setOutputProperty(
                    "{ http://xml.apache.org/xslt }indent-amount", "2");
            serializer.transform(new DOMSource(doc), new StreamResult(out));
            return out.toString();
        }
        catch (TransformerException e)
        {
            // this is fatal, just dump the stack and throw an exception
            e.printStackTrace();
            throw new BuildException(e);
        }
    }


    public String toString()
    {
        return "ModuleFile{" +
               "rootDir=" + rootDir +
               ", moduleFile=" + moduleFile +
               ", relativePaths=" + relativePaths +
               ", project=" + project +
               '}';
    }
}
