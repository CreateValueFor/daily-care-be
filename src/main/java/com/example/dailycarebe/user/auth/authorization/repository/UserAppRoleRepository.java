package com.example.dailycarebe.user.auth.authorization.repository;


import com.example.dailycarebe.base.orm.repository.BaseRepository;
import com.example.dailycarebe.user.auth.authorization.model.UserAppRole;

import java.util.List;

public interface UserAppRoleRepository extends BaseRepository<UserAppRole> {

  void deleteAllByUserId(long userId);

  List<UserAppRole> findAllByUserId(long userId);
}
