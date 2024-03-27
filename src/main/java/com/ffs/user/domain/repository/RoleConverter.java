package com.ffs.user.domain.repository;

import com.ffs.user.domain.Role;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Slf4j
@Converter
public class RoleConverter implements AttributeConverter<Role, String> {

    @Override
    public String convertToDatabaseColumn(Role role) {
        if(role == null) return null;
        return role.getName();
    }

    @Override
    public Role convertToEntityAttribute(String dbData) {
        if(dbData == null) return null;

        try {
            return Role.getRoleByName(dbData);
        } catch (IllegalStateException e) {
            log.error("Failure to convert cause unexpected code [{}]", dbData, e);
            throw e;
        }
    }
}
