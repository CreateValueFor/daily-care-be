package com.example.dailycarebe.app.food.mapper;

import com.example.dailycarebe.app.exercise.dto.ExerciseRecordViewDto;
import com.example.dailycarebe.app.exercise.mapper.ExerciseMapper;
import com.example.dailycarebe.app.exercise.record.dto.ExerciseRecordEditDto;
import com.example.dailycarebe.app.exercise.record.model.ExerciseRecord;
import com.example.dailycarebe.app.food.dto.FoodRegisterDto;
import com.example.dailycarebe.app.food.dto.FoodViewDto;
import com.example.dailycarebe.app.food.model.Food;
import com.example.dailycarebe.base.orm.mapper.HashIdsMapper;
import com.example.dailycarebe.user.mapper.UserIdMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring", uses = {HashIdsMapper.class, UserIdMapper.class})
public interface FoodMapper {
    @Mapping(target = "uuid", source = "id")
    FoodViewDto entityToDto(Food entity);

    List<FoodViewDto> entitiesToDtos(List<Food> entities);

    Food registerDtoToEntity(FoodRegisterDto editDto);

}
