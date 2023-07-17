package com.example.dailycarebe.user.auth.authorization;

import com.example.dailycarebe.auth.authorization.model.DefaultRole;
import com.example.dailycarebe.auth.authorization.repository.AppRoleRepository;
import com.example.dailycarebe.base.BaseService;
import com.example.dailycarebe.exception.ResourceNotFoundException;
import com.example.dailycarebe.user.auth.authorization.model.UserAppRole;
import com.example.dailycarebe.user.auth.authorization.repository.UserAppRoleRepository;
import com.example.dailycarebe.user.model.User;
import com.example.dailycarebe.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserAppRoleService extends BaseService<UserAppRole, UserAppRoleRepository> {

  private final UserRepository userRepository;

  private final AppRoleRepository appRoleRepository;

  @Transactional
  public void initDefaultUserRole(long userId) {
    User user = userRepository
      .findById(userId)
      .orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 유저 입니다."));

    DefaultRole.DEFAULT_APP_USER_ROLES.forEach(roleId -> {
      UserAppRole userAppRole = new UserAppRole(
        user,
        appRoleRepository
          .findByRoleId(roleId)
          .orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 권한 입니다."))
      );

      saveDirectly(userAppRole);
    });
  }

  @Transactional
  public void deleteAllRoles(long userId) {
    repository.deleteAllByUserId(userId);
  }

  @Transactional(readOnly = true)
  public Collection<GrantedAuthority> getAppRolesByUserId(long userId) {
    List<UserAppRole> userAppRoles = repository.findAllByUserId(userId);

    return userAppRoles.stream()
      .map(userAppRole -> new SimpleGrantedAuthority(userAppRole.getAppRole().getRoleId()))
      .collect(Collectors.toSet());
  }
}
