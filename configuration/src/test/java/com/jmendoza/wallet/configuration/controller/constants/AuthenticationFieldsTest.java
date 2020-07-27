package com.jmendoza.wallet.configuration.controller.constants;

import lombok.Getter;

@Getter
public enum AuthenticationFieldsTest {

    EMAIL("email", "email description"),
    PASSWORD("password", "password description"),
    TOKEN("token", "token description"),
    CUSTOMER_ID("customerId", "customerId description"),
    FIRST_NAME("firstName", "firstName description"),
    LAST_NAME("lastName", "lastName description"),
    ROLES("roles", "roles description"),
    CREATED_AT("createdAt", "createdAt description"),
    UPDATED_AT("updatedAt", "updatedAt description"),
    ROLE_ID("roleId", "roleId description"),
    ROLE_NAME("roleName", "roleName description");

    private final String field;
    private final String description;

    AuthenticationFieldsTest(String field, String description) {
        this.field = field;
        this.description = description;
    }

    public String getField() {
        return this.field;
    }

    public String getDescription() {
        return this.description;
    }
}
