package com.example.dailycarebe.app.exercise.mapper;

import com.example.dailycarebe.app.exercise.dto.ExerciseViewDto;
import com.example.dailycarebe.app.exercise.model.Exercise;
import com.example.dailycarebe.base.orm.mapper.HashIdsMapper;
import com.example.dailycarebe.user.mapper.UserIdMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring", uses = {HashIdsMapper.class, UserIdMapper.class})
public interface ExerciseMapper {
    @Mapping(target = "uuid", source = "id")
    ExerciseViewDto entityToDto(Exercise exercise);

    List<ExerciseViewDto> entitiesToDtos(List<Exercise> entities);
}
