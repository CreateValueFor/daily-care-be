package com.example.dailycarebe.user;

import com.example.dailycarebe.app.exercise.model.CourseWeekType;
import com.example.dailycarebe.auth.authentication.AppUserDetails;
import com.example.dailycarebe.auth.authentication.AppUserDetailsService;
import com.example.dailycarebe.auth.authentication.dto.UserAuthDto;
import com.example.dailycarebe.auth.authentication.model.AppUserDetailsType;
import com.example.dailycarebe.auth.authentication.util.JWTTokenUtil;
import com.example.dailycarebe.auth.dto.AuthDto;
import com.example.dailycarebe.auth.dto.AuthTokenType;
import com.example.dailycarebe.base.BaseService;
import com.example.dailycarebe.exception.DuplicatedKeyException;
import com.example.dailycarebe.exception.InvalidDataRequestException;
import com.example.dailycarebe.exception.ResourceNotFoundException;
import com.example.dailycarebe.oauth.social.kakao.model.KakaoAccount;
import com.example.dailycarebe.oauth.social.kakao.model.KakaoProperties;
import com.example.dailycarebe.user.auth.authorization.UserAppRoleService;
import com.example.dailycarebe.user.auth.authorization.repository.PasswordResetTokenRepository;
import com.example.dailycarebe.user.dto.UserEditDto;
import com.example.dailycarebe.user.dto.UserLoginDto;
import com.example.dailycarebe.user.dto.UserRegisterDto;
import com.example.dailycarebe.user.dto.UserViewDto;
import com.example.dailycarebe.user.mapper.UserMapper;
import com.example.dailycarebe.user.model.ProviderType;
import com.example.dailycarebe.user.model.User;
import com.example.dailycarebe.user.model.UserGender;
import com.example.dailycarebe.user.repository.UserRepository;
import com.example.dailycarebe.user.statistics.UserStatisticsRepository;
import com.example.dailycarebe.user.statistics.UserStatisticsViewDto;
import com.example.dailycarebe.user.statistics.model.UserStatistics;
import com.example.dailycarebe.util.PasswordUtil;
import com.example.dailycarebe.util.SecurityContextUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService extends BaseService<User, UserRepository> {

  private final UserMapper userMapper;

  private final PasswordEncoder passwordEncoder;

  private final UserAppRoleService userAppRoleService;

  private final AppUserDetailsService userDetailsService;

  private final PasswordResetTokenRepository passwordResetTokenRepository;

  private final JWTTokenUtil jwtTokenUtil;

  private final UserStatisticsRepository userStatisticsRepository;


  @Autowired
  @Qualifier("userAuthenticationProvider")
  private AuthenticationProvider authenticationProvider;

  @Transactional
  public UserAuthDto registerUser(UserRegisterDto registerDto) {
//    smsRepository.findByPhone(registerDto.getPhone()).ifPresent(sms -> {
//      if(sms.getStatus() != SmsVerificationStatus.VERIFIED)
//        throw new InvalidRequestException("번호 인증이 되지 않았습니다.");
//    });

//    repository.findAllByPhone(registerDto.getPhone())
//      .ifPresent((userList) -> {
//        if(!userList.isEmpty()) {
//          throw new InvalidDataRequestException("이미 존재하는 번호입니다.");
//        }
//      });
//
    String username = registerDto.getLoginId();

    repository.findByLoginId(username)
      .ifPresent((user) -> {
        throw new InvalidDataRequestException("이미 존재하는 아이디(이름)입니다.");
      });


    User user = userMapper.registerDtoToEntity(registerDto);
    user.setPassword(passwordEncoder.encode(user.getPassword()));


    user.setCourseWeekType(CourseWeekType.FIRST);
    user.setCourseDay(1);
    user.setCourseWeek(1);
    user.setIsCourseUpgradable(true);
    user.setStartDate(LocalDate.now());
    user.setNextWeek(LocalDate.now().plusWeeks(1));
    User entity = saveDirectly(user);

    Long userId = entity.getId();

    LocalDateTime now = LocalDateTime.now();

    userAppRoleService.initDefaultUserRole(userId);

    UserViewDto userViewDto = userMapper.entityToDto(entity);

    UserAuthDto userAuthDto = new UserAuthDto();

    userAuthDto.setUser(userViewDto);
    userAuthDto.setAuth(createAuthDto(user.getLoginId(), registerDto.getPassword()));

    return userAuthDto;
  }

  @Transactional(readOnly = true)
  public UserViewDto getUser(long userId) {
    User user = userRepository.getReferenceById(userId);

    return userMapper.entityToDto(user);
  }

  @Transactional(readOnly = true)
  public UserViewDto getMe() {
    User user = getContextUser();
    return userMapper.entityToDto(user);
  }

  @Transactional(readOnly = true)
  public User getSocialUser(ProviderType provider, String providerId) {
    Optional<User> socialUser = repository.findByProviderAndProviderId(provider, providerId);

    return socialUser.orElse(null);
  }

  @Transactional
  public User registerSocialUser(String providerId, ProviderType provider, KakaoAccount account, KakaoProperties properties) {
    repository.findByPhone("0"+account.getPhone_number().substring(4))
      .ifPresent((user) -> {
        throw new InvalidDataRequestException("이미 가입된 회원입니다.");
      });

    UserRegisterDto userRegisterDto = new UserRegisterDto();

    userRegisterDto.setLoginId(PasswordUtil.uniqueId());
    userRegisterDto.setPhone("0"+account.getPhone_number().substring(4));
    userRegisterDto.setEmail(account.getEmail());
    userRegisterDto.setBirth(LocalDate.parse(
      account.getBirthyear() + "-"
        + account.getBirthday().substring(0,2) + "-"
        + account.getBirthday().substring(2,4)));
    userRegisterDto.setGender(account.getGender().equals("male") ? UserGender.MALE : UserGender.FEMALE);

    if (properties != null) {
      userRegisterDto.setName(properties.getNickname());
    }
    userRegisterDto.setPassword(passwordEncoder.encode(PasswordUtil.generatePassayPassword()));

    User user = saveDirectly(userMapper.registerDtoToEntity(userRegisterDto));
    user.setProvider(provider);
    user.setProviderId(providerId);
    Long userId = user.getId();

    LocalDateTime now = LocalDateTime.now();

    userAppRoleService.initDefaultUserRole(userId);

    return user;
  }

  @Transactional
  public UserViewDto editUser(UserEditDto editDto) {
    // 수정
    User entity = userMapper.editDtoToEntity(editDto);
    entity.setId(SecurityContextUtil.getUserId());
    entity.setPassword(passwordEncoder.encode(editDto.getPassword()));

    // 이메일 중복체크
    User oldEntity = repository.findById(SecurityContextUtil.getUserId())
      .orElseThrow(() -> new InvalidDataRequestException("토큰 정보가 잘못되었습니다."));
    if (!Objects.equals(oldEntity.getLoginId(), entity.getLoginId())){
      repository.findByLoginId(entity.getLoginId())
        .ifPresent(user -> {
          throw new DuplicatedKeyException("중복 이메일이 있습니다.");
        });
    }

    return userMapper.entityToDto(save(entity));
  }


  public AuthDto createAuthDto(String username, String originPassword) {

    // authenticate
    authenticationProvider.authenticate(
      new UsernamePasswordAuthenticationToken(
        username,
        originPassword
      )
    );

    final AppUserDetails userDetails = (AppUserDetails)userDetailsService.loadUserByUsername(username);
    userDetails.setType(AppUserDetailsType.USER);
    final String token = jwtTokenUtil.generateToken(userDetails);
//    jwtTokenUtil.refreshToken(token);

    AuthDto authDto = new AuthDto();

    authDto.setAccessToken(token);
    authDto.setTokenType(AuthTokenType.BEARER);
    authDto.setUserName(username);

    return authDto;
  }

  public AuthDto createSocialUserAuthDto(String username) {

    final AppUserDetails userDetails = (AppUserDetails)userDetailsService.loadUserByUsername(username);
    userDetails.setType(AppUserDetailsType.USER);
    final String token = jwtTokenUtil.generateToken(userDetails);

    AuthDto authDto = new AuthDto();

    authDto.setAccessToken(token);
    authDto.setTokenType(AuthTokenType.BEARER);
    authDto.setUserName(username);

    return authDto;
  }

  @Transactional
  public UserAuthDto login(UserLoginDto loginDto) {
    String username = loginDto.getLoginId();
    String password = loginDto.getPassword();

    AuthDto authDto = createAuthDto(username, password);

    UserAuthDto userAuthDto = new UserAuthDto();

    User user = repository.findByLoginId(username).get();
    userAuthDto.setUser(userMapper.entityToDto(user));
    userAuthDto.setAuth(authDto);

    return userAuthDto;
  }

  @Transactional(readOnly = true)
  public UserStatisticsViewDto getUserStatistics(Integer age) {
    if(age < 20) {
      age = 19;
    } else if (age > 80){
      age = 80;
    } else {
      age = age / 10 * 10;
    }
    UserStatistics userStatistics = userStatisticsRepository.findByAge(age);

    UserStatisticsViewDto userStatisticsViewDto = new UserStatisticsViewDto();

    userStatisticsViewDto.setHeight(userStatistics.getHeight());
    userStatisticsViewDto.setWeight(userStatistics.getWeight());

    return userStatisticsViewDto;


  }


}
