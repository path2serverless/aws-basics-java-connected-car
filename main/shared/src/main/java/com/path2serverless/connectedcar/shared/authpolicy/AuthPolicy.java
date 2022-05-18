package com.path2serverless.connectedcar.shared.authpolicy;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthPolicy {
  
  private String principalId = null;
  private AuthPolicyDocument policyDocument = null;

  @JsonProperty("principalId")
  public String getPrincipalId() {
    return principalId;
  }

  public void setPrincipalId(String principalId) {
    this.principalId = principalId;
  }

  @JsonProperty("policyDocument")
  public AuthPolicyDocument getPolicyDocument() {
    return policyDocument;
  }

  public void setPolicyDocument(AuthPolicyDocument policyDocument) {
    this.policyDocument = policyDocument;
  }
}
