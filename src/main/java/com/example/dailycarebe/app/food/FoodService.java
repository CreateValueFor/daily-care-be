package com.example.dailycarebe.app.food;

import com.example.dailycarebe.app.food.dto.FoodEditDto;
import com.example.dailycarebe.app.food.dto.FoodRegisterDto;
import com.example.dailycarebe.app.food.dto.FoodViewDto;
import com.example.dailycarebe.app.food.mapper.FoodMapper;
import com.example.dailycarebe.app.food.model.Food;
import com.example.dailycarebe.app.food.model.FoodLibrary;
import com.example.dailycarebe.app.food.repository.FoodLibraryRepository;
import com.example.dailycarebe.app.food.repository.FoodRepository;
import com.example.dailycarebe.base.BaseService;
import com.example.dailycarebe.user.model.User;
import com.example.dailycarebe.util.SecurityContextUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodService extends BaseService<Food, FoodRepository> {
    private final FoodLibraryRepository foodLibraryRepository;

    private final FoodMapper foodMapper;
    @Transactional(readOnly = true)
    public List<String> search(String name) {
//        foodLibraryRepository.searchByTitleLike(name);

        return foodLibraryRepository.searchByTitleLike(name).get().stream().map(FoodLibrary::getName).collect(Collectors.toList());
    }

    @Transactional
    public List<FoodViewDto> register(FoodRegisterDto registerDto) {
        User user = getContextUser();

        Food food = foodMapper.registerDtoToEntity(registerDto);
        food.setUser(user);

        save(food);

        return getAll();
    }
    @Transactional
    public List<FoodViewDto> edit(FoodEditDto editDto) {
        User user = getContextUser();

        Food food = repository.findByUserAndStartTime(user, editDto.getStartTime());

        food.setSubject(editDto.getSubject());

        save(food);

        return getAll();
    }

    @Transactional(readOnly = true)
    public List<FoodViewDto> getAll() {
        User user = getContextUser();


        return foodMapper.entitiesToDtos(repository.findAllByUser(user));
    }
}
