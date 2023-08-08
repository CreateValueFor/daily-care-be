package com.example.dailycarebe.app.food.repository;

import com.example.dailycarebe.app.food.model.FoodLibrary;
import com.example.dailycarebe.base.orm.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FoodLibraryRepository extends BaseRepository<FoodLibrary> {

    @Query("SELECT m FROM FoodLibrary m WHERE m.name LIKE %:name% ")
    Optional<List<FoodLibrary>> searchByTitleLike(@Param("name") String name);
}
