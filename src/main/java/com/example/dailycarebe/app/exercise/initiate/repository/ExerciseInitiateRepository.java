package com.example.dailycarebe.app.exercise.initiate.repository;

import com.example.dailycarebe.app.exercise.initiate.model.ExerciseInitiate;
import com.example.dailycarebe.app.exercise.model.CourseWeekType;
import com.example.dailycarebe.base.orm.repository.BaseRepository;
import com.example.dailycarebe.user.model.UserExerciseType;

import java.util.List;

public interface ExerciseInitiateRepository extends BaseRepository<ExerciseInitiate> {
    List<ExerciseInitiate> findAllByUserExerciseTypeAndCourseWeekType(UserExerciseType userExerciseType, CourseWeekType courseWeekType);
}
