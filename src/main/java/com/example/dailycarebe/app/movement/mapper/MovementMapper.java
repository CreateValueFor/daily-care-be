package com.example.dailycarebe.app.movement.mapper;

import com.example.dailycarebe.app.movement.dto.MovementRegisterDto;
import com.example.dailycarebe.app.movement.dto.MovementViewDto;
import com.example.dailycarebe.app.movement.model.Movement;
import com.example.dailycarebe.app.movement.model.MovementDetail;
import com.example.dailycarebe.base.orm.mapper.HashIdsMapper;
import com.example.dailycarebe.user.mapper.UserIdMapper;
import org.mapstruct.*;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring", uses = {HashIdsMapper.class, UserIdMapper.class, MovementDetailMapper.class})
public interface MovementMapper {
    @Mapping(target = "uuid", source = "id")
    MovementViewDto entityToDto(Movement movement);

    List<MovementViewDto> entitiesToDtos(List<Movement> entities);

    Movement registerDtoToEntity(MovementRegisterDto registerDto);

    @AfterMapping
    default void onEventMapped(@MappingTarget Movement movement) {
        Set<MovementDetail> movementDetails = movement.getMovementDetails();
        if (!CollectionUtils.isEmpty(movementDetails)) {
            for (MovementDetail movementDetail : movementDetails) {
                movementDetail.setMovement(movement);
            }
        }
    }
}
