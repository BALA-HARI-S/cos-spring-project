package net.breezeware.service.impl;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import net.breezeware.dao.FoodItemRepository;
import net.breezeware.dto.food.item.CreateFoodItemDto;
import net.breezeware.dto.food.item.FoodItemDto;
import net.breezeware.dto.food.item.UpdateFoodItemDto;
import net.breezeware.entity.FoodItem;
import net.breezeware.exception.FoodItemAlreadyExistException;
import net.breezeware.exception.FoodItemException;
import net.breezeware.mapper.FoodItemMapper;
import net.breezeware.service.api.FoodItemService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class FoodItemServiceImpl implements FoodItemService {

    private final FoodItemRepository foodItemRepository;
    private final FoodItemMapper foodItemMapper;

    @Override
    public List<FoodItemDto> retrieveFoodItems() throws FoodItemException {
        log.info("Entering retrieveFoodItems()");
        List<FoodItem> foodItems = foodItemRepository.findAll();
        if (foodItems.isEmpty()) {
            throw new FoodItemException("No food Items found");
        }

        List<FoodItemDto> listOfFoodItems = foodItems.stream().map(foodItemMapper::foodItemToFoodItemDto).toList();
        log.info("Leaving retrieveFoodItems()");
        return listOfFoodItems;
    }

    @Override
    public FoodItemDto retrieveFoodItem(Long id) throws FoodItemException {
        log.info("Entering retrieveFoodItem()");
        FoodItem foodItem = foodItemRepository.findById(id)
                .orElseThrow(() -> new FoodItemException("Food item not found for id: " + id));
        FoodItemDto foodItemDto = foodItemMapper.foodItemToFoodItemDto(foodItem);
        log.info("Leaving retrieveFoodItem()");
        return foodItemDto;
    }

    @Override
    public FoodItemDto retrieveFoodItemByName(String name) throws FoodItemException {
        log.info("Entering retrieveFoodItemByName()");
        FoodItem foodItem = foodItemRepository.findByName(name)
                .orElseThrow(() -> new FoodItemException("Food item not found for name: " + name));
        FoodItemDto foodItemDto = foodItemMapper.foodItemToFoodItemDto(foodItem);
        log.info("Leaving retrieveFoodItemByName()");
        return foodItemDto;
    }

    @Override
    public FoodItemDto createFoodItem(CreateFoodItemDto createFoodItemDto) throws FoodItemAlreadyExistException {
        log.info("Entering createFoodItem()");
        Optional<FoodItem> foodItem = foodItemRepository.findByName(createFoodItemDto.getName());
        if (foodItem.isPresent()) {
            throw new FoodItemAlreadyExistException(
                    "Food item already exist with the name: " + createFoodItemDto.getName());
        }

        FoodItemDto foodItemDto = new FoodItemDto();
        foodItemDto.setName(createFoodItemDto.getName());
        foodItemDto.setPrice(createFoodItemDto.getPrice());
        Instant instant = Instant.now();
        foodItemDto.setCreated(instant);
        foodItemDto.setModified(instant);
        log.info("Leaving createFoodItem()");
        return processFoodItem(foodItemMapper.foodItemDtoToFoodItem(foodItemDto));
    }

    @Override
    public FoodItemDto updateFoodItem(Long id, UpdateFoodItemDto updateFoodItemDto) throws FoodItemException {
        log.info("Entering updateFoodItem()");
        Optional<FoodItem> retrievedFoodItem = foodItemRepository.findById(id);
        FoodItemDto foodItemDto = new FoodItemDto();
        if (retrievedFoodItem.isPresent()) {
            foodItemDto.setId(retrievedFoodItem.get().getId());

            foodItemDto.setPrice(retrievedFoodItem.get().getPrice());
            if (Objects.isNull(updateFoodItemDto.getName())) {
                foodItemDto.setName(retrievedFoodItem.get().getName());
            } else {
                foodItemDto.setName(updateFoodItemDto.getName());
            }

            if (Objects.isNull(updateFoodItemDto.getPrice())) {
                foodItemDto.setPrice(retrievedFoodItem.get().getPrice());
            } else {
                foodItemDto.setPrice(updateFoodItemDto.getPrice());
            }

            foodItemDto.setCreated(retrievedFoodItem.get().getCreated());
            foodItemDto.setModified(Instant.now());
        } else {
            throw new FoodItemException("Food item not found for id: " + id);
        }

        log.info("Leaving updateFoodItem()");
        return processFoodItem(foodItemMapper.foodItemDtoToFoodItem(foodItemDto));
    }

    @Override
    public void deleteFoodItem(Long id) throws FoodItemException {
        log.info("Entering deleteFoodItem()");
        FoodItem foodItem = foodItemRepository.findById(id)
                .orElseThrow(() -> new FoodItemException("Food item not found for id: " + id));
        foodItemRepository.deleteById(foodItem.getId());
        log.info("Leaving deleteFoodItem()");
    }

    private FoodItemDto processFoodItem(FoodItem foodItem) {
        log.info("Entering processFoodItem()");
        FoodItem saveFoodItem = foodItemRepository.save(foodItem);
        log.info("Leaving processFoodItem()");
        return foodItemMapper.foodItemToFoodItemDto(saveFoodItem);
    }
}
