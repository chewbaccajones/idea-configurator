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

import org.apache.tools.ant.BuildException;

import java.io.File;
import java.util.Arrays;


/**
 * @author Justin Tomich
 */
public class VersionedDescriptor implements DescriptorSpec
{
    private final String name;
    private final String[] allowedVersions;
    private final String file;
    private String version;


    public VersionedDescriptor(String name, String[] allowedVersions,
                               String file)
    {
        this.name = name;
        this.allowedVersions = allowedVersions;
        this.file = file;
    }


    public String getName()
    {
        return name;
    }


    public void setVersion(String version)
    {
        this.version = version;
        for (String v : allowedVersions)
        {
            if (v.equals(version)) return;
        }
        throw new BuildException("Invalid version " + version + ". Valid:" +
                                 allowedVersions);
    }


    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VersionedDescriptor that = (VersionedDescriptor) o;
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
        component.addDescriptor(name, new File(file), version);
    }


    public String toString()
    {
        return "VersionedDescriptor{" +
               "name='" + name + '\'' +
               ", allowedVersions=" + Arrays.asList(allowedVersions) +
               ", file='" + file + '\'' +
               ", version='" + version + '\'' +
               '}';
    }
}