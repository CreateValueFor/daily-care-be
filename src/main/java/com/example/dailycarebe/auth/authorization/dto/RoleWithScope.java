package com.example.dailycarebe.auth.authorization.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false, of = { "roleId" })
public class RoleWithScope implements Serializable {
    private String roleId;

    private Set<AccessControlScope> accessControlScopes = new HashSet<>();

    public void addAccessControlScope(AccessControlScope accessControlScope) {
        accessControlScopes.add(accessControlScope);
    }

    public void addAllAccessControlScope(Set<AccessControlScope> accessControlScope) {
        accessControlScopes.addAll(accessControlScope);
    }

    public static RoleWithScope of(String roleId) {
        RoleWithScope dto = new RoleWithScope();
        dto.setRoleId(roleId);
        return dto;
    }
}
