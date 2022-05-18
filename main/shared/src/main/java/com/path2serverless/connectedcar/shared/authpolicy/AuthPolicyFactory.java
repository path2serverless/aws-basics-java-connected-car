package com.path2serverless.connectedcar.shared.authpolicy;

import java.util.ArrayList;

public class AuthPolicyFactory {
  
  public static AuthPolicy GetApiPolicy(String principalId, boolean isAllowed) {
    AuthPolicy policy = new AuthPolicy();
    AuthPolicyDocument document = new AuthPolicyDocument();
    AuthPolicyStatement statement = new AuthPolicyStatement();

    policy.setPrincipalId(principalId);
    policy.setPolicyDocument(document);
    
    document.setVersion("2012-10-17");
    document.setStatement(new ArrayList<AuthPolicyStatement>());
    document.getStatement().add(statement);

    statement.setAction("execute-api:Invoke");
    statement.setEffect(isAllowed ? "Allow" : "Deny");
    statement.setResource("*");

    return policy;
  }
}
