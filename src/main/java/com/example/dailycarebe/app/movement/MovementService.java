package com.example.dailycarebe.app.movement;

import com.example.dailycarebe.app.movement.dto.MovementRegisterDto;
import com.example.dailycarebe.app.movement.dto.MovementViewDto;
import com.example.dailycarebe.app.movement.mapper.MovementMapper;
import com.example.dailycarebe.app.movement.model.Movement;
import com.example.dailycarebe.app.movement.model.MovementDetail;
import com.example.dailycarebe.app.movement.repository.MovementDetailRepository;
import com.example.dailycarebe.app.movement.repository.MovementRepository;
import com.example.dailycarebe.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MovementService extends BaseService<Movement, MovementRepository> {

    private final MovementMapper movementMapper;

    private final MovementDetailRepository movementDetailRepository;

    @Transactional
    public MovementViewDto registerMovement(MovementRegisterDto registerDto) {
        Movement movement = movementMapper.registerDtoToEntity(registerDto);

        repository.findByLocalDateAndUser(registerDto.getLocalDate(), getContextUser()).ifPresent(e -> {
            movement.setId(e.getId());
            Set<MovementDetail> movementDetailSet = movement.getMovementDetails();
            movementDetailSet.addAll(e.getMovementDetails());
            movement.setMovementDetails(movementDetailSet);
        });
        movement.setUser(getContextUser());
        save(movement);
        return movementMapper.entityToDto(movement);
    }

    @Transactional(readOnly = true)
    public List<MovementViewDto> getMyMovements() {
        return movementMapper.entitiesToDtos(repository.findAllByUserOrderByLocalDateAsc(getContextUser()));
    }

    @Transactional
    public void deleteDetail(String uuid) {
        MovementDetail movement = movementDetailRepository.getReferenceById(convertToId(uuid));

        movementDetailRepository.delete(movement);
    }
}
