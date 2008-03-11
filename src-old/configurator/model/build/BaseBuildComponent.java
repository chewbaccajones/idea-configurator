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

package configurator.model.build;

import configurator.model.url.UrlFactory;
import configurator.util.ElementCreator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;


/**
 * Base behavior for all the various flavors of "build". Concrete
 * implementations provide module-specific derivations.
 *
 * @author Justin Tomich
 */
abstract class BaseBuildComponent implements BuildPropertiesComponent
{
    private final String name;
    private final UrlFactory urlFactory;

    private final FileSetting explodedUrl = file("EXPLODED_URL");
    private final BooleanSetting explodedEnabled = bool("EXPLODED_ENABLED");
    private final FileSetting jarUrl = file("JAR_URL");
    private final BooleanSetting jarEnabled = bool("JAR_ENABLED");
    private final BooleanSetting syncExplodedDir = bool("SYNC_EXPLODED_DIR");
    private final BooleanSetting buildModuleOnFrameDeactivation =
            bool("BUILD_MODULE_ON_FRAME_DEACTIVATION");
    private final BooleanSetting buildExternalDependencies =
            bool("BUILD_EXTERNAL_DEPENDENCIES");

    private Setting[] settings = new Setting[] {
            explodedUrl, explodedEnabled, jarUrl, jarEnabled, syncExplodedDir,
            buildModuleOnFrameDeactivation, buildExternalDependencies};

    private List<ExcludedFile> excludedFiles = new ArrayList<ExcludedFile>();


    protected BaseBuildComponent(String name, UrlFactory urlFactory)
    {
        this.name = name;
        this.urlFactory = urlFactory;
    }


    public void setExplodedDir(String path)
    {
        explodedUrl.setFile(urlFactory.fileUrl(path));
    }


    public void setExplodeEnabled(boolean enabled)
    {
        explodedEnabled.setValue(enabled);
    }


    public void setJarFile(String path)
    {
        jarUrl.setFile(urlFactory.fileUrl(path));
    }


    public void setJarEnabled(boolean enabled)
    {
        jarEnabled.setValue(enabled);
    }


    public void setSyncExplodedDir(boolean enabled)
    {
        syncExplodedDir.setValue(enabled);
    }


    public void setBuildModuleOnFrameDeactivation(boolean enabled)
    {
        buildModuleOnFrameDeactivation.setValue(enabled);
    }


    public void setBuildExternalDependencies(boolean enabled)
    {
        buildExternalDependencies.setValue(enabled);
    }


    public void excludeFromValidation(String... paths)
    {
        for (String path : paths)
        {
            excludedFiles.add(new ExcludedFile(urlFactory.fileUrl(path)));
        }
    }


    protected FileSetting file(String name)
    {
        return new FileSetting(name);
    }


    protected BooleanSetting bool(String name)
    {
        return new BooleanSetting(name);
    }

    // EXPLODED_URL FileUrl
    // EXPLODED_ENABLED boolean
    // JAR_URL FileUrl
    // JAR_ENABLED boolean
    // SYNC_EXPLODED_DIR boolean
    // BUILD_MODULE_ON_FRAME_DEACTIVATION boolean
    // BUILD_EXTERNAL_DEPENDENCIES boolean

    // RUN_JASPER_VALIDATION boolean
    // RUN_EJB_VALIDATION" boolean

    // <setting name="EXPLODED_URL" value="file://$MODULE_DIR$/exploded" />
    // <setting name="EXPLODED_ENABLED" value="true" />

    // <excludedFromValidation>
    //   <file url="file://$MODULE_DIR$/resources/Foobly.jsp" />
    // </excludedFromValidation>


    protected abstract Setting[] additionalSettings();


    public void addToDom(Element parent)
    {
//        Document dom = parent.getOwnerDocument();
//        Element component = dom.createElement("component");
//        parent.appendChild(component);

        Element component = new ElementCreator("component", parent).create();
        component.setAttribute("name", name);

        for (Setting setting : settings)
        {
            setting.addToDom(component);
        }

        Setting[] more = additionalSettings();
        for (Setting setting : more)
        {
            setting.addToDom(component);
        }

        if (excludedFiles.size() > 0)
        {
            addExcludes(component);
        }

    }


    private void addExcludes(Element component)
    {
        Document dom = component.getOwnerDocument();
        Element excludedElem = dom.createElement("excludedFromValidation");
        for (ExcludedFile excludedFile : excludedFiles)
        {
            excludedFile.addToDom(excludedElem);
        }
        excludedElem.appendChild(component);
    }

//    public String toString()
//    {
//        return "BuildComponent{" +
//               "name='" + name + '\'' +
//               ", explodedUrl=" + explodedUrl +
//               ", explodedEnabled=" + explodedEnabled +
//               ", jarUrl=" + jarUrl +
//               ", jarEnabled=" + jarEnabled +
//               ", syncExplodedDir=" + syncExplodedDir +
//               ", buildModuleOnFrameDeactivation=" +
//               buildModuleOnFrameDeactivation +
//               ", buildExternalDependencies=" + buildExternalDependencies +
//               ", settings=" +
//               (settings == null ? null : Arrays.asList(settings)) +
//               '}';
//    }
}
