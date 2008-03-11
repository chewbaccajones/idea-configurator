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

import configurator.util.Validator;


/**
 * @author Justin Tomich
 */
public abstract class BaseCopyNamedLib implements DeploymentEntry
{
    protected String name;
    private String archivePath;
    protected boolean linkViaManifest = false;


    protected BaseCopyNamedLib()
    {
    }


    public void setName(String name)
    {
        this.name = name;
    }


    public void setArchivePath(String archivePath)
    {
        // todo validate? ensure it's an absolute path?
        this.archivePath = archivePath;
    }


    public void setLinkViaManifest(boolean linkViaManifest)
    {
        this.linkViaManifest = linkViaManifest;
    }


    protected void validate()
    {
        Validator.notEmpty(name, "name");
    }


    protected String archivePath()
    {
//        if (Validator.isNotEmpty(archivePath)) return archivePath;
//        if (linkViaManifest) return "/";
//        return "/WEB-INF/lib/";
        return new ArchivePath().libDirPath(archivePath, linkViaManifest);
    }
}
