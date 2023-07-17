package com.example.dailycarebe.user.auth.authorization.repository;


import com.example.dailycarebe.base.orm.repository.BaseRepository;
import com.example.dailycarebe.user.auth.authorization.model.PasswordResetToken;

import java.util.Optional;

public interface PasswordResetTokenRepository extends BaseRepository<PasswordResetToken> {
  Optional<PasswordResetToken> findByToken(String token);
}
