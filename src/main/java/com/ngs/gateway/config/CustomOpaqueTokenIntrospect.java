package com.ngs.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.NimbusReactiveOpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.ReactiveOpaqueTokenIntrospector;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomOpaqueTokenIntrospect implements ReactiveOpaqueTokenIntrospector {

  @Value("${spring.security.oauth2.resourceserver.opaquetoken.introspection-uri}")
  private String introspectionUri;

  @Value("${spring.security.oauth2.resourceserver.opaquetoken.client-id}")
  private String clientId;

  @Value("${spring.security.oauth2.resourceserver.opaquetoken.client-secret}")
  private String clientSecret;

  public Mono<OAuth2AuthenticatedPrincipal> introspect(String token) {
    ReactiveOpaqueTokenIntrospector delegate =
        new NimbusReactiveOpaqueTokenIntrospector(introspectionUri, clientId, clientSecret);
    return delegate
        .introspect(token)
        .map(
            principal -> {
              List<String> authorities = principal.getAttribute("authorities");
              if (authorities == null) authorities = new ArrayList<>();
              return new DefaultOAuth2AuthenticatedPrincipal(
                  principal.getAttribute("user_name"),
                  principal.getAttributes(),
                  authorities.stream()
                      .map(SimpleGrantedAuthority::new)
                      .collect(Collectors.toSet()));
            });
  }
}
