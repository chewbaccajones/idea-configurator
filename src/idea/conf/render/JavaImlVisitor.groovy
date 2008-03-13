package idea.conf.render

import groovy.xml.MarkupBuilder

import idea.conf.JavaModule
import idea.conf.JavaComponent
import idea.conf.url.UrlFactory
import idea.conf.url.UrlFactoryImpl

/**
*
*
* @author tomichj
*/
class JavaImlVisitor extends DefaultVisitor
{
    private final StringWriter writer = new StringWriter()
    private final MarkupBuilder xml = new MarkupBuilder(writer)
    private final UrlFactory urls;


    JavaImlVisitor(root, relativePaths)
    {
        urls = new UrlFactoryImpl(root, relativePaths)
    }

    void visit(JavaModule module)
    {
        xml.module(relativePaths:module.getRelativePaths(),  type:"JAVA_MODULE",
                version:"4") {
            super.visit(module)
        }
    }

    void visit(JavaComponent java)
    {
        xml.component(name:"NewModuleRootManager",
                'inherit-compiler-output':java.getInheritCompilerOutput()) {
            File output = java.getOutputDir()
            if (output) xml.output(url:urls.fileUrl(output).contents())

            if (java.getExcludeOutputPaths())
            {
                xml.exclude-output(){}
            }
            super.visit(java)
        }
    }


    String toString()
    {
        return writer.toString()
    }
}