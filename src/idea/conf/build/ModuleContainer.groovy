package idea.conf.build

import idea.conf.Visitable

/**

jar module output and copy file to
 
 <containerElement type="module" name="other-module">
  <attribute name="method" value="5" />
  <attribute name="URI" value="/crapola/other-module.jar" />
</containerElement>


 jar module, link via manifest and copy to
 
 <containerElement type="module" name="other-module">
   <attribute name="method" value="6" />
   <attribute name="URI" value="/jared-and-linked-via-manifest/other-module.jar" />
 </containerElement>

 */

public class ModuleContainer implements Package
{
    static final PackageMethod DEFAULT_PACKAGING = PackageMethod.COPY_OUTPUT
    static final String DEFAULT_RELATIVE_PATH = "/WEB-INF/classes"

    String name
    PackageMethod method
    String relativePath
    

    //def defaultPath()
    //{
    //    if (method == PackageMethod.COPY_OUTPUT) {
    //        return DEFAULT_RELATIVE_PATH
    //    }
    //    return "/" + name + ".jar"
    //}

    public List<Visitable> getChildren()
    {
        //if (!method) method = DEFAULT_PACKAGING
        //if (!relativePath) relativePath = defaultPath()

        return [new Attribute("method", method),
                new Attribute("URI", relativePath)]
    }

    String toString()
    {
        "ModuleContainer{name=${name}, method=${method}, relativePath=${relativePath}}"
    }
}