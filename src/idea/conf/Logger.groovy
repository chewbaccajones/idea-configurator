package idea.conf

import org.apache.tools.ant.Project

/**
 * 
 * User: tomichj
 * Date: Apr 2, 2009
 * Time: 2:44:31 PM
 */

public class Logger
{
    private static Logger logger

    private Project project

    static void init(Project project)
    {
        logger = new Logger(project)
    }

    static void debug(String msg)
    {
        logger.project.log(msg, Project.MSG_DEBUG)
    }

    static void info(String msg)
    {
        logger.project.log(msg, Project.MSG_INFO)
    }

    static void verbose(String msg)
    {
        logger.project.log(msg, Project.MSG_VERBOSE)
    }

    private Logger(project)
    {
        this.project = project;
    }

}
