package com.example.dailycarebe.user.repository;


import com.example.dailycarebe.base.orm.repository.BaseRepository;
import com.example.dailycarebe.user.model.ProviderType;
import com.example.dailycarebe.user.model.User;
import com.example.dailycarebe.user.model.UserStateType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends BaseRepository<User> {
    Optional<User> findByLoginId(String username);
    Optional<User> findByPhone(String phone);

    Optional<List<User>> findAllByPhone(String phone);

    Optional<User> findByEmail(String email);

    Optional<User> findByNameAndPhone(String name, String phone);

    Optional<User> findByNameAndEmail(String name, String email);

    Optional<User> findByLoginIdAndPhone(String loginId, String phone);

    Optional<User> findByLoginIdAndEmail(String loginId, String email);

    Optional<User> findByLoginIdAndPhoneAndName(String username, String phone, String name);

    Optional<User> findByProviderAndProviderId(ProviderType provider, String providerId);

    List<User> findAllByLastModifiedDateLessThanEqual(LocalDateTime localDateTime);
    List<User> findAllByLastModifiedDateLessThanEqualAndState(LocalDateTime localDateTime, UserStateType userStateType);
}


