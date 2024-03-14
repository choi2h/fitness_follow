package com.ffs.user.domain.repository;

import com.ffs.user.domain.UserStatus;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Slf4j
@Converter
public class UserStatusConverter implements AttributeConverter<UserStatus, String> {

    @Override
    public String convertToDatabaseColumn(UserStatus userStatus) {
        if(userStatus == null) return null;

        return userStatus.getName();
    }

    @Override
    public UserStatus convertToEntityAttribute(String dbData) {
        if(dbData == null) return null;

        try {
            return UserStatus.getUserStatusByName(dbData);
        } catch (IllegalStateException e) {
            log.error("Failure to convert cause unexpected code [{}]", dbData, e);
            throw e;
        }
    }
}
