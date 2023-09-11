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

                //14. 1주일 넘게 빠졌을 경우에 빠진 전주로 다시 시작
                if(localDate.isAfter(user.getNextWeek().plusDays(6))) {
                    while(localDate.isBefore(user.getNextWeek())) {
                        user.setNextWeek(user.getNextWeek().plusWeeks(1));
                    }
                    user.setCourseDay(1);
                    user.setIsCourseUpgradable(true);
                }
                //오늘의 운동 생성 시작
                //주가 아직 안바뀌어있다면? 바꿔주기 5
                if(localDate.isAfter(user.getNextWeek().minusDays(1))) {
                    if(user.getIsCourseUpgradable() && localDate.isBefore(user.getNextWeek().plusDays(6))) {
                        if(user.getCourseWeekType() == CourseWeekType.FIRST) {
                            user.setCourseWeekType(CourseWeekType.SECOND);
                        } else if(user.getCourseWeekType() == CourseWeekType.SECOND) {
                            user.setCourseWeekType(CourseWeekType.THIRD);
                        }

                        if(user.getUpperCourseWeekType() == CourseWeekType.FIRST) {
                            user.setUpperCourseWeekType(CourseWeekType.SECOND);
                        } else if(user.getUpperCourseWeekType() == CourseWeekType.SECOND) {
                            user.setUpperCourseWeekType(CourseWeekType.THIRD);
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
//                    exerciseRecord.setCourseWeekType(user.getCourseWeekType());
                    exerciseRecord.setToday(localDate);
                    exerciseRecord.setUser(user);
                    exerciseRecordRepository.save(exerciseRecord);
                } else {

                    LocalDate searching = localDate.minusDays(1);
                    List<ExerciseRecord> yesterdayExerciseList =
                            exerciseRecordRepository
                                    .findAllByUserAndToday(user, searching);

                    while (yesterdayExerciseList.size() < 2 ) {
                        if(yesterdayExerciseList.size() < 1) {
                            ExerciseRecord exerciseRecord = new ExerciseRecord();
                            exerciseRecord.setCourseType(CourseType.REST);
                            exerciseRecord.setCourseDay(user.getCourseDay());
                            exerciseRecord.setCourseWeekType(user.getCourseWeekType());
                            exerciseRecord.setToday(searching);
                            exerciseRecord.setUser(user);
                            exerciseRecordRepository.save(exerciseRecord);

                        }

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
//        if(user.getCourseWeekType() == CourseWeekType.THIRD) {
//            if(user.getCourseDay() == 3) {
//                List<ExerciseAdditional> e = exerciseAdditionalRepository.findAllByCourseDayAndCourseWeekAndCourseType(
//                        2, user.getCourseWeek(), CourseType.HIGH
//                );
//
//                e.forEach(exerciseAdditional -> {
//                    ExerciseRecord tmp = new ExerciseRecord();
//                    tmp.setExercise(exerciseAdditional.getExercise());
//                    exerciseList.add(tmp);
//                });
//
//            } else if (user.getCourseDay() == 5) {
//                List<ExerciseAdditional> e = exerciseAdditionalRepository.findAllByCourseDayAndCourseWeekAndCourseType(
//                        1, user.getCourseWeek(), CourseType.MEDIUM
//                );
//
//                e.forEach(exerciseAdditional -> {
//                    ExerciseRecord tmp = new ExerciseRecord();
//                    tmp.setExercise(exerciseAdditional.getExercise());
//                    exerciseList.add(tmp);
//                });
//
//            }
//        }
//
        return exerciseRecordMapper.entitiesToDtos(exerciseList);
    }



    public void userExerciseInitiate(User user) {
        UserExerciseType exerciseType = user.getUserExerciseType();

        if(exerciseType != null) {
            List<Exercise> exerciseList =
            exerciseInitiateRepository.
                    findAllByUserExerciseTypeAndCourseWeek
                            (user.getUserExerciseType(), user.getCourseWeek())
                    .stream().map(ExerciseInitiate::getExercise).collect(Collectors.toList());

            exerciseList.forEach(exercise -> {
                ExerciseRecord exerciseRecord = new ExerciseRecord();

                exerciseRecord.setUser(user);
                if(user.getIsUpper()) {
                    exerciseRecord.setExercise(
                            repository.findByPostureTypeAndName(PostureType.UPPER, exercise.getName())
                    );

                } else {
                    exerciseRecord.setExercise(exercise);

                }
                exerciseRecord.setCourseType(CourseType.LOW);
                exerciseRecord.setToday(LocalDate.now());
                exerciseRecord.setCourseWeekType(user.getCourseWeekType());
                exerciseRecord.setCourseDay(user.getCourseDay());
                exerciseRecord.setIsCourseUpgradable(true);
                exerciseRecord.setName(exercise.getName());
                exerciseRecordRepository.save(exerciseRecord);
            });
        }
    }


    @Transactional
    public void createExerciseTodayFromYesterday(User user, List<ExerciseRecord> yesterdayExerciseList, LocalDate localDate) {
        List<ExerciseRecord> yesterDayExerciseList1 = yesterdayExerciseList;

        //3. 2세트를 한 동작에 "어렵다"는 코스와 자세에 영향을 미치지 않음.
        if(yesterDayExerciseList1.size() > 13) {
            yesterDayExerciseList1 = yesterDayExerciseList1.subList(0, 13);
        }

        yesterDayExerciseList1.forEach(beforeExerciseRecord -> {

            ExerciseRecord newExerciseRecord = getEvaluate(beforeExerciseRecord);

            int newCourseDay = newExerciseRecord.getCourseDay() +1;

            if(newCourseDay == 4) {
                newCourseDay = newCourseDay +1;
            }

            CourseType courseType =
                    courseMapRepository
                            .findByCourseWeekTypeAndCourseDay
                                    (newExerciseRecord.getCourseWeekType(), newCourseDay)
                            .getCourseType();

            newExerciseRecord.setCourseType(courseType);
            newExerciseRecord.setCourseDay(newCourseDay);

            //13. 제공된 운동 영상 시간에 못한 동작들의 평가는 전체 평가로 입력되어 다음날 제공
            if(beforeExerciseRecord.getExerciseEvaluationType() == ExerciseEvaluationType.NULL) {
                newExerciseRecord.setCourseType(beforeExerciseRecord.getCourseType());
                newExerciseRecord.setCourseDay(beforeExerciseRecord.getCourseDay());

            }

//            newExerciseRecord.setCourseWeekType(newExerciseRecord.getCourseWeekType());
            newExerciseRecord.setToday(localDate);

//            newExerciseRecord.setIsCourseUpgradable(newExerciseRecord.getIsCourseUpgradable());
            newExerciseRecord.setName(newExerciseRecord.getExercise().getName());

            exerciseRecordRepository.save(newExerciseRecord);

        });


        List<ExerciseRecord> exerciseList =
                exerciseRecordRepository.findAllByUserAndToday(user, localDate);

        if(exerciseList.size() > 12) {
//            //3주차 이상 쉽다 같은강도 한번더 로직
            exerciseList.forEach(todayExerciseRecord -> {

                if(todayExerciseRecord.getCourseWeekType() != CourseWeekType.THIRD || todayExerciseRecord.getCourseDay() != 6) {
                    ExerciseRecord exerciseRecord = exerciseRecordRepository.findByUserAndCourseTypeAndCourseWeekTypeAndNameAndTodayAfterAndExerciseEvaluationType(
                            user, todayExerciseRecord.getCourseType(), todayExerciseRecord.getCourseWeekType(), todayExerciseRecord.getExercise().getName(), user.getNextWeek().minusDays(8), ExerciseEvaluationType.EASY
                    );

                    if(exerciseRecord != null) {
                        if(todayExerciseRecord.getCourseDay() != 6 || todayExerciseRecord.getCourseWeekType() != CourseWeekType.THIRD || todayExerciseRecord.getCourseType() != CourseType.HIGH) {
                            ExerciseRecord newExerciseRecord = new ExerciseRecord();


                            newExerciseRecord.setUser(user);
                            newExerciseRecord.setExercise(todayExerciseRecord.getExercise());
                            newExerciseRecord.setCourseType(todayExerciseRecord.getCourseType());
                            newExerciseRecord.setCourseDay(todayExerciseRecord.getCourseDay());
                            newExerciseRecord.setCourseWeekType(todayExerciseRecord.getCourseWeekType());
                            newExerciseRecord.setToday(localDate);
                            newExerciseRecord.setName(todayExerciseRecord.getExercise().getName());
                            exerciseRecordRepository.save(newExerciseRecord);


                        }
                    }

                }
            });
        }

    }

    @Transactional
    public void createExerciseTodayNewWeek(User user, List<ExerciseRecord> yesterdayExerciseList, LocalDate localDate) {

        List<Exercise> notUpgradableList = yesterdayExerciseList.stream().filter(exerciseRecorde -> !exerciseRecorde.getIsCourseUpgradable()).map(ExerciseRecord::getExercise).collect(Collectors.toList());

        if(yesterdayExerciseList.size() > 13) {
            yesterdayExerciseList = yesterdayExerciseList.subList(0,13);
        }


        yesterdayExerciseList.forEach(exercise -> {

            if(
                    (exercise.getCourseWeekType() == CourseWeekType.FIRST
                            && exercise.getCourseDay() == 3)
                            ||
                            (exercise.getCourseWeekType() == CourseWeekType.SECOND
                                    && exercise.getCourseDay() == 3)
                            ||
                            (exercise.getCourseWeekType() == CourseWeekType.THIRD
                                    && exercise.getCourseDay() == 2)
            ) {
                //테스트 후 수정
                if(exercise.getExerciseEvaluationType() == ExerciseEvaluationType.DIFFICULT) {
                    exercise.setIsCourseUpgradable(false);
                }
            }

            ExerciseRecord exerciseRecord = new ExerciseRecord();

            exerciseRecord.setUser(user);
            if(user.getIsUpper()) {
                exerciseRecord.setExercise(
                        repository.findByPostureTypeAndName(PostureType.UPPER, exercise.getExercise().getName())
                );
            } else {
                if(user.getHasUpper() && exercise.getId() > 36) {
//                    if(user.getUpperCourseWeekType() == CourseWeekType.FIRST) {
                        exerciseRecord.setExercise(
                                repository.findByPostureTypeAndName(PostureType.EASY, exercise.getExercise().getName())
                        );
//
//                    } else if(user.getUpperCourseWeekType() == CourseWeekType.SECOND) {
//                        exerciseRecord.setExercise(
//                                repository.findByPostureTypeAndName(PostureType.NORMAL, exercise.getExercise().getName())
//                        );
//                    } else if (user.getUpperCourseWeekType() == CourseWeekType.THIRD) {
//                        exerciseRecord.setExercise(
//                                repository.findByPostureTypeAndName(PostureType.NORMAL, exercise.getExercise().getName())
//                        );
//                    }

                } else {
                    if(exercise.getIsCourseUpgradable() && !exercise.getIsAllNull()) {
                        if(exercise.getExercise().getPostureType() == PostureType.EASY) {
                            exerciseRecord.setExercise(repository.findByPostureTypeAndName(
                                    PostureType.NORMAL, exercise.getExercise().getName()
                            ));
                        } else if(exercise.getExercise().getPostureType() == PostureType.NORMAL) {
                            exerciseRecord.setExercise(repository.findByPostureTypeAndName(
                                    PostureType.DIFFICULT, exercise.getExercise().getName()
                            ));
                        } else {
                            exerciseRecord.setExercise(exercise.getExercise());

                        }

                    } else {
                        exerciseRecord.setExercise(exercise.getExercise());

                    }
                }

            }

            if(exercise.getIsCourseUpgradable() && !exercise.getIsAllNull()) {
                if(exercise.getCourseWeekType() == CourseWeekType.FIRST) {
                    exerciseRecord.setCourseWeekType(CourseWeekType.SECOND);
                } else {
                    exerciseRecord.setCourseWeekType(CourseWeekType.THIRD);
                }
            } else {
                exerciseRecord.setCourseWeekType(exercise.getCourseWeekType());
            }

            if(user.getHasUpper() && exercise.getId() > 36) {
                exerciseRecord.setCourseWeekType(CourseWeekType.FIRST);
            }

            CourseType courseType =
                    courseMapRepository
                            .findByCourseWeekTypeAndCourseDay
                                    (exerciseRecord.getCourseWeekType(), user.getCourseDay())
                            .getCourseType();


            exerciseRecord.setCourseType(courseType);

//            if(user.getUpperCourseWeekType() != null ) {
//                exerciseRecord.setCourseWeekType(user.getUpperCourseWeekType());
//            }

            exerciseRecord.setCourseDay(user.getCourseDay());
            exerciseRecord.setToday(localDate);
            exerciseRecord.setIsCourseUpgradable(true);
            exerciseRecord.setName(exerciseRecord.getExercise().getName());
            exerciseRecordRepository.save(exerciseRecord);

        });

//        List<ExerciseInitiate> initiates = exerciseInitiateRepository.findAllByUserExerciseTypeAndCourseWeek(user.getUserExerciseType(), user.getCourseWeek());
        List<ExerciseInitiate> initiates = exerciseInitiateRepository.findAllByUserExerciseTypeAndCourseWeekTypeAndCourseWeek(user.getUserExerciseType(), CourseWeekType.FIRST, user.getCourseWeek());

        initiates.forEach(exercise -> {

            ExerciseRecord exerciseRecord = new ExerciseRecord();

            exerciseRecord.setUser(user);
            if(user.getIsUpper()) {
                exerciseRecord.setExercise(
                        repository.findByPostureTypeAndName(PostureType.UPPER, exercise.getExercise().getName())
                );
            } else {
                exerciseRecord.setExercise(exercise.getExercise());

            }
                    CourseType courseType =
                courseMapRepository
                        .findByCourseWeekTypeAndCourseDay
                                (exercise.getCourseWeekType(), user.getCourseDay())
                        .getCourseType();


            exerciseRecord.setCourseType(courseType);

            exerciseRecord.setCourseWeekType(exercise.getCourseWeekType());

            exerciseRecord.setCourseDay(user.getCourseDay());
            exerciseRecord.setToday(localDate);
            exerciseRecord.setIsCourseUpgradable(true);
            exerciseRecord.setName(exerciseRecord.getExercise().getName());
            exerciseRecordRepository.save(exerciseRecord);
        });

    }

    @Transactional
    public ExerciseRecord getEvaluate(ExerciseRecord exerciseRecord) {

        ExerciseRecord newExerciseRecord = new ExerciseRecord();
        newExerciseRecord.setIsCourseUpgradable(exerciseRecord.getIsCourseUpgradable());
        newExerciseRecord.setExercise(exerciseRecord.getExercise());
        if(exerciseRecord.getExerciseEvaluationType() != ExerciseEvaluationType.NULL) {
            newExerciseRecord.setIsAllNull(false);
        } else {
            newExerciseRecord.setIsAllNull(exerciseRecord.getIsAllNull());
        }

        if(getContextUser().getIsUpper()) {

            newExerciseRecord.setExercise(exerciseRecord.getExercise());

        } else {
            if(exerciseRecord.getExerciseEvaluationType() == ExerciseEvaluationType.DIFFICULT) {
                //4. 코스 상향은 매주 증가하지만 첫번째 고강도에서 ‘어렵다’로 응답할 경우 안함.
                //두번째, 세번째 고강도에서 '어렵다'라고 해도 아무 영향 없음
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
                    //테스트 후 수정
                    newExerciseRecord.setIsCourseUpgradable(false);
                }
                Exercise exercise = exerciseRecord.getExercise();

                //6. 대상자가 어느 강도에서 든지 동작을 ‘어렵다’로 응답할 경우 다음날 자세 하향 (그 다음주에 영향 미치지 않음)
                if(exercise.getPostureType() == PostureType.DIFFICULT) {
                    exercise = repository.findByPostureTypeAndName(PostureType.NORMAL, exercise.getName());

                } else{
                    exercise = repository.findByPostureTypeAndName(PostureType.EASY, exercise.getName());
                }

                newExerciseRecord.setExercise(exercise);
            } else if(exerciseRecord.getExerciseEvaluationType() == ExerciseEvaluationType.EASY) {
                //7. 첫번째 고강도에서 ‘쉽다’로 응답할 경우 다음날 자세 상향  (그 다음주에 영향 미치지 않음)
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
                    //7. 첫번째 고강도에서 ‘쉽다’로 응답할 경우 다음날 자세 상향  (그 다음주에 영향 미치지 않음)
                    Exercise exercise = exerciseRecord.getExercise();

                    if(exercise.getPostureType() == PostureType.EASY) {
                        exercise = repository.findByPostureTypeAndName(PostureType.NORMAL, exercise.getName());

                    } else{
                        exercise = repository.findByPostureTypeAndName(PostureType.DIFFICULT, exercise.getName());
                    }

                    newExerciseRecord.setExercise(exercise);
                }

            }

        }
        newExerciseRecord.setToday(exerciseRecord.getToday());
        newExerciseRecord.setCourseWeekType(exerciseRecord.getCourseWeekType());
        newExerciseRecord.setCourseDay(exerciseRecord.getCourseDay());
        newExerciseRecord.setCourseType(exerciseRecord.getCourseType());
        newExerciseRecord.setUser(exerciseRecord.getUser());
        return newExerciseRecord;
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
