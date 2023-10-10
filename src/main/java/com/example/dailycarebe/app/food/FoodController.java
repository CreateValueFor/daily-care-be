package com.example.dailycarebe.app.food;

import com.example.dailycarebe.app.food.dto.FoodEditDto;
import com.example.dailycarebe.app.food.dto.FoodRegisterDto;
import com.example.dailycarebe.app.food.dto.FoodViewDto;
import com.example.dailycarebe.base.BaseComponent;
import com.example.dailycarebe.rest.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/food")
public class FoodController extends BaseComponent {
    private final FoodService foodService;

    @GetMapping("/search")
    public ResponseEntity<CustomResponse<List<String>>> searchKeyword(String keyword) {
        return CustomResponse.ok(foodService.search(keyword));
    }

    @PostMapping("/register")
    public ResponseEntity<CustomResponse<List<FoodViewDto>>> register(@RequestBody FoodRegisterDto registerDto) {
        return CustomResponse.ok(foodService.register(registerDto));

    }

    @GetMapping("/get")
    public ResponseEntity<CustomResponse<List<FoodViewDto>>> getAll() {
        return CustomResponse.ok(foodService.getAll());
    }

    @GetMapping("/one")
    public ResponseEntity<CustomResponse<List<FoodViewDto>>> getOne(String localDate) {
        return CustomResponse.ok(foodService.getDay(LocalDate.parse(localDate)));
    }

    @PostMapping("/edit")
    public ResponseEntity<CustomResponse<List<FoodViewDto>>> edit(@RequestBody FoodEditDto editDto) {
        return CustomResponse.ok(foodService.edit(editDto));

    }
}
