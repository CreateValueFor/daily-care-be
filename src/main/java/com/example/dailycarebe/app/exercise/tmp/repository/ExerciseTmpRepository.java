package com.example.dailycarebe.app.exercise.tmp.repository;

import com.example.dailycarebe.app.exercise.tmp.model.ExerciseTmp;
import com.example.dailycarebe.base.orm.repository.BaseRepository;
import com.example.dailycarebe.user.model.User;

import java.time.LocalDate;
import java.util.List;

public interface ExerciseTmpRepository extends BaseRepository<ExerciseTmp> {

    List<ExerciseTmp> findAllByUserAndToday(User user, LocalDate today);
}
