package com.example.dailycarebe.user.mapper;

import com.example.dailycarebe.base.orm.mapper.HashIdsMapper;
import com.example.dailycarebe.user.model.User;
import com.example.dailycarebe.util.HashidsUtil;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
  unmappedTargetPolicy = ReportingPolicy.IGNORE,
  componentModel = "spring", uses = {HashIdsMapper.class})
public interface UserIdMapper {
  default User of(long userId) {
    return User.of(userId);
  }

  default User of(String userUuid) {
    if (userUuid == null) {
      return null;
    }
    return User.of(HashidsUtil.decodeNumber(userUuid));
  }
}
