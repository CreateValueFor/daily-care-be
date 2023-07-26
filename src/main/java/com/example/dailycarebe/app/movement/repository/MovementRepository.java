package com.example.dailycarebe.app.movement.repository;

import com.example.dailycarebe.app.movement.model.Movement;
import com.example.dailycarebe.base.orm.repository.BaseRepository;
import com.example.dailycarebe.user.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MovementRepository extends BaseRepository<Movement> {
    Optional<Movement> findByLocalDateAndUser(LocalDate localDate, User user);

    List<Movement> findAllByUserOrderByLocalDateAsc(User user);
}
