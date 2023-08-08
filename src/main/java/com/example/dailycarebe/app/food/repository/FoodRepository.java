package com.example.dailycarebe.app.food.repository;

import com.example.dailycarebe.app.food.model.Food;
import com.example.dailycarebe.base.orm.repository.BaseRepository;
import com.example.dailycarebe.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface FoodRepository extends BaseRepository<Food> {
    List<Food> findAllByUser(User user);

    Food findByUserAndStartTime(User user, LocalDateTime startTime);
}
