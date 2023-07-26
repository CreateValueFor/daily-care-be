package com.example.dailycarebe.app.exercise.repository;

import com.example.dailycarebe.app.exercise.model.Exercise;
import com.example.dailycarebe.app.exercise.model.PostureType;
import com.example.dailycarebe.base.orm.repository.BaseRepository;

public interface ExerciseRepository extends BaseRepository<Exercise> {

    Exercise findByPostureTypeAndName(PostureType postureType, String name);
}
