package com.example.dailycarebe.app.exercise.repository;

import com.example.dailycarebe.app.exercise.model.CourseType;
import com.example.dailycarebe.app.exercise.model.ExerciseAdditional;
import com.example.dailycarebe.base.orm.repository.BaseRepository;

import java.util.List;

public interface ExerciseAdditionalRepository extends BaseRepository<ExerciseAdditional> {
    List<ExerciseAdditional> findAllByCourseDayAndCourseWeekAndCourseType(Integer courseDay, Integer courseWeek, CourseType courseType);
}
