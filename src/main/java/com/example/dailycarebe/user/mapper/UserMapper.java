package com.example.dailycarebe.user.mapper;

import com.example.dailycarebe.base.orm.mapper.HashIdsMapper;
import com.example.dailycarebe.user.dto.UserEditDto;
import com.example.dailycarebe.user.dto.UserRegisterDto;
import com.example.dailycarebe.user.dto.UserViewDto;
import com.example.dailycarebe.user.model.User;
import com.example.dailycarebe.user.model.UserPain;
import org.mapstruct.*;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

@Mapper(
  unmappedTargetPolicy = ReportingPolicy.IGNORE,
  componentModel = "spring", uses = {HashIdsMapper.class, UserIdMapper.class, UserInfoMapper.class})
public interface UserMapper {
  @Mapping(target = "uuid", source = "id")
  UserViewDto entityToDto(User entity);

  List<UserViewDto> entitiesToDtos(List<User> entities);

  User registerDtoToEntity(UserRegisterDto registerDto);

  User editDtoToEntity(UserEditDto editDto);
}
