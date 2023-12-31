package com.example.dailycarebe.app.exercise;

import com.example.dailycarebe.app.exercise.dto.ExerciseRecordViewDto;
import com.example.dailycarebe.app.exercise.dto.ExerciseViewDto;
import com.example.dailycarebe.app.exercise.model.ExerciseEvaluationType;
import com.example.dailycarebe.app.exercise.record.dto.ExerciseRecordEditDto;
import com.example.dailycarebe.app.exercise.tmp.dto.ExerciseTmpViewDto;
import com.example.dailycarebe.app.exercise.tmp.model.ExerciseTmp;
import com.example.dailycarebe.base.BaseComponent;
import com.example.dailycarebe.rest.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/exercise")
public class ExerciseController extends BaseComponent {

    private final ExerciseService exerciseService;

    @PostMapping("/my")
    public ResponseEntity<CustomResponse<List<ExerciseTmpViewDto>>> getMy(String localDate) {
        return CustomResponse.ok(exerciseService.getMyTodayExercise(LocalDate.parse(localDate)));
    }

    @PostMapping("/overall")
    public void overall(ExerciseEvaluationType exerciseEvaluationType) {
        exerciseService.overall(exerciseEvaluationType);
    }

    @PostMapping("/evaluate")
    public void evaluate(@RequestBody List<ExerciseRecordEditDto> editDtos) {
        exerciseService.evaluate(editDtos);
    }

    @PostMapping("/complete")
    public void complete(String uuid) {
        exerciseService.complete(uuid);
    }
}
