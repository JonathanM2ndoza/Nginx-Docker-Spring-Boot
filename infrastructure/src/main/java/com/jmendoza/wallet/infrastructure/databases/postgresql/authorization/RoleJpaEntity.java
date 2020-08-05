package com.jmendoza.wallet.infrastructure.databases.postgresql.authorization;


import com.jmendoza.wallet.common.constants.global.DBSchema;
import com.jmendoza.wallet.domain.model.authorization.Roles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "ROLE", schema= DBSchema.AUTH_SCHEMA)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class RoleJpaEntity {
    @Id
    @Column(name = "R_ID_PK", updatable = false, nullable = false)
    @GeneratedValue(generator = "ROLE_R_ID_PK_SEQ")
    @SequenceGenerator(name = "ROLE_R_ID_PK_SEQ", sequenceName = DBSchema.AUTH_SCHEMA + ".ROLE_R_ID_PK_SEQ", allocationSize = 1)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(name = "R_NAME", length = 100)
    private Roles name;
}

