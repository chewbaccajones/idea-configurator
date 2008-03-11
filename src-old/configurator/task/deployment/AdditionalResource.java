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
import configurator.model.deployment.DeploymentComponent;

import java.io.File;


/**
 * An unknown resource (an xml file?) to be deployed in the archive.
 *
 * Usage as follows:
 * <pre>
 *   <ideawebmodule moduleFile="whatever.iml">
 *     <deployment>
 *       <additionalResource file="some.file" archivePath="/WEB-INF/some.file"/>
 *     </deployment>
 *   </ideawebmodule>
 * </pre>
 *
 * @author Justin Tomich
 */
public class AdditionalResource implements DescriptorSpec
{
    private File file;
    private String archivePath;


    public void setFile(File file)
    {
        this.file = file;
    }


    public void setArchivePath(String archivePath)
    {
        this.archivePath = archivePath;
    }


    public String getName()
    {
        return file.toString();
    }


    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdditionalResource that = (AdditionalResource) o;
        if (file != null ? !file.equals(that.file) : that.file != null)
        {
            return false;
        }
        return true;
    }


    public int hashCode()
    {
        return (file != null ? file.hashCode() : 0);
    }


    public void configure(DeploymentComponent component)
    {
        validate();
        component.addDescriptorUnknown(file, archivePath);
    }


    private void validate()
    {
        Validator.notEmpty(file, "file");
        Validator.notEmpty(archivePath, "archivePath");
    }


    public String toString()
    {
        return "AdditionalResource{" +
               "file=" + file +
               ", archivePath='" + archivePath + '\'' +
               '}';
    }


}
