package com.jmendoza.wallet.infrastructure.databases.postgresql.authorization;

import com.jmendoza.wallet.domain.model.authorization.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleJpaRepository extends JpaRepository<RoleJpaEntity, Long> {
    Optional<RoleJpaEntity> findByName(Roles name);
}
