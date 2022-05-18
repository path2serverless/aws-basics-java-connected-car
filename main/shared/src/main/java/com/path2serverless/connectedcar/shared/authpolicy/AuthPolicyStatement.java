package com.path2serverless.connectedcar.shared.authpolicy;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthPolicyStatement {
  
  private String action = null;
  private String effect = null;
  private String resource = null;

  @JsonProperty("Action")
  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  @JsonProperty("Effect")
  public String getEffect() {
    return effect;
  }

  public void setEffect(String effect) {
    this.effect = effect;
  }

  @JsonProperty("Resource")
  public String getResource() {
    return resource;
  }

  public void setResource(String resource) {
    this.resource = resource;
  }
}
