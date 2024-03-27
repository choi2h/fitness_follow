package com.ffs.user.domain.repository;

import com.ffs.user.domain.UserType;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Slf4j
@Converter
public class UserTypeConverter implements AttributeConverter<UserType, String> {

    @Override
    public String convertToDatabaseColumn(UserType userType) {
        if(userType == null) return null;

        return userType.getName();
    }

    @Override
    public UserType convertToEntityAttribute(String dbData) {
        if(dbData == null) {
            return null;
        }

        try {
            return UserType.getUserTypeByName(dbData);
        } catch (IllegalStateException e) {
            log.error("Failure to convert cause unexpected code [{}]", dbData, e);
            throw e;
        }
    }
}
