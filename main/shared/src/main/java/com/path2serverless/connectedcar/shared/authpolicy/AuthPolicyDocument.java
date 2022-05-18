package com.path2serverless.connectedcar.shared.authpolicy;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthPolicyDocument {
  
  private String version = null;
  private List<AuthPolicyStatement> Statement = null;

  @JsonProperty("Version")
  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  @JsonProperty("Statement")
  public List<AuthPolicyStatement> getStatement() {
    return Statement;
  }

  public void setStatement(List<AuthPolicyStatement> statement) {
    Statement = statement;
  }
}
