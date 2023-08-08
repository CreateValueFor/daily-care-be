package com.example.dailycarebe.user.mapper;

import com.example.dailycarebe.base.orm.mapper.HashIdsMapper;
import com.example.dailycarebe.user.dto.UserInfoEditDto;
import com.example.dailycarebe.user.dto.UserInfoRegisterDto;
import com.example.dailycarebe.user.dto.UserInfoViewDto;
import com.example.dailycarebe.user.model.UserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring", uses = {HashIdsMapper.class, UserIdMapper.class})
public interface UserInfoMapper {

    UserInfo registerDtoToEntity(UserInfoRegisterDto registerDto);

    UserInfo editDtoToEntitiy(UserInfoEditDto editDto);

    @Mapping(target = "uuid", source = "id")
    UserInfoViewDto entityToDto(UserInfo entity);
}
