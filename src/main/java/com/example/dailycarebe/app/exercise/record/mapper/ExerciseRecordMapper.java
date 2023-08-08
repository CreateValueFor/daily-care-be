package com.example.dailycarebe.app.exercise.record.mapper;

import com.example.dailycarebe.app.exercise.dto.ExerciseRecordViewDto;
import com.example.dailycarebe.app.exercise.dto.ExerciseViewDto;
import com.example.dailycarebe.app.exercise.mapper.ExerciseMapper;
import com.example.dailycarebe.app.exercise.model.Exercise;
import com.example.dailycarebe.app.exercise.record.dto.ExerciseRecordEditDto;
import com.example.dailycarebe.app.exercise.record.model.ExerciseRecord;
import com.example.dailycarebe.base.orm.mapper.HashIdsMapper;
import com.example.dailycarebe.user.mapper.UserIdMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring", uses = {HashIdsMapper.class, UserIdMapper.class, ExerciseMapper.class})
public interface ExerciseRecordMapper {
    @Mapping(target = "uuid", source = "id")
    ExerciseRecordViewDto entityToDto(ExerciseRecord entity);

    List<ExerciseRecordViewDto> entitiesToDtos(List<ExerciseRecord> entities);

    @Mapping(target = "id", source = "uuid")
    ExerciseRecord editDtoToEntity(ExerciseRecordEditDto editDto);
}
