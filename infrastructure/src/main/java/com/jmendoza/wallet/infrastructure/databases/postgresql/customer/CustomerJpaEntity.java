package com.jmendoza.wallet.infrastructure.databases.postgresql.customer;

import com.jmendoza.wallet.common.constants.global.DBSchema;
import com.jmendoza.wallet.infrastructure.databases.postgresql.authorization.RoleJpaEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CUSTOMER", schema = DBSchema.AUTH_SCHEMA)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class CustomerJpaEntity {

    @Id
    @Column(name = "C_ID_PK", updatable = false, nullable = false)
    @GeneratedValue(generator = "CUSTOMER_C_ID_PK_SEQ")
    @SequenceGenerator(name = "CUSTOMER_C_ID_PK_SEQ", sequenceName = DBSchema.AUTH_SCHEMA + ".CUSTOMER_C_ID_PK_SEQ", allocationSize = 1)
    private long id;
    @Column(name = "C_FIRST_NAME", nullable = false)
    private String firstName;
    @Column(name = "C_LAST_NAME", nullable = false)
    private String lastName;
    @Column(name = "C_EMAIL", nullable = false)
    private String email;
    @Column(name = "C_PASS", nullable = false)
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "CUSTOMER_ROLE", schema = DBSchema.AUTH_SCHEMA,
            joinColumns = @JoinColumn(name = "C_ID_PFK", referencedColumnName = "C_ID_PK"),
            inverseJoinColumns = @JoinColumn(name = "R_ID_PFK", referencedColumnName = "R_ID_PK"))
    private Set<RoleJpaEntity> roleJpaEntities = new HashSet<>();
    @Column(name = "C_CREATED_AT", nullable = false, updatable = false)
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "C_UPDATED_AT", nullable = false)
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}
