package com.example.dailycarebe.user;

import com.example.dailycarebe.auth.authentication.dto.UserAuthDto;
import com.example.dailycarebe.base.BaseComponent;
import com.example.dailycarebe.rest.CustomResponse;
import com.example.dailycarebe.user.dto.UserEditDto;
import com.example.dailycarebe.user.dto.UserLoginDto;
import com.example.dailycarebe.user.dto.UserRegisterDto;
import com.example.dailycarebe.user.dto.UserViewDto;
import com.example.dailycarebe.user.statistics.UserStatisticsViewDto;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/users")
public class UserController extends BaseComponent {
  private final UserService userService;

  @PostMapping
  public ResponseEntity<CustomResponse<UserAuthDto>> register(
    @Valid @RequestBody @ApiParam(name = "user 가입 정보", required = true) UserRegisterDto registerDto) {
    UserAuthDto userAuthDto = userService.registerUser(registerDto);
    return CustomResponse.ok(userAuthDto);
  }

  @GetMapping("/me")
  @PreAuthorize("hasAnyAuthority('APP_USER')")
  public ResponseEntity<CustomResponse<UserViewDto>> getMe() {
    UserViewDto me = userService.getMe();
    return CustomResponse.ok(me);
  }

  @PutMapping("/users")
  public ResponseEntity<CustomResponse<UserViewDto>> editUser(
    @Valid @RequestBody @ApiParam UserEditDto editDto) {
    UserViewDto userViewDto = userService.editUser(editDto);
    return CustomResponse.ok(userViewDto);
  }


  @PostMapping("/action/login")
  public ResponseEntity<CustomResponse<UserAuthDto>> login(
    @Valid @RequestBody UserLoginDto loginDto
  ) {
    return CustomResponse.ok(userService.login(loginDto));
  }

  @GetMapping("/age")
  public ResponseEntity<CustomResponse<UserStatisticsViewDto>> getStatistics(Integer age) {
    return CustomResponse.ok(userService.getUserStatistics(age));
  }

  @PostMapping("/pain")
  public ResponseEntity<CustomResponse<Boolean>> judgeUserPain() {
    return CustomResponse.ok(userService.judgeUserUpper());
  }
}
