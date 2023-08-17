package com.example.dailycarebe.app.movement.mapper;

import com.example.dailycarebe.app.movement.dto.MovementDetailViewDto;
import com.example.dailycarebe.app.movement.dto.MovementViewDto;
import com.example.dailycarebe.app.movement.model.Movement;
import com.example.dailycarebe.app.movement.model.MovementDetail;
import com.example.dailycarebe.base.orm.mapper.HashIdsMapper;
import com.example.dailycarebe.user.mapper.UserIdMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring", uses = {HashIdsMapper.class, UserIdMapper.class})
public interface MovementDetailMapper {
    @Mapping(target = "uuid", source = "id")
    MovementDetailViewDto entityToDto(MovementDetail movement);
}
