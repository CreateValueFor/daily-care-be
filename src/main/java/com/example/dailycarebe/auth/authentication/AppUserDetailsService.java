package com.example.dailycarebe.auth.authentication;

import com.example.dailycarebe.auth.authentication.mapper.AppUserDetailsMapper;
import com.example.dailycarebe.exception.InvalidDataRequestException;
import com.example.dailycarebe.user.model.User;
import com.example.dailycarebe.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    private final AppUserDetailsMapper appUserDetailsMapper;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserDetailsByUsername(username);
    }

    @Transactional(readOnly = true)
    public AppUserDetails getUserDetailsByUsername(String username) {
        User user = userRepository.findByLoginId(username)
                .orElseThrow(() -> new InvalidDataRequestException("존재하지 않는 계정 정보 입니다."));
        return getAppUserDetailsByUser(user);
    }

    @Transactional(readOnly = true)
    public AppUserDetails getAppUserDetailsById(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new InvalidDataRequestException("존재하지 않는 id 계정 정보 입니다."));
        return getAppUserDetailsByUser(user);
    }

    private AppUserDetails getAppUserDetailsByUser(User user) {
        return appUserDetailsMapper.userDetailsFrom(user);
    }
}
