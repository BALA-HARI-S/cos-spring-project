package net.breezeware.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.breezeware.dao.FoodItemRepository;
import net.breezeware.dto.FoodItemDto;
import net.breezeware.entity.FoodItem;
import net.breezeware.exception.FoodItemException;
import net.breezeware.mapper.FoodItemMapper;
import net.breezeware.service.api.FoodItemService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

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
        List<FoodItemDto> listOfFoodItems = foodItems
                .stream()
                .map(foodItemMapper::foodItemToFoodItemDto)
                .toList();
        log.info("Leaving retrieveFoodItems()");
        return listOfFoodItems;
    }

    @Override
    public FoodItemDto retrieveFoodItem(Long id) throws FoodItemException {
        log.info("Entering retrieveFoodItem()");
        FoodItem foodItem = foodItemRepository.findById(id).orElseThrow(() ->
                new FoodItemException("Food item not found for id: " + id));
        FoodItemDto foodItemDto = foodItemMapper.foodItemToFoodItemDto(foodItem);
        log.info("Leaving retrieveFoodItem()");
        return foodItemDto;
    }

    @Override
    public FoodItemDto retrieveFoodItemByName(String name) throws FoodItemException {
        log.info("Entering retrieveFoodItemByName()");
        FoodItem foodItem = foodItemRepository.findByName(name).orElseThrow(() -> new FoodItemException("Food item not found for name: " + name));
        FoodItemDto foodItemDto = foodItemMapper.foodItemToFoodItemDto(foodItem);
        log.info("Leaving retrieveFoodItemByName()");
        return foodItemDto;
    }

    @Override
    public FoodItemDto createFoodItem(FoodItemDto foodItemDto) {
        log.info("Entering createFoodItem()");
        Instant instant = Instant.now();
        foodItemDto.setCreated(instant);
        foodItemDto.setModified(instant);
        log.info("Leaving createFoodItem()");
        return processFoodItem(foodItemMapper.foodItemDtoToFoodItem(foodItemDto));
    }

    @Override
    public FoodItemDto updateFoodItem(Long id, FoodItemDto foodItemDto) throws FoodItemException {
        log.info("Entering updateFoodItem()");
        FoodItem retrievedFoodItem = foodItemRepository.findById(id).orElseThrow(() ->
                new FoodItemException("Food item not found for id: " + id));
        FoodItem foodItem = foodItemMapper.foodItemDtoToFoodItem(foodItemDto);

        foodItem.setId(id);
        if (Objects.isNull(foodItemDto.getName())) {
            foodItem.setName(retrievedFoodItem.getName());
        }
        if (Objects.isNull(foodItemDto.getPrice())) {
            foodItem.setPrice(retrievedFoodItem.getPrice());
        }
        foodItem.setCreated(retrievedFoodItem.getCreated());
        foodItem.setModified(Instant.now());

        log.info("Leaving updateFoodItem()");
        return processFoodItem(foodItem);
    }

    @Override
    public void deleteFoodItem(Long id) throws FoodItemException {
        log.info("Entering deleteFoodItem()");
        FoodItem foodItem = foodItemRepository.findById(id).orElseThrow(() ->
                new FoodItemException("Food item not found for id: " + id));
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
