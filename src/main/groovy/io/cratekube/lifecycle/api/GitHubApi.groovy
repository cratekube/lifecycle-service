package io.cratekube.lifecycle.api

import io.cratekube.lifecycle.api.exception.NotFoundException

/**
 * Manages GitHub interactions
 */
interface GitHubApi {
  /**
   * Finds the latest released version for a repository
   *
   * @param atomFeedUrl {@code non-empty} location of atom feed
   *
   * @return the latest released version or null if no release exists
   */
  String getLatestVersionFromAtomFeed(String atomFeedUrl)

  /**
   * Retrieves the specified deployable component configuration for a specific version. Throws {@link NotFoundException} if the version does not exist.
   *
   * @param component {@code non-empty} name of component
   * @param version {@code non-empty} version of component
   *
   * @return the components config
   *
   * @throws NotFoundException
   */
  String getDeployableComponent(String component, String version) throws NotFoundException
}
