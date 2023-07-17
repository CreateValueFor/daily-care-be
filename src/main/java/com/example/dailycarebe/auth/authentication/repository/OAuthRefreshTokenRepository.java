package com.example.dailycarebe.auth.authentication.repository;

import com.example.dailycarebe.auth.authentication.model.OAuthRefreshToken;
import com.example.dailycarebe.base.orm.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface OAuthRefreshTokenRepository extends BaseRepository<OAuthRefreshToken> {
    @Modifying
    @Query("DELETE FROM OAuthRefreshToken e WHERE e.tokenId in :tokenIds")
    void deleteAllByTokenId(Set<String> tokenIds);

    OAuthRefreshToken findByTokenId(String tokenId);
}
