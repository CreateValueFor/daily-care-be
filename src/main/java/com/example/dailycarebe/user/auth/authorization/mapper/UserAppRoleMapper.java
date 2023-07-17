package com.example.dailycarebe.user.auth.authorization.mapper;

import com.example.dailycarebe.auth.authorization.dto.RoleWithScope;
import com.example.dailycarebe.auth.authorization.model.AppRole;
import com.example.dailycarebe.base.orm.mapper.HashIdsMapper;
import com.example.dailycarebe.user.auth.authorization.dto.UserAppRoleRegisterDto;
import com.example.dailycarebe.user.auth.authorization.dto.UserAppRoleViewDto;
import com.example.dailycarebe.user.auth.authorization.model.UserAppRole;
import com.example.dailycarebe.user.model.User;
import com.example.dailycarebe.util.HashidsUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;
import java.util.Set;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring", uses = {HashIdsMapper.class})
public interface UserAppRoleMapper {
    @Mapping(target = "user", source = "userUuid")
    @Mapping(target = "appRole", source = "appRoleUuid")
    UserAppRole registerDtoToEntity(UserAppRoleRegisterDto registerDto);

    @Mapping(target = "roleId", source = "appRole.roleId")
    RoleWithScope userAppRoleToRoleWithScope(UserAppRole entity);

    Set<RoleWithScope> userAppRoleToRoleWithScopes(Collection<UserAppRole> entities);

    UserAppRoleViewDto entityToViewDto(UserAppRole entity);

    default User userOf(long id) {
        return User.of(id);
    }

    default User userOf(String uuid) {
        return User.of(HashidsUtil.decodeNumber(uuid));
    }

    default AppRole appRoleof(long id) {
        return AppRole.of(id);
    }

    default AppRole appRoleof(String uuid) {
        return AppRole.of(HashidsUtil.decodeNumber(uuid));
    }
}
