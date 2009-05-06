package idea.conf.facets

import idea.conf.Visitable
import idea.conf.url.FileUrl
import idea.conf.java.depend.Dependencies

/**
 *
 *
 * @author tomichj
 */

class GwtFacet implements Visitable, DependencyInjector
{
    String compilerParams
    Integer compilerMaxHeap = 128
    File compilerOutputPath      // normal path
    GwtOutputStyle outputStyle  // DETAILED, PRETTY or OBF
    File gwtSdkUrl  // should be a file url
    boolean runGwtCompilerOnMake = true  // defaults to true
    String intoWebFacet  // drop from iml unless empty

    // add params, optional, for name of project or global gwt library
    String projectGwtLib
    String globalGwtLib
    

    List<Visitable> getChildren()
    {
        return null;
    }

    void injectDependencies(Dependencies dependencies)
    {
        if (projectGwtLib) dependencies.createProjectLibrary().name = projectGwtLib
        if (globalGwtLib) dependencies.createGlobalLibrary().name = globalGwtLib
    }
    

    String toString()
    {
        return "GwtFacet{" +
                "compilerParams=" + compilerParams +
                ", compilerMaxHeap=" + compilerMaxHeap +
                ", compilerOutputPath=" + compilerOutputPath +
                ", output=" + outputStyle +
                ", gwtSdkUrl=" + gwtSdkUrl +
                ", runGwtCompilerOnMake=" + runGwtCompilerOnMake +
                ", intoWebFacet=" + intoWebFacet +
                "}"
    }

}
