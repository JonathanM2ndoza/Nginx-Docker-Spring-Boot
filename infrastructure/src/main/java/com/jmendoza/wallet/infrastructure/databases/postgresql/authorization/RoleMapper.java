package com.jmendoza.wallet.infrastructure.databases.postgresql.authorization;

import com.jmendoza.wallet.domain.model.authorization.Role;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoleMapper {

    @Autowired
    ModelMapper modelMapper;

    public Set<Role> mapToDomain(Set<RoleJpaEntity> roleJpaEntities) {
        return Arrays.asList(modelMapper.map(roleJpaEntities, Role[].class)).stream().collect(Collectors.toSet());
    }
}
