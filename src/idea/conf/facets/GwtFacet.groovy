package idea.conf.facets

import idea.conf.Visitable
import idea.conf.url.FileUrl

/**
 *
 *
 * @author tomichj
 */

class GwtFacet implements Visitable
{
    String compilerParams
    Integer compilerMaxHeap = 128
    File compilerOutputPath      // normal path
    GwtOutputStyle scriptOutputStyle  // DETAILED, PRETTY or OBF
    File gwtSdkUrl  // should be a file url
    boolean runGwtCompilerOnMake = true  // defaults to true
    String intoWebFacet  // drop from iml unless empty


    GwtFacet()
    {
    }

    List<Visitable> getChildren()
    {
        return null;
    }
    

    String toString()
    {
        return "GwtFacet{" +
                "compilerParams=" + compilerParams +
                ", compilerMaxHeap=" + compilerMaxHeap +
                ", compilerOutputPath=" + compilerOutputPath +
                ", scriptOutputStyle=" + scriptOutputStyle +
                ", gwtSdkUrl=" + gwtSdkUrl +
                ", runGwtCompilerOnMake=" + runGwtCompilerOnMake +
                ", intoWebFacet=" + intoWebFacet +
                "}"
    }
}