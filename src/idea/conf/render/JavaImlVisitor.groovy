package idea.conf.render

import groovy.xml.MarkupBuilder

import idea.conf.JavaModule
import idea.conf.java.JavaComponent
import idea.conf.java.depend.Dependencies
import idea.conf.java.depend.ModuleLibrary
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

/**
* The JavaImlVisitor creates an iml file for java projects.
*
* @author tomichj
*/
class JavaImlVisitor extends DefaultVisitor
{
    // todo move to JavaModule ??
    static final int VERSION = 4;
    static final String TYPE = "JAVA_MODULE";

    private final StringWriter writer = new StringWriter();
    private final MarkupBuilder xml = new MarkupBuilder(writer);
    private final UrlFactory urls;

    def filters = []
    

    JavaImlVisitor(UrlFactory urlFactory)
    {
        this.urls = urlFactory
    }


    void visit(JavaModule module)
    {
        xml.module(relativePaths:module.relativePaths, type:TYPE, version:VERSION) {
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


    void visit(GroovyFacet facet)
    {
        xml.facet(type:"Groovy", name:"Groovy") {
            xml.configuration()
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
        filters = dependencies.filters

        super.visit(dependencies)
        
        xml.orderEntryProperties(){}
    }


    void visit(Module module)
    {
        xml.orderEntry(addExport(['module-name':module.getName(), type:"module"], module)) {
            super.visit(module)
        }
    }


    void visit(ModuleLibrary lib)
    {
        Path classes = lib.classes
        
        // apply filters
        filters.each { filter -> classes = filter.filter(classes) }

        if (!classes.list().size() && !lib.jarDirs.list().size()) return;

        xml.orderEntry(addExport([type:"module-library"], lib)) {
            xml.library(nonNulls(name:lib.getName())){
                xml.CLASSES() {
                    classes.list().each{ jar -> xml.root(url:asUrl(jar)) }
                    lib.jarDirs.list().each{ dir -> xml.root(url:asUrl(dir)) }
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

