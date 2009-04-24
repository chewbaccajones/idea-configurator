package idea.conf.facets

import idea.conf.java.depend.Dependencies

/**
 * Implemented by facets that may inject dependencies based on their configuration.
 * E.g. the groovy facet might inject a project library dependency on groovy jars.  
 */
public interface DependencyInjector
{
    def injectDependencies(Dependencies dependencies)
}
