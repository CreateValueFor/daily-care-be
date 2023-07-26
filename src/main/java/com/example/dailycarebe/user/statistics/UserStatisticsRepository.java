package com.example.dailycarebe.user.statistics;

import com.example.dailycarebe.base.orm.repository.BaseRepository;
import com.example.dailycarebe.user.statistics.model.UserStatistics;

public interface UserStatisticsRepository extends BaseRepository<UserStatistics> {
    UserStatistics findByAge(Integer age);
}
