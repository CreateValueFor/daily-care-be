package com.example.dailycarebe.app.exercise.repository;

import com.example.dailycarebe.app.exercise.model.CourseMap;
import com.example.dailycarebe.app.exercise.model.CourseWeekType;
import com.example.dailycarebe.base.orm.repository.BaseRepository;

public interface CourseMapRepository extends BaseRepository<CourseMap> {
    CourseMap findByCourseWeekTypeAndCourseDay(CourseWeekType courseWeekType, Integer courseDay);
}
