package com.example.dailycarebe.auth.authorization.mapper;

import com.example.dailycarebe.auth.authorization.model.AppRole;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring", uses = {})
public interface AppRoleMapper {
    default AppRole of(long id) {
        return AppRole.of(id);
    }
}
