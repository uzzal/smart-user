/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartitengineering.user.client.impl;

import com.smartitengineering.user.client.api.OrganizationResource;
import com.smartitengineering.user.client.api.RoleResource;
import com.smartitengineering.user.client.api.UriTemplateResource;
import com.smartitengineering.util.opensearch.api.OpenSearchDescriptor;
import com.smartitengineering.util.opensearch.api.Url;
import com.smartitengineering.util.opensearch.api.Url.Rel;
import com.smartitengineering.util.rest.client.AbstractClientResource;
import com.smartitengineering.util.rest.client.ClientUtil;
import com.smartitengineering.util.rest.client.Resource;
import com.smartitengineering.util.rest.client.ResourceLink;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.config.ClientConfig;
import java.net.URI;

/**
 *
 * @author imyousuf
 */
public class UriTemplateResourceImpl extends AbstractClientResource<OpenSearchDescriptor, Resource> implements
    UriTemplateResource {

  public UriTemplateResourceImpl(Resource referrer, ResourceLink resouceLink) throws IllegalArgumentException,
                                                                                     UniformInterfaceException {
    super(referrer, resouceLink);
  }

  @Override
  protected void processClientConfig(ClientConfig clientConfig) {
  }

  @Override
  protected ResourceLink getNextUri() {
    return null;
  }

  @Override
  protected ResourceLink getPreviousUri() {
    return null;
  }

  @Override
  protected Resource instantiatePageableResource(ResourceLink link) {
    return null;
  }

  @Override
  public OrganizationResource getOrganizationForUniqueShortName(String uniqueShortName) {
    OpenSearchDescriptor descriptor = getLastReadStateOfEntity();
    for (Url url : descriptor.getUrls()) {
      for (Rel rel : url.getRels()) {
        if ("org".equals(rel.getValue())) {
          String urlStr = url.getTemplate();
          urlStr = urlStr.replace("{uniqueShortName}", uniqueShortName);
          ResourceLink link = ClientUtil.createResourceLink(rel.getValue(), URI.create(urlStr), url.getType());
          return new OrganizationResourceImpl(link, this);
        }
      }
    }
    return null;
  }

  @Override
  public RoleResource getRoleResourceForRoleName(String roleName) {
    OpenSearchDescriptor descriptor = getLastReadStateOfEntity();
    for (Url url : descriptor.getUrls()) {
      for (Rel rel : url.getRels()) {
        if ("role".equals(rel.getValue())) {
          String urlStr = url.getTemplate();
          urlStr = urlStr.replace("{roleName}", roleName);
          ResourceLink link = ClientUtil.createResourceLink(rel.getValue(), URI.create(urlStr), url.getType());
          return new RoleResourceImpl(link, this);
        }
      }
    }
    return null;
  }
}
