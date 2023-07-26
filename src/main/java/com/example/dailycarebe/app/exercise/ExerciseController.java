package com.example.dailycarebe.app.exercise;

import com.example.dailycarebe.app.exercise.dto.ExerciseViewDto;
import com.example.dailycarebe.base.BaseComponent;
import com.example.dailycarebe.rest.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/exercise")
public class ExerciseController extends BaseComponent {

    private final ExerciseService exerciseService;

    @PostMapping("/my")
    public ResponseEntity<CustomResponse<List<ExerciseViewDto>>> getMy() {
        return CustomResponse.ok(exerciseService.getMyTodayExercise());
    }
}
