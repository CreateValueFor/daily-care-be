package com.example.dailycarebe.auth.authentication.repository;

import com.example.dailycarebe.auth.authentication.model.OAuthAccessToken;
import com.example.dailycarebe.base.orm.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface OAuthAccessTokenRepository extends BaseRepository<OAuthAccessToken> {
    @Modifying
    @Query("DELETE FROM OAuthAccessToken e WHERE e.id in :ids")
    void deleteAllById(Set<Long> ids);

    @Query("SELECT e FROM OAuthAccessToken e WHERE e.userName = :username ORDER BY e.id DESC")
    List<OAuthAccessToken> findAllByUserName(String username);
}
