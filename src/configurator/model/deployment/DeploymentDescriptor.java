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

package configurator.model.deployment;

import configurator.model.XmlElement;
import static configurator.model.deployment.DeploymentDescriptorOption.Name;
import configurator.model.url.FileUrl;
import configurator.util.ElementCreator;

import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;


/**
 * <deploymentDescriptor name="web.xml" url="file://$MODULE_DIR$/WEB-INF/web.xml"
 * optional="false" version="2.4" />
 *
 * @author Justin Tomich
 */
public class DeploymentDescriptor implements XmlElement
{
    private String name;
    private FileUrl url;
    private boolean optional = false;
    private String version;
    private List<DeploymentDescriptorOption> options =
            new ArrayList<DeploymentDescriptorOption>();


    public DeploymentDescriptor(String name, FileUrl url, String version)
    {
        this.name = name;
        this.url = url;
        this.version = version;
    }


    /**
     * Constructor for "unknown" deployment descriptors. Might push out to
     * component.
     */
    public DeploymentDescriptor(String name, FileUrl url, String archivePath,
                                String description)
    {
        this.name = name;
        this.url = url;
        options.add(new DeploymentDescriptorOption(Name.DEFAULT_DIR,
                archivePath));
        options.add(new DeploymentDescriptorOption(Name.NAME, name));
        options.add(new DeploymentDescriptorOption(Name.DESCRIPTION,
                description));
    }


    public void add(DeploymentDescriptorOption option)
    {
        options.add(option);
    }


    public void addToDom(Element parent)
    {
//        Document dom = parent.getOwnerDocument();
//        Element e = dom.createElement("deploymentDescriptor");
//        parent.appendChild(e);

        Element e = new ElementCreator("deploymentDescriptor", parent).create();
        url.setAttribute(e);
        e.setAttribute("name", name);
        e.setAttribute("optional", optional + "");
        if (version != null) e.setAttribute("version", version);

        for (DeploymentDescriptorOption option : options)
        {
            option.addToDom(e);
        }

    }


    public String toString()
    {
        return "DeploymentDescriptor{" +
               "name='" + name + '\'' +
               ", url=" + url +
               ", optional=" + optional +
               ", version='" + version + '\'' +
               '}';
    }
}
