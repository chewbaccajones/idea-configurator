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

package configurator.task.deployment;

import static configurator.util.Validator.*;
import configurator.model.deployment.DeploymentComponent;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference;

import java.io.File;


/**
 * Copy the given path of module library jars upon deployment.
 * <p/>
 * Note that archiveDir is the directory in the archive, relative to the root of
 * the archive, that the jars should be copied to. Again it's a directory in the
 * archive, and all of the jars in the path will be copied to this directory.
 *
 * @author Justin Tomich
 */
public class CopyModuleLibPath implements Library
{
    private Project project;
    private Path path;
    private String archiveDir;
    private boolean linkViaManifest = false;


    public CopyModuleLibPath(Project project)
    {
        this.project = project;
    }


    public void setPath(Path path)
    {
        assertPathNotSet();
        this.path = path;
    }


    public void setRefid(Reference r)
    {
        assertPathNotSet();
        this.path = new Path(project);
        path.createPath().setRefid(r);
    }


    private void assertPathNotSet()
    {
        if (isNotEmpty(path)) huck("set either path or refid. " + this);
    }


    public void setArchiveDir(String archiveDir)
    {
        this.archiveDir = archiveDir;
    }


    public void setLinkViaManifest(boolean linkViaManifest)
    {
        this.linkViaManifest = linkViaManifest;
    }


    private void validate()
    {
        notEmpty(path, "path");
    }


    public void configure(DeploymentComponent component)
    {
        validate();
        String[] jars = path.list();
        for (String jar : jars)
        {
            File jarFile = new File(jar);
            String jarName = jarFile.getName();
            component.copyModuleLibrary(jarFile, archivePath(jarName),
                    linkViaManifest);
        }
    }


    private String archivePath(String jarName)
    {
//        if (isNotEmpty(archiveDir)) return endWithSlash(archiveDir) + jarName;
//        if (linkViaManifest) return "/" + jarName;
//        return "/WEB-INF/lib/" + jarName;

        return new ArchivePath().
                archiveDirJarPath(archiveDir, linkViaManifest, jarName);
    }

//    private String completeArchivePath(String jarName)
//    {
//        if (isNotEmpty(archiveDir)) return endWithSlash(archiveDir) + jarName;
//        return null;
//    }
//
//
//    private String endWithSlash(String s)
//    {
//        if (s.endsWith("/")) return s;
//        return s + "/";
//    }


    public String toString()
    {
        return "CopyModuleLibPath{" +
               "path=" + path +
               ", archiveDir='" + archiveDir + '\'' +
               ", linkViaManifest=" + linkViaManifest +
               '}';
    }
}

