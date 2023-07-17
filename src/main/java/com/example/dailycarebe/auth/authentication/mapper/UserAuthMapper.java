package com.example.dailycarebe.auth.authentication.mapper;

import com.example.dailycarebe.user.model.User;
import com.example.dailycarebe.auth.authentication.dto.UserAuthDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE
        , componentModel = "spring"
)
public interface UserAuthMapper {
    UserAuthDto entityToDto(User user);
}
