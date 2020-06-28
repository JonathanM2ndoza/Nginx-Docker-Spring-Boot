package com.jmendoza.wallet.domain.model.authorization;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class Role {

    private String roleId;
    private Roles roleName;
}
