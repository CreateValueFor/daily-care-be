package com.example.dailycarebe.app.movement;

import com.example.dailycarebe.app.movement.dto.MovementRegisterDto;
import com.example.dailycarebe.app.movement.dto.MovementViewDto;
import com.example.dailycarebe.base.BaseComponent;
import com.example.dailycarebe.rest.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/movements")
public class MovementController extends BaseComponent {

    private final MovementService movementService;


    @PostMapping("/register")
    public ResponseEntity<CustomResponse<MovementViewDto>> register(@RequestBody MovementRegisterDto registerDto) {
        MovementViewDto movementViewDto = movementService.registerMovement(registerDto);
        return CustomResponse.ok(movementViewDto);
    }

    @GetMapping("/my")
    public ResponseEntity<CustomResponse<List<MovementViewDto>>> getMyMovements() {
        return CustomResponse.ok(movementService.getMyMovements());
    }
}
