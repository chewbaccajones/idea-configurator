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

import configurator.model.deployment.DeploymentComponent;
import configurator.util.Validator;

import java.io.File;


/**
 * Describes "general" xml descriptors, such as web.xml or persistence.xml.
 *
Á * @see configurator.task.deployment.WebDeploymentTask.Deployment#createWebXml()
 *
 * @author Justin Tomich
 */
public class FileDescriptor implements DescriptorSpec
{
    String name;
    String version;
    File file;


    public FileDescriptor(String name, String version)
    {
        this.name = name;
        this.version = version;
    }


    public void setFile(File file)
    {
        this.file = file;
    }


    public String getName()
    {
        return name;
    }


    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileDescriptor that = (FileDescriptor) o;
        if (name != null ? !name.equals(that.name) : that.name != null)
        {
            return false;
        }
        return true;
    }


    public int hashCode()
    {
        return (name != null ? name.hashCode() : 0);
    }


    public void configure(DeploymentComponent component)
    {
        validate();
        component.addDescriptor(name, file, version);
    }


    private void validate()
    {
        Validator.notEmpty(name, "name");
        Validator.notEmpty(version, "version");
        Validator.notEmpty(file, "file");
    }


    public String toString()
    {
        return "FileDescriptor{" +
               "name='" + name + '\'' +
               ", version='" + version + '\'' +
               ", file=" + file +
               '}';
    }
}
