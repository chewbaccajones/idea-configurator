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

import configurator.model.build.WebBuildComponent;
import configurator.model.deployment.WebPropertiesComponent;
import configurator.model.module.WebModule;
import configurator.model.rootmgr.NewModuleRootManagerComponent;
import configurator.task.build.BuildTask;
import configurator.task.deployment.WebDeploymentTask;
import configurator.task.java.JavaTask;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.Path;

import java.io.File;
import javax.xml.parsers.ParserConfigurationException;


/**
 * @author Justin Tomich
 */
public class IdeaWebModuleTask extends Task
{
    private ModuleFile moduleFile;
    private JavaTask javaTask;
    private BuildTask buildTask;
    private WebDeploymentTask deploymentTask;

    private boolean runJasperValidation;
    private Path excludeFromValidation;


    /**
     * Set the project. Will be done automagicaly by Ant, before other methods
     * are called. Set up attributes dependent upon the Project here.
     */
    public void setProject(Project project)
    {
        super.setProject(project);
        excludeFromValidation = new Path(project);
        moduleFile = new ModuleFile(project);
        javaTask = new JavaTask(project);
        buildTask = new BuildTask(javaTask);
        deploymentTask = new WebDeploymentTask(project);
    }


    public JavaTask createJava()
    {
        return javaTask;
    }


    public Build createBuild()
    {
        return new Build(); // no ref necessary, it holds no state itself
    }


    public WebDeploymentTask kscreateDeployment()
    {
        return deploymentTask;
    }

    ////////////////////////////////


    public void setRootDir(File rootDir)
    {
        moduleFile.setRootDir(rootDir);
    }


    public void setModuleFile(File moduleFile)
    {
        this.moduleFile.setModuleFile(moduleFile);
    }


    public void setRelativePaths(boolean relativePaths)
    {
        moduleFile.setRelativePaths(relativePaths);
    }


    public void execute() throws BuildException
    {
        super.execute();
        WebModule web = new WebModule(moduleFile.moduleFile(),
                moduleFile.rootDir(),
                moduleFile.relativePaths());

        NewModuleRootManagerComponent javaComponent = web.javaComponent();
        WebBuildComponent buildComponent = web.webBuild();
        WebPropertiesComponent propertiesComponent = web.webProperties();

        javaTask.configure(javaComponent);
        buildTask.configure(buildComponent);
        buildComponent.setRunJasperValidation(runJasperValidation);
        buildComponent.excludeFromValidation(excludeFromValidation.list());
        deploymentTask.configure(propertiesComponent);

        try
        {
            moduleFile.writeBuildFile(web.toDom());
        }
        catch (ParserConfigurationException e)
        {
            throw new BuildException(e);
        }

    }


    // <jasperValidator run="true">
    //   <exclude file="whatever.jsp"/
    // </jasperValidator>
    public class JasperValidator
    {
        public void setRun(boolean run)
        {
            runJasperValidation = run;
        }


        public Path createExcludes()
        {
            return excludeFromValidation.createPath();
        }
    }


    /**
     * Wrapper around BuildTask's War and Exploded, plus jasper validator
     */
    public class Build
    {
        public JasperValidator createJasperValidator()
        {
            return new JasperValidator();
        }


        public BuildTask.War createWar()
        {
            return buildTask.createWar();
        }


        public BuildTask.Exploded createExploded()
        {
            return buildTask.createExploded();
        }
    }

}
