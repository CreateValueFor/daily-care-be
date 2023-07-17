package com.example.dailycarebe.auth.authorization.repository;


import com.example.dailycarebe.auth.authorization.model.AppRole;
import com.example.dailycarebe.base.orm.repository.BaseRepository;

import java.util.Optional;

public interface AppRoleRepository extends BaseRepository<AppRole> {
    Optional<AppRole> findByRoleId(String roleId);
}
