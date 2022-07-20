package com.ngs.gateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.introspection.ReactiveOpaqueTokenIntrospector;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.util.CollectionUtils;

@RequiredArgsConstructor
@EnableWebFluxSecurity
public class SecurityConfig {

  private final ReactiveOpaqueTokenIntrospector introspection;

  private final PatternAuthorizationProperties patternPermit;

  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

    if (!CollectionUtils.isEmpty(patternPermit.getPermitPattern())) {
      http.authorizeExchange()
          .pathMatchers(patternPermit.getPermitPattern().toArray(new String[0]))
          .permitAll();
    }

    if (!CollectionUtils.isEmpty(patternPermit.getAuthenticatePattern())) {
      http.authorizeExchange()
          .pathMatchers(patternPermit.getAuthenticatePattern().toArray(new String[0]))
          .authenticated();
    }

    if (!CollectionUtils.isEmpty(patternPermit.getHasRolePattern())) {
      patternPermit
          .getHasRolePattern()
          .forEach(
              rolePattern ->
                  http.authorizeExchange()
                      .pathMatchers(rolePattern.getPattern().toArray(new String[0]))
                      .hasRole(rolePattern.getRole()));
    }

    if (!CollectionUtils.isEmpty(patternPermit.getHasAuthorizePattern())) {
      patternPermit
          .getHasAuthorizePattern()
          .forEach(
              authorityPattern ->
                  http.authorizeExchange()
                      .pathMatchers(authorityPattern.getPattern().toArray(new String[0]))
                      .hasRole(authorityPattern.getAuthority()));
    }

    http.csrf()
        .disable()
        .authorizeExchange()
        .anyExchange()
        .authenticated()
        .and()
        .oauth2ResourceServer()
        .opaqueToken()
        .introspector(introspection);
    return http.build();
  }
}
