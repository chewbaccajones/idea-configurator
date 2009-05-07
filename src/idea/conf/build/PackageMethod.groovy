package idea.conf.build

/**
 * 
 * User: tomichj
 * Date: May 6, 2009
 * Time: 11:38:16 AM
 */
public enum PackageMethod {

    COPY_OUTPUT(1),
    LINK_IN_MANIFEST_AND_COPY(2),
    JAR_OUTPUT_AND_COPY(5),
    JAR_AND_LINK_IN_MANIFEST(6);

    
    private final code

    PackageMethod(int code)
    {
        this.code = code
    }


    int getCode()
    {
        return code
    }
}