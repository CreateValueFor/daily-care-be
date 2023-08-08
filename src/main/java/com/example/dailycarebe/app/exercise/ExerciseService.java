package com.example.dailycarebe.app.exercise;

import com.example.dailycarebe.app.exercise.dto.ExerciseRecordViewDto;
import com.example.dailycarebe.app.exercise.dto.ExerciseViewDto;
import com.example.dailycarebe.app.exercise.initiate.model.ExerciseInitiate;
import com.example.dailycarebe.app.exercise.initiate.repository.ExerciseInitiateRepository;
import com.example.dailycarebe.app.exercise.mapper.ExerciseMapper;
import com.example.dailycarebe.app.exercise.model.*;
import com.example.dailycarebe.app.exercise.record.dto.ExerciseRecordEditDto;
import com.example.dailycarebe.app.exercise.record.mapper.ExerciseRecordMapper;
import com.example.dailycarebe.app.exercise.record.model.ExerciseRecord;
import com.example.dailycarebe.app.exercise.record.repository.ExerciseRecordRepository;
import com.example.dailycarebe.app.exercise.repository.CourseMapRepository;
import com.example.dailycarebe.app.exercise.repository.ExerciseAdditionalRepository;
import com.example.dailycarebe.app.exercise.repository.ExerciseRepository;
import com.example.dailycarebe.base.BaseService;
import com.example.dailycarebe.exception.InvalidKeyException;
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

    private final ExerciseRecordMapper exerciseRecordMapper;

    private final ExerciseRecordRepository exerciseRecordRepository;

    private final ExerciseInitiateRepository exerciseInitiateRepository;

    private final CourseMapRepository courseMapRepository;

    private final ExerciseAdditionalRepository exerciseAdditionalRepository;

    @Transactional
    public List<ExerciseRecordViewDto> getMyTodayExercise(LocalDate localDate) {

        User user = userRepository.getReferenceById(SecurityContextUtil.getUserId());


        //오늘의 운동이 없으면?
        if(exerciseRecordRepository.findAllByUserAndToday(user, localDate).isEmpty()) {
            if(exerciseRecordRepository.findAllByUser(user).isEmpty()) {//설마 처음임?
                userExerciseInitiate(user);


            } else {

                //오늘의 운동 생성 시작
                //주가 아직 안바뀌어있다면? 바꿔주기 5
                if(localDate.isAfter(user.getNextWeek().minusDays(1))) {
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
                    user.setCourseWeek(user.getCourseWeek() + 1);
                } else {
                    user.setCourseDay(user.getCourseDay() + 1);
                }


                CourseType type = courseMapRepository
                        .findByCourseWeekTypeAndCourseDay
                                (user.getCourseWeekType(), user.getCourseDay())
                        .getCourseType();

                if(type == CourseType.REST) { //휴식날
                    ExerciseRecord exerciseRecord = new ExerciseRecord();
                    exerciseRecord.setCourseType(CourseType.REST);
                    exerciseRecord.setCourseDay(user.getCourseDay());
                    exerciseRecord.setCourseWeekType(user.getCourseWeekType());
                    exerciseRecord.setToday(localDate);
                    exerciseRecord.setUser(user);
                    exerciseRecordRepository.save(exerciseRecord);
                } else {

                    LocalDate searching = localDate.minusDays(1);
                    List<ExerciseRecord> yesterdayExerciseList =
                            exerciseRecordRepository
                                    .findAllByUserAndToday(user, searching);

                    while (yesterdayExerciseList.size() == 1 ) {
                        searching = searching.minusDays(1);
                        yesterdayExerciseList = exerciseRecordRepository
                                .findAllByUserAndToday(user, searching);
                    }

                    if(user.getCourseDay() == 1) {

                        createExerciseTodayNewWeek(user, yesterdayExerciseList, localDate);

                    } else {

                        createExerciseTodayFromYesterday(user, yesterdayExerciseList, localDate);

                    }

                }

            }

        }


        List<ExerciseRecord> exerciseList =
                exerciseRecordRepository.findAllByUserAndToday(user, localDate);


        //2,3
        if(user.getCourseWeekType() == CourseWeekType.THIRD) {
            if(user.getCourseDay() == 3) {
                List<ExerciseAdditional> e = exerciseAdditionalRepository.findAllByCourseDayAndCourseWeekAndCourseType(
                        2, user.getCourseWeek(), CourseType.HIGH
                );

                e.forEach(exerciseAdditional -> {
                    ExerciseRecord tmp = new ExerciseRecord();
                    tmp.setExercise(exerciseAdditional.getExercise());
                    exerciseList.add(tmp);
                });

            } else if (user.getCourseDay() == 5) {
                List<ExerciseAdditional> e = exerciseAdditionalRepository.findAllByCourseDayAndCourseWeekAndCourseType(
                        1, user.getCourseWeek(), CourseType.MEDIUM
                );

                e.forEach(exerciseAdditional -> {
                    ExerciseRecord tmp = new ExerciseRecord();
                    tmp.setExercise(exerciseAdditional.getExercise());
                    exerciseList.add(tmp);
                });

            }
        }

        return exerciseRecordMapper.entitiesToDtos(exerciseList);
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
                exerciseRecord.setCourseWeekType(user.getCourseWeekType());
                exerciseRecord.setCourseDay(user.getCourseDay());

                exerciseRecordRepository.save(exerciseRecord);
            });
        }
    }


    @Transactional
    public void createExerciseTodayFromYesterday(User user, List<ExerciseRecord> yesterdayExerciseList, LocalDate localDate) {
        List<Exercise> difficult =
                yesterdayExerciseList.stream().filter(exerciseRecord -> exerciseRecord.getExerciseEvaluationType() == ExerciseEvaluationType.DIFFICULT)
                .collect(Collectors.toList())
                        .stream().map(ExerciseRecord::getExercise).collect(Collectors.toList());

        List<Exercise> easy =
                yesterdayExerciseList.stream().filter(exerciseRecord -> exerciseRecord.getExerciseEvaluationType() == ExerciseEvaluationType.EASY)
                        .collect(Collectors.toList())
                        .stream().map(ExerciseRecord::getExercise).collect(Collectors.toList());

        List<Exercise> beforeExerciseList = yesterdayExerciseList.stream().map(ExerciseRecord::getExercise).collect(Collectors.toList());


//        List<Exercise> exerciseList =
//                exerciseInitiateRepository
//                .findAllByUserExerciseTypeAndCourseWeekType
//                        (user.getUserExerciseType(), user.getCourseWeekType())
//                        .stream().map(ExerciseInitiate::getExercise).collect(Collectors.toList());

        CourseType courseType =
        courseMapRepository
                .findByCourseWeekTypeAndCourseDay
                        (user.getCourseWeekType(), user.getCourseDay())
                .getCourseType();

        beforeExerciseList.forEach(exercise -> {
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
            exerciseRecord.setToday(localDate);

            exerciseRecordRepository.save(exerciseRecord);
        });

        beforeExerciseList.forEach(exercise -> {
            if(easy.contains(exercise)) {

                ExerciseRecord exerciseRecord = new ExerciseRecord();

                exerciseRecord.setUser(user);
                exerciseRecord.setExercise(exercise);
                exerciseRecord.setCourseType(courseType);
                exerciseRecord.setCourseWeekType(user.getCourseWeekType());
                exerciseRecord.setCourseDay(user.getCourseDay());
                exerciseRecord.setToday(localDate);

                exerciseRecordRepository.save(exerciseRecord);

            }
        });

    }

    @Transactional
    public void createExerciseTodayNewWeek(User user, List<ExerciseRecord> yesterdayExerciseList, LocalDate localDate) {

        List<Exercise> beforeExerciseList = yesterdayExerciseList.stream().map(ExerciseRecord::getExercise).collect(Collectors.toList());

        List<ExerciseInitiate> initiates = exerciseInitiateRepository.findAllByUserExerciseTypeAndCourseWeek(user.getUserExerciseType(), user.getCourseWeek());


        CourseType courseType =
        courseMapRepository
                .findByCourseWeekTypeAndCourseDay
                        (user.getCourseWeekType(), user.getCourseDay())
                .getCourseType();

        initiates.forEach(exercise -> {

            ExerciseRecord exerciseRecord = new ExerciseRecord();

            exerciseRecord.setUser(user);
            exerciseRecord.setExercise(exercise.getExercise());
            exerciseRecord.setCourseType(courseType);
            exerciseRecord.setCourseWeekType(user.getCourseWeekType());
            exerciseRecord.setCourseDay(user.getCourseDay());
            exerciseRecord.setToday(localDate);

            exerciseRecordRepository.save(exerciseRecord);
        });

    }

    @Transactional
    public void getEvaluate(ExerciseRecord exerciseRecord) {
        //첫번째 고강도 쉬움 다음날 자세상향
        //4
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

    @Transactional
    public void overall(ExerciseEvaluationType exerciseEvaluationType) {
        User user = getContextUser();
        if(
                (user.getCourseWeekType() == CourseWeekType.FIRST
                        && user.getCourseDay() == 3)
                        ||
                        (user.getCourseWeekType() == CourseWeekType.SECOND
                                && user.getCourseDay() == 3)
                        ||
                        (user.getCourseWeekType() == CourseWeekType.THIRD
                                && user.getCourseDay() == 2)
        ) {
            if(exerciseEvaluationType == ExerciseEvaluationType.DIFFICULT) {
                user.setIsCourseUpgradable(false);
            }
        }
    }

    @Transactional
    public void evaluate(List<ExerciseRecordEditDto> editDtos) {
        editDtos.forEach(exerciseRecordEditDto -> {
            ExerciseRecord exerciseRecord = exerciseRecordRepository.getReferenceById(convertToId(exerciseRecordEditDto.getUuid()));
            exerciseRecord.setExerciseEvaluationType(exerciseRecordEditDto.getExerciseEvaluationType());
        });
    }


    @Transactional
    public void complete(String uuid) {
        ExerciseRecord exerciseRecord = exerciseRecordRepository.getReferenceById(convertToId(uuid));
        exerciseRecord.setComplete(true);
    }


}
