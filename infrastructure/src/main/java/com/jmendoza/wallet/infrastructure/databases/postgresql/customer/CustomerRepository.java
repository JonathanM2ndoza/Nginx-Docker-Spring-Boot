package com.jmendoza.wallet.infrastructure.databases.postgresql.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerJpaEntity, Long> {

    boolean existsByEmail(String email);

    Optional<CustomerJpaEntity> findByEmail(String email);
}
