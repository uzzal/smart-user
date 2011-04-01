/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartitengineering.user.ws.resources;

import com.smartitengineering.util.opensearch.api.Url.RelEnum;
import com.smartitengineering.util.opensearch.impl.OpenSearchDescriptorBuilder;
import com.smartitengineering.util.opensearch.impl.UrlBuilder;
import com.smartitengineering.util.opensearch.jaxrs.MediaType;
import com.smartitengineering.util.rest.server.AbstractResource;
import com.smartitengineering.util.rest.server.ServerResourceInjectables;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author imyousuf
 */
public class UriTemplatesResource extends AbstractResource {

  protected final transient Logger logger = LoggerFactory.getLogger(getClass());
  private final static String REL_ORG = "org";
  private final static String REL_ROLE = "role";

  public UriTemplatesResource(ServerResourceInjectables injectables) {
    super(injectables);
  }

  @GET
  @Produces(MediaType.APPLICATION_OPENSEARCHDESCRIPTION_XML)
  public Response getSpec() {
    OpenSearchDescriptorBuilder descBuilder = OpenSearchDescriptorBuilder.getBuilder();
    descBuilder.shortName("Smart User Url Templates");
    descBuilder.description("Search for common entities in Smart User");
    StringBuilder templateBuilder = getOrgUri();
    final String orgUrlTemplate = templateBuilder.toString();
    if (logger.isInfoEnabled()) {
      logger.info("Organization Template URL: " + orgUrlTemplate);
    }
    UrlBuilder orgBuilder = UrlBuilder.getBuilder().rel(REL_ORG).template(orgUrlTemplate).type(
        javax.ws.rs.core.MediaType.APPLICATION_ATOM_XML);
    templateBuilder = getRoleUri();
    final String roleUrlTemplate = templateBuilder.toString();
    if (logger.isInfoEnabled()) {
      logger.info("Role Template URL: " + roleUrlTemplate);
    }
    UrlBuilder roleBuilder = UrlBuilder.getBuilder().rel(REL_ROLE).template(roleUrlTemplate).
        type(javax.ws.rs.core.MediaType.APPLICATION_ATOM_XML);
    descBuilder.urls(orgBuilder.build(), roleBuilder.build());
    ResponseBuilder builder = Response.ok(descBuilder.build());
    return builder.build();
  }

  private StringBuilder getOrgUri() {
    StringBuilder builder = new StringBuilder();
    builder.append(getRelativeURIBuilder().path(OrganizationResource.class).build());
    return builder;
  }

  private StringBuilder getRoleUri() {
    StringBuilder builder = new StringBuilder();
    builder.append(getRelativeURIBuilder().path(RoleResource.class).build());
    return builder;
  }
}
