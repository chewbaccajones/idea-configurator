package idea.conf.render

import groovy.xml.MarkupBuilder

import idea.conf.java.JavaComponent
import idea.conf.java.depend.Dependencies
import idea.conf.java.depend.Jdk
import idea.conf.java.depend.ModuleSource
import idea.conf.java.depend.ProjectLibrary
import idea.conf.java.depend.GlobalLibrary
import idea.conf.java.depend.IdeaLibrary
import idea.conf.java.depend.Module
import idea.conf.java.depend.Exportable
import idea.conf.java.depend.Classpath
import idea.conf.url.UrlFactory
import idea.conf.url.FileUrl
import idea.conf.facets.GroovyFacet
import idea.conf.facets.FacetManager
import org.apache.tools.ant.types.Path
import idea.conf.java.depend.ModuleLibraryType
import idea.conf.facets.GwtFacet
import idea.conf.facets.SpringFacet
import idea.conf.facets.SpringFileset
import idea.conf.facets.SpringFile
import idea.conf.build.BuildComponent
import idea.conf.build.ClasspathContainer
import idea.conf.build.GlobalLibraryContainer
import idea.conf.build.ModuleContainer
import idea.conf.build.ModuleLibraryContainer
import idea.conf.build.ProjectLibraryContainer
import idea.conf.Logger
import idea.conf.facets.web.WebFacet
import idea.conf.facets.web.Descriptor
import idea.conf.facets.web.WebRoot
import idea.conf.facets.web.Option

/**
* The JavaImlVisitor creates an iml file for java projects.
*
* @author tomichj
*/
class ImlVisitor extends DefaultVisitor
{
    // todo move to JavaModule ??
    static final int VERSION = 4;
    static final String JAVA_TYPE = "JAVA_MODULE";

    private final StringWriter writer = new StringWriter();
    private final MarkupBuilder xml = new MarkupBuilder(writer);
    private final UrlFactory urls;

    def filters = []
    

    ImlVisitor(UrlFactory urlFactory)
    {
        this.urls = urlFactory
    }


    void visit(idea.conf.JavaModule module)
    {
        xml.module(relativePaths:module.relativePaths, type:JAVA_TYPE, version:VERSION) {
            super.visit(module)
        }
    }


    // eventually we'll extract the facets, i think
    void visit(FacetManager facets)
    {
        xml.component(name:"FacetManager") {
            super.visit(facets)
        }
    }


    void visit(SpringFacet spring)
    {
        xml.facet(type:"Spring", name:"Spring") {
            xml.configuration() {
                super.visit(spring)
            }
        }
    }

    void visit(SpringFileset fileset)
    {
        xml.fileset(id:fileset.id, name:fileset.name, removed:false) {
            if (fileset.parentFilesetName) {
                xml.dependency(fileset.parentFilesetId)
            }
            super.visit(fileset)
        }
    }

    void visit(SpringFile file)
    {
        String url = urls.file(file.location)
        xml.file(url) {
            super.visit(file)
        }
    }


    void visit(GroovyFacet groovy)
    {
        xml.facet(type:"Groovy", name:"Groovy") {
            xml.configuration()
        }
    }

    void visit(GwtFacet gwt)
    {
        String path = gwt.gwtSdkUrl != null ? gwt.gwtSdkUrl.absolutePath : ""
        def sdkUrl = new FileUrl(path)

        xml.facet(type:"gwt", name:"GWT") {
            setting("additionalCompilerParameters", gwt.compilerParams)
            setting("compilerMaxHeapSize", gwt.compilerMaxHeap)
            setting("compilerOutputPath", gwt.compilerOutputPath)
            setting("gwtScriptOutputStyle", gwt.outputStyle)
            setting("gwtSdkUrl", sdkUrl.url())
            setting("runGwtCompilerOnMake", gwt.runGwtCompilerOnMake)
            if (gwt.intoWebFacet) setting("webFacet", gwt.intoWebFacet)
        }
    }

    void visit(WebFacet web)
    {
        xml.facet(type:"web", name:"web") {
            xml.configuration() {
                xml.descriptors() {
                    web.descriptors.each { visit((Descriptor)it)  }
                }
                xml.webroots() {
                    web.webRoots.each { visit((WebRoot)it)}
                }
                xml.building(){
                    setting('EXPLODED_URL', web.explodedDir)
                    setting('EXPLODED_ENABLED', web.explodedDir != null)
                    setting('JAR_URL', web.war)
                    setting('JAR_ENABLED', web.war != null)
                    setting('EXCLUDE_EXPLODED_DIRECTORY', web.excludeExploded)
                }
                xml.packaging() {
                    web.packaging.each { visit(it) }
                }
            }
        }
    }

    void visit(Descriptor d)
    {
        println "DESCRIPTOR:" + d
        def args = [:]
        args['url'] = urls.file(d.url)
        if (d.getName()) args['name'] = d.getName()
        if (d.getOptional()) args['optional'] = d.getOptional()
        if (d.getVersion()) args['version'] = d.getVersion()

        xml.deploymentDescriptor(args) {
            super.visit(d)
        }
        
        //xml.deploymentDescriptor(name:d.getName(), url:urls.file(d.url),
        //        optional:d.optional, version:d.version) {
        //    super.visit(d)
        //}
    }

    void visit(WebRoot root)
    {
        xml.root(url:urls.file(root.url), relative:root.relative) {
            super.visit(root)
        }
    }

    void visit(Option option)
    {
        xml.option(name:option.name, value:option.value){
            super.visit(option)
        }
    }


    void visit(JavaComponent java)
    {
        xml.component(name:"NewModuleRootManager",
                'inherit-compiler-output':java.inheritCompilerOutput) {
            File out = java.getOutputDir()
            if (out) xml.output(url:urls.file(out))

            File outTest = java.getTestsOutputDir()
            if (outTest) xml.'output-test'(url:urls.file(outTest))

            if (java.excludeOutputPaths) xml.'exclude-output'()

            String contentUrl = new FileUrl('$MODULE_DIR$').url()
            xml.content(url:contentUrl){
                java.getSources().list().each { source(it, false) }
                java.getTests().list().each { source(it, true) }
                java.getExcludes().list().each{ exclude(it) }
            }

            super.visit(java)
        }
    }


    void source(String path, boolean isTest)
    {
        xml.sourceFolder(url:urls.file(path), isTestSource:isTest)
    }


    void exclude(String path)
    {
        xml.excludeFolder(url:urls.file(path))
    }


    void visit(Dependencies dependencies)
    {
        // get a handle on the filters so we can apply them to module lib entries
        filters = dependencies.filters()

        super.visit(dependencies)
        
        xml.orderEntryProperties(){}
    }


    void visit(Module module)
    {
        xml.orderEntry(addExport(['module-name':module.name, type:"module"], module)) {
            super.visit(module)
        }
    }


    void visit(ModuleLibraryType lib)
    {
        Path classes = lib.classes

        //Logger.verbose("filters=${filters}")

        // apply filters - this application is whack... todo fixme
        filters.each { filter -> classes = filter.filter(classes) }

        if (!classes.list().size() &&
            !lib.jarDirs.list().size() &&
            !lib.getIdeaLibs().size()) return;

        xml.orderEntry(addExport([type:"module-library"], lib)) {
            xml.library(nonNulls(name:lib.getName())){
                xml.CLASSES() {
                    classes.list().each{ jar -> xml.root(url:asUrl(jar)) }
                    lib.jarDirs.list().each{ dir -> xml.root(url:asUrl(dir)) }
                    lib.ideaLibs.each { jar -> xml.root(url:urls.ideaLib(jar)) }
                }
                xml.JAVADOC() {
                    lib.javadocs.list().each { javadoc -> xml.root(url:asUrl(javadoc)) }
                    lib.javadocUrls.each { docUrl -> xml.root(url:asUrl(docUrl.url)) }
                }
                xml.SOURCES() {
                    lib.sources.list().each { source -> xml.root(url:asUrl(source)) }
                }
                lib.jarDirs.list().each { jarDir ->
                    xml.jarDirectory(url:asUrl(jarDir), recursive:"false")
                }
                super.visit(lib)
            }
        }
    }


    void visit(IdeaLibrary lib)
    {
        super.visit(lib)
    }


    void visit(Classpath cp)
    {
        super.visit(cp)
    }


    void visit(ProjectLibrary lib)
    {
        globalOrProjectLib(lib, "project")
    }


    void visit(GlobalLibrary lib)
    {
        globalOrProjectLib(lib, "application")
    }


    void visit(ModuleSource source)
    {
        xml.orderEntry(type:"sourceFolder", forTests:"false"){
            super.visit(source)
        }
    }


    void visit(Jdk jdk)
    {
        String name = jdk.getJdkName()
        if (name)
        {
            // todo always JavaSDK?
            xml.orderEntry(type:"jdk", jdkName:name, jdkType:"JavaSDK"){
                super.visit(jdk)
            }
        }
        else
        {
            xml.orderEntry(type:"inheritedJdk"){
                super.visit(jdk)
            }
        }

    }


    void globalOrProjectLib(library, String level)
    {
        Map a = addExport([type:"library", name:library.getName(), level:level], library);
        xml.orderEntry(a){
            super.visit(library)
        }
    }


    void visit(BuildComponent build)
    {
        xml.component(name:"BuildJarSettings"){
            super.visit(build)
            Logger.debug "build.size = ${build.getChildren().size()}"
            // first do libraries and module dependencies
            setting("jarUrl", urls.file(build.getJar()))
            setting("buildJar", "true") // always true... what is it?
            setting("mainClass", build.getMainClass())
        }
    }

    void setting(name, value)
    {
        xml.setting(name:name, value:value)
    }

    void visit(ClasspathContainer cp)
    {
        // todo probably need to apply filters
        cp.list().each{ jar ->
            xml.containerElement(type:"library", level:"module") {
                attribute("method", "1")
                attribute("URI", "/")
                xml.url() {
                    xml.yield urls.jar(jar)
                }
            }
        }
    }

    void visit(GlobalLibraryContainer lib)
    {
        namedContainer(lib.name, "library", "application")
        super.visit(lib)
    }

    void visit(ModuleContainer module)
    {
        namedContainer(module.name, "module", null)
    }

    void visit(ModuleLibraryContainer lib)
    {
        namedContainer(lib.name, "library", "module")
    }

    void visit(ProjectLibraryContainer lib)
    {
        namedContainer(lib.name, "library", "project")
    }

    void attribute(name, value)
    {
        xml.attribute(name:name, value:value)
    }

    def namedContainer(String name, String type, String level)
    {
        def attrs = [:]
        attrs["name"] = name
        attrs["type"] = type
        if (level) attrs["level"] = level

        xml.containerElement(attrs){
            attribute("method", "1")
            attribute("URI", "/")
        }        
    }

    /**
     * Given a path as a String, return the appropriate Url type.
     */
    String asUrl(String path)
    {
        assert path
        if (path.endsWith("jar")) return urls.jar(path)
        if (path.endsWith("zip")) return urls.jar(path)
        if (path.startsWith("http")) return urls.http(path)
        return urls.file(path)
    }
    
    /**
     * Add the exported flag if the Exportable is being exported.
     */
    Map addExport(Map map, Exportable library)
    {
        if(library.exported) map['exported']= ''
        return map
    }
    

    Map nonNulls(Map map)
    {
        // todo do this more groovyish
        Map nonNulls = [:]
        map.each { k, v -> if (v) { nonNulls[k] = v } };
        return nonNulls
    }


    String toString()
    {
        return writer.toString()
    }

}

