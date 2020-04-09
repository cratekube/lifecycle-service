package io.cratekube.lifecycle.resources

import groovy.transform.Immutable
import groovy.util.logging.Slf4j
import io.cratekube.lifecycle.api.ComponentApi
import io.cratekube.lifecycle.auth.User
import io.cratekube.lifecycle.model.Component
import io.cratekube.lifecycle.modules.annotation.ComponentCache
import io.dropwizard.auth.Auth
import io.swagger.annotations.Api
import io.swagger.annotations.ApiParam

import javax.annotation.security.RolesAllowed
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.Response

import static org.hamcrest.Matchers.notNullValue
import static org.valid4j.Assertive.require
import static org.valid4j.matchers.ArgumentMatchers.notEmptyString

@Api
@Slf4j
@Path('/component')
@Produces('application/json')
@Consumes('application/json')
class ComponentResource {
  Map<String, Component> componentCache
  ComponentApi componentApi

  @Inject
  ComponentResource(@ComponentCache Map<String, Component> componentCache, ComponentApi componentApi) {
    this.componentCache = require componentCache, notNullValue()
    this.componentApi = require componentApi, notNullValue()
  }

  /**
   * Retrieves a specific component by name.
   *
   * @param name {@code non-empty} component name
   * @return the CrateKube component or 404 {@code NOT_FOUND} if one does not exist
   */
  @GET
  @Path('{name}')
  Optional<Component> getComponent(
    @PathParam('name') String name
  ) {
    require name, notEmptyString()
    log.debug 'executing get component for [{}]', name

    return Optional.empty()
  }

  /**
   * Retrieves component upgrade availability.
   *
   * @return in memory cache of managed components.
   */
  @GET
  @Path('version')
  Map<String, Component> getComponentUpgradeAvailability() {
    log.debug 'executing get component upgrade availability'

    return [:]
  }

  /**
   * Applies the specified component version. If no exception is thrown a 202 accepted response will be returned with the location
   * for the resource.
   *
   * @param name {@code non-empty} component name
   * @param componentVersionRequest {@code non-null} request body with version information
   * @param user {@code non-null} user principal
   * @return 202 accepted response if no exception thrown, else a 4xx/5xx response depending on the exception
   */
  @POST
  @RolesAllowed('admin')
  @Path('{name}/version')
  Response applyComponentVersion(
    @PathParam('name') String name,
    @ApiParam ComponentVersionRequest componentVersionRequest,
    @ApiParam(hidden = true) @Auth User user
  ) {
    require name, notEmptyString()
    require componentVersionRequest?.version, notNullValue()
    require user, notNullValue()
    log.debug 'executing apply component version for [{}] [{}]', name, componentVersionRequest.version

    return null
  }

  /**
   * Request object for applying a specific component version.
   */
  @Immutable
  static class ComponentVersionRequest {
    /** version of the component */
    String version
  }
}
