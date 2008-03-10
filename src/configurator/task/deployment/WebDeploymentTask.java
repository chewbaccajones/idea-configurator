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
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

import java.util.ArrayList;
import java.util.List;


/**
 * Does deployment for web modules.
 * <p/>
 * This class may be pulled apart a bit more to support multiple deployment
 * types (ejb, application, etc, in addition to web).
 * <p/>
 * <p/>
 * <pre>
 * <deployment>
 *   <webXml file="WEB-INF/web.xml"/>
 *   <directory projectDir="resources" archiveDir="/"/>
 *   <directory projectDir="WEB-INF" archiveDir="/WEB-INF"/>
 *   <ignoremodule name="java"/>
 *   <copymodule name="java2" archivePath="/WEB-INF/classes"/>
 *   <copymodulelib path="some-lib.jar"/>
 *   <copygloballib name="ant-contrib"/>
 * </deployment>
 * </pre>
 * <p/>
 * <p/>
 *
 * @author Justin Tomich
 */
public class WebDeploymentTask extends Task
{
    // hmmm... maybe legal versions should be enforced in model instead???
    private static final String[] WEBLOGIC_VERSIONS =
            new String[] {"6.x", "7.x", "8.x", "9.x"};
    private static final String[] GLASSFISH_VERSIONS =
            new String[] {"2.3.0", "2.3.1", "2.4.0", "2.4.1", "2.5.0"};
    private static final String[] JBOSS_VERSIONS = new String[] {"3.2", "4.0"};
    private static final String[] GERONIMO_VERSIONS =
            new String[] {"1.0", "1.1"};
    private static final String[] TOMCAT_VERSIONS = new String[] {"5.x"};

    // consider keeping one huge collection1wfb

    private Project project;
    private List<DeploymentEntry> entries = new ArrayList<DeploymentEntry>();


    public WebDeploymentTask(Project project)
    {
        this.project = project;
    }


    public void configure(DeploymentComponent component)
    {
        for (DeploymentEntry entry : entries)
        {
            entry.configure(component);
        }
    }


    void add(DescriptorSpec d)
    {
        if (entries.contains(d))
        {
            throw new BuildException(
                    "Duplicate descriptors for " + d.getName());
        }
        entries.add(d);
    }


    public FileDescriptor createWebXml()
    {
        FileDescriptor xml = new FileDescriptor("web.xml", "2.4");
        add(xml);
        return xml;
    }


    public FileDescriptor createOrmXml()
    {
        FileDescriptor xml = new FileDescriptor("orm.xml", "1.0");
        add(xml);
        return xml;
    }


    public FileDescriptor createPersistenceXml()
    {
        FileDescriptor xml = new FileDescriptor("persistence.xml", "1.0");
        add(xml);
        return xml;
    }


    public VersionedDescriptor createWeblogic()
    {
        VersionedDescriptor xml = new VersionedDescriptor("weblogic.xml",
                WEBLOGIC_VERSIONS, "WEB-INF/weblogic.xml");
        add(xml);
        return xml;
    }


    public VersionedDescriptor createGlassfish()
    {
        VersionedDescriptor xml = new VersionedDescriptor("sun-web.xml",
                GLASSFISH_VERSIONS, "WEB-INF/sun-web.xml");
        add(xml);
        return xml;
    }


    public VersionedDescriptor createJBoss()
    {
        VersionedDescriptor xml = new VersionedDescriptor("jboss-web.xml",
                JBOSS_VERSIONS, "WEB-INF/jboss-web.xml");
        add(xml);
        return xml;
    }


    public VersionedDescriptor createGeronimo()
    {
        VersionedDescriptor xml = new VersionedDescriptor("geronimo-web.xml",
                GERONIMO_VERSIONS, "WEB-INF/geronimo-web.xml");
        add(xml);
        return xml;
    }


    public VersionedDescriptor createTomcat()
    {
        VersionedDescriptor xml = new VersionedDescriptor("context.xml",
                TOMCAT_VERSIONS, "WEB-INF/context.xml");
        add(xml);
        return xml;
    }


    public AdditionalResource createAdditionalResource()
    {
        AdditionalResource res = new AdditionalResource();
        add(res);
        return res;
    }


    public IgnoreModule createIgnoreModule()
    {
        IgnoreModule m = new IgnoreModule();
        entries.add(m);
        return m;
    }


    public CopyModule createCopyModule()
    {
        CopyModule m = new CopyModule();
        entries.add(m);
        return m;
    }


    public JarModule createJarModule()
    {
        JarModule m = new JarModule();
        entries.add(m);
        return m;
    }


    public IgnoreModuleLib createIgnoreModuleLib()
    {
        IgnoreModuleLib l = new IgnoreModuleLib();
        entries.add(l);
        return l;
    }


    public IgnoreProjectLib createProjectModuleLib()
    {
        IgnoreProjectLib l = new IgnoreProjectLib();
        entries.add(l);
        return l;
    }


    public IgnoreGlobalLib createIgnoreGlobalLib()
    {
        IgnoreGlobalLib l = new IgnoreGlobalLib();
        entries.add(l);
        return l;
    }


    public CopyModuleLib createCopyModuleLib()
    {
        CopyModuleLib c = new CopyModuleLib();
        entries.add(c);
        return c;
    }


    public CopyModuleLibPath createCopyModuleLibPath()
    {
        CopyModuleLibPath c = new CopyModuleLibPath(project);
        entries.add(c);
        return c;
    }


    public CopyGlobalLib createCopyGlobalLib()
    {
        CopyGlobalLib c = new CopyGlobalLib();
        entries.add(c);
        return c;
    }


    public CopyProjectLib createCopyProjectLib()
    {
        CopyProjectLib c = new CopyProjectLib();
        entries.add(c);
        return c;
    }


    public Directory createDirectory()
    {
        Directory d = new Directory();
        entries.add(d);
        return d;
    }


    public String toString()
    {
        return "WebDeploymentTask{" +
               "project=" + project +
               ", entries=" + entries +
               '}';
    }
}