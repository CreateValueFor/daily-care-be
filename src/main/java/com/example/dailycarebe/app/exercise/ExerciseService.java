package com.example.dailycarebe.app.exercise;

import com.example.dailycarebe.app.exercise.dto.ExerciseViewDto;
import com.example.dailycarebe.app.exercise.initiate.model.ExerciseInitiate;
import com.example.dailycarebe.app.exercise.initiate.repository.ExerciseInitiateRepository;
import com.example.dailycarebe.app.exercise.mapper.ExerciseMapper;
import com.example.dailycarebe.app.exercise.model.*;
import com.example.dailycarebe.app.exercise.record.model.ExerciseRecord;
import com.example.dailycarebe.app.exercise.record.repository.ExerciseRecordRepository;
import com.example.dailycarebe.app.exercise.repository.CourseMapRepository;
import com.example.dailycarebe.app.exercise.repository.ExerciseRepository;
import com.example.dailycarebe.base.BaseService;
import com.example.dailycarebe.user.model.User;
import com.example.dailycarebe.user.model.UserExerciseType;
import com.example.dailycarebe.user.repository.UserRepository;
import com.example.dailycarebe.util.SecurityContextUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExerciseService extends BaseService<Exercise, ExerciseRepository> {

    private final UserRepository userRepository;

    private final ExerciseMapper exerciseMapper;

    private final ExerciseRecordRepository exerciseRecordRepository;

    private final ExerciseInitiateRepository exerciseInitiateRepository;

    private final CourseMapRepository courseMapRepository;

    @Transactional
    public List<ExerciseViewDto> getMyTodayExercise() {

        User user = userRepository.getReferenceById(SecurityContextUtil.getUserId());

        if(LocalDate.now().isAfter(user.getNextWeek())) {
            if(user.getIsCourseUpgradable()) {
                if(user.getCourseWeekType() == CourseWeekType.FIRST) {
                    user.setCourseWeekType(CourseWeekType.SECOND);
                } else if(user.getCourseWeekType() == CourseWeekType.SECOND) {
                    user.setCourseWeekType(CourseWeekType.THIRD);
                }
            }

            user.setCourseDay(1);
            user.setIsCourseUpgradable(true);
            user.setNextWeek(user.getNextWeek().plusWeeks(1));
        }


        if(exerciseRecordRepository.findAllByUserAndToday(user, LocalDate.now()).isEmpty()) {
            user.setCourseDay(user.getCourseDay() + 1);
            if(exerciseRecordRepository.findAllByUser(user).isEmpty()) {
                userExerciseInitiate(user);


            } else {
                List<ExerciseRecord> yesterdayExerciseList =
                        exerciseRecordRepository
                                .findAllByUserAndToday(user, LocalDate.now().minusDays(1));

                createExerciseTodayFromYesterday(user, yesterdayExerciseList);

            }
        }


        List<Exercise> exerciseList =
                exerciseRecordRepository.findAllByUserAndToday(user, LocalDate.now())
                        .stream().map(ExerciseRecord::getExercise).collect(Collectors.toList());


        return exerciseMapper.entitiesToDtos(exerciseList);
    }



    public void userExerciseInitiate(User user) {
        UserExerciseType exerciseType = user.getUserExerciseType();

        if(exerciseType == UserExerciseType.GASSY) {
            List<Exercise> exerciseList =
            exerciseInitiateRepository.
                    findAllByUserExerciseTypeAndCourseWeekType
                            (user.getUserExerciseType(), user.getCourseWeekType())
                    .stream().map(ExerciseInitiate::getExercise).collect(Collectors.toList());

            exerciseList.forEach(exercise -> {
                ExerciseRecord exerciseRecord = new ExerciseRecord();

                exerciseRecord.setUser(user);
                exerciseRecord.setExercise(exercise);
                exerciseRecord.setCourseType(CourseType.LOW);
                exerciseRecord.setToday(LocalDate.now());

                exerciseRecordRepository.save(exerciseRecord);
            });
        }
    }


    @Transactional
    public void createExerciseTodayFromYesterday(User user, List<ExerciseRecord> yesterdayExerciseList) {
        List<Exercise> difficult =
                yesterdayExerciseList.stream().filter(exerciseRecord -> exerciseRecord.getExerciseEvaluationType() == ExerciseEvaluationType.DIFFICULT)
                .collect(Collectors.toList())
                        .stream().map(ExerciseRecord::getExercise).collect(Collectors.toList());

        List<Exercise> exerciseList =
                exerciseInitiateRepository
                .findAllByUserExerciseTypeAndCourseWeekType
                        (user.getUserExerciseType(), user.getCourseWeekType())
                        .stream().map(ExerciseInitiate::getExercise).collect(Collectors.toList());

        CourseType courseType =
        courseMapRepository
                .findByCourseWeekTypeAndCourseDay
                        (user.getCourseWeekType(), user.getCourseDay())
                .getCourseType();

        exerciseList.forEach(exercise -> {
            if(difficult.contains(exercise)) {
                if(exercise.getPostureType() == PostureType.DIFFICULT) {
                    exercise = repository.findByPostureTypeAndName(PostureType.NORMAL, exercise.getName());

                } else{
                    exercise = repository.findByPostureTypeAndName(PostureType.EASY, exercise.getName());

                }
            }
            ExerciseRecord exerciseRecord = new ExerciseRecord();

            exerciseRecord.setUser(user);
            exerciseRecord.setExercise(exercise);
            exerciseRecord.setCourseType(courseType);
            exerciseRecord.setCourseWeekType(user.getCourseWeekType());
            exerciseRecord.setCourseDay(user.getCourseDay());
            exerciseRecord.setToday(LocalDate.now());

            exerciseRecordRepository.save(exerciseRecord);
        });

    }

    @Transactional
    public void getEvaluate(ExerciseRecord exerciseRecord) {
        User user = userRepository.getReferenceById(SecurityContextUtil.getUserId());

        if(
                (exerciseRecord.getCourseWeekType() == CourseWeekType.FIRST
                && exerciseRecord.getCourseDay() == 3)
                ||
                (exerciseRecord.getCourseWeekType() == CourseWeekType.SECOND
                && exerciseRecord.getCourseDay() == 3)
                ||
                (exerciseRecord.getCourseWeekType() == CourseWeekType.THIRD
                && exerciseRecord.getCourseDay() == 2)
        ) {
            if(exerciseRecord.getExerciseEvaluationType() == ExerciseEvaluationType.DIFFICULT) {
                user.setIsCourseUpgradable(false);
            }


        }
    }
}
