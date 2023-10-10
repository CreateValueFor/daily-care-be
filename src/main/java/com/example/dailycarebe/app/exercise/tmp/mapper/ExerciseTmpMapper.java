package com.example.dailycarebe.app.exercise.tmp.mapper;

import com.example.dailycarebe.app.exercise.mapper.ExerciseMapper;
import com.example.dailycarebe.app.exercise.record.mapper.ExerciseRecordMapper;
import com.example.dailycarebe.app.exercise.tmp.dto.ExerciseTmpViewDto;
import com.example.dailycarebe.app.exercise.tmp.model.ExerciseTmp;
import com.example.dailycarebe.base.orm.mapper.HashIdsMapper;
import com.example.dailycarebe.user.mapper.UserIdMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring", uses = {HashIdsMapper.class, UserIdMapper.class, ExerciseMapper.class, ExerciseRecordMapper.class})
public interface ExerciseTmpMapper {

    @Mapping(target = "uuid", source = "id")
    ExerciseTmpViewDto entityToDto(ExerciseTmp exerciseTmp);

    List<ExerciseTmpViewDto> entitiesToDtos(List<ExerciseTmp> exerciseTmps);
}
