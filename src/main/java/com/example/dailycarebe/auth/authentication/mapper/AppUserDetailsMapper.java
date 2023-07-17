package com.example.dailycarebe.auth.authentication.mapper;

import com.example.dailycarebe.auth.authentication.AppUserDetails;
import com.example.dailycarebe.user.auth.authorization.mapper.UserAppRoleMapper;
import com.example.dailycarebe.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
  unmappedTargetPolicy = ReportingPolicy.IGNORE,
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
  componentModel = "spring", uses = {UserAppRoleMapper.class})
public interface AppUserDetailsMapper {
    @Mapping(target = "userId", source = "id")
    AppUserDetails userDetailsFrom(User user);
}
