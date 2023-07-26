package com.example.dailycarebe.app.exercise.record.repository;

import com.example.dailycarebe.app.exercise.record.model.ExerciseRecord;
import com.example.dailycarebe.base.orm.repository.BaseRepository;
import com.example.dailycarebe.user.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExerciseRecordRepository extends BaseRepository<ExerciseRecord> {

    List<ExerciseRecord> findAllByUser(User user);

    List<ExerciseRecord> findAllByUserAndToday(User user, LocalDate today);
}
