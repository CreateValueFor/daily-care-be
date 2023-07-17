package com.example.dailycarebe.user.dto;

import com.example.dailycarebe.base.paging.dto.BasePaginationResultDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserPaginationResultDto extends BasePaginationResultDto<UserViewDto> {

}
