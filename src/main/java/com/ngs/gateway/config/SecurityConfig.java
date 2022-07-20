package com.ngs.gateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.introspection.ReactiveOpaqueTokenIntrospector;
import org.springframework.security.web.server.SecurityWebFilterChain;

@RequiredArgsConstructor
@EnableWebFluxSecurity
public class SecurityConfig {

  private final ReactiveOpaqueTokenIntrospector introspection;

  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

    http.csrf()
        .disable()
        .authorizeExchange()
        .pathMatchers("/**/v3/**")
        .permitAll()
        .pathMatchers("/user-service/**")
        .authenticated()
        .anyExchange()
        .permitAll()
        .and()
        .oauth2ResourceServer()
        .opaqueToken()
        .introspector(introspection);
    return http.build();
  }
}
