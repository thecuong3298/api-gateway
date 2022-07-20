package com.ngs.gateway.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "yaml")
@PropertySource(value = "classpath:security/config.yml", factory = YamlPropertySourceFactory.class)
public class PatternAuthorizationProperties {

  private List<String> permitPattern = new ArrayList<>();

  private List<String> authenticatePattern = new ArrayList<>();

  private List<RolePattern> hasRolePattern = new ArrayList<>();

  private List<AuthorityPattern> hasAuthorizePattern = new ArrayList<>();

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class RolePattern {
    private String role;

    private List<String> pattern = new ArrayList<>();
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class AuthorityPattern {
    private String authority;

    private List<String> pattern = new ArrayList<>();
  }
}
