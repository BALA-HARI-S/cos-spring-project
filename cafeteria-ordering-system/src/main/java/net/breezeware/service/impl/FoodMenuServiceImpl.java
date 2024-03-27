package net.breezeware.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.breezeware.dao.FoodMenuItemMapRepository;
import net.breezeware.dao.FoodMenuItemQuantityMapRepository;
import net.breezeware.dao.FoodMenuRepository;
import net.breezeware.dto.fooditemdto.FoodItemDto;
import net.breezeware.dto.foodmenudto.*;
import net.breezeware.entity.Availability;
import net.breezeware.entity.FoodMenu;
import net.breezeware.entity.FoodMenuItemMap;
import net.breezeware.entity.FoodMenuItemQuantityMap;
import net.breezeware.exception.FoodItemException;
import net.breezeware.exception.FoodMenuException;
import net.breezeware.mapper.FoodItemMapper;
import net.breezeware.mapper.FoodMenuMapper;
import net.breezeware.service.api.FoodItemService;
import net.breezeware.service.api.FoodMenuService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class FoodMenuServiceImpl implements FoodMenuService {

    private final FoodMenuRepository foodMenuRepository;

    private final FoodMenuMapper foodMenuMapper;

    private final FoodMenuItemMapRepository foodMenuItemMapRepository;

    private final FoodMenuItemQuantityMapRepository foodMenuItemQuantityMapRepository;

    private final FoodItemService foodItemService;

    private final FoodItemMapper foodItemMapper;

    @Override
    public List<FoodMenuDto> retrieveFoodMenus() throws FoodMenuException {
        log.info("Entering retrieveFoodMenus() service");
        List<FoodMenu> foodMenus = foodMenuRepository.findAll();
        if(foodMenus.isEmpty()){
            throw new FoodMenuException("No food menus found");
        }
        List<FoodMenuDto> foodMenuDtoList = foodMenus
                .stream()
                .map(foodMenuMapper::foodMenuToFoodMenuDto)
                .toList();
        log.info("Leaving retrieveFoodMenus() service");
        return foodMenuDtoList;
    }

    @Override
    public FoodMenuItemsDto retrieveFoodMenu(Long id) throws FoodMenuException, FoodItemException {
        log.info("Entering retrieveFoodMenu() service");
        FoodMenu foodMenu = foodMenuRepository.findById(id).orElseThrow( () ->
                new FoodMenuException("Food menu not found for id: " + id));

        FoodMenuItemsDto foodMenuItemsDto = new FoodMenuItemsDto();
        foodMenuItemsDto.setName(foodMenu.getName());
        foodMenuItemsDto.setMenuAvailability(foodMenu.getMenuAvailability());
        foodMenuItemsDto.setFoodMenuItemsDto(retrieveFoodMenuItems(id));
        foodMenuItemsDto.setCreated(foodMenu.getCreated());
        foodMenuItemsDto.setModified(foodMenu.getModified());

        log.info("Leaving retrieveFoodMenu() service");
        return foodMenuItemsDto;
    }

    @Override
    public List<FoodMenuItemsQuantityDto> retrieveFoodMenuOfTheDay() throws FoodMenuException, FoodItemException {
        log.info("Entering retrieveFoodMenuOfTheDay() service");
        LocalDate currentDate = LocalDate.now();
        DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
        String today = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH).toUpperCase();

        List<FoodMenu> retrievedFoodMenus = foodMenuRepository.findByMenuAvailability(Availability.valueOf(today));
        List<FoodMenuItemsQuantityDto> foodMenuItemsQuantityDtoList = new ArrayList<>();
        for (FoodMenu foodMenu: retrievedFoodMenus){
            Map<FoodItemDto, Integer> foodItemsQuantity = new HashMap<>();
            FoodMenuItemsQuantityDto foodMenuItemsQuantityDto = new FoodMenuItemsQuantityDto();
            List<FoodMenuItemMap> foodMenuItemMaps = foodMenuItemMapRepository.findByFoodMenuId(foodMenu.getId());
            foodMenuItemsQuantityDto.setName(foodMenu.getName());
            foodMenuItemsQuantityDto.setMenuAvailability(foodMenu.getMenuAvailability());
            for(FoodMenuItemMap itemMap: foodMenuItemMaps){
                FoodItemDto foodItemDto = foodItemMapper.foodItemToFoodItemDto(itemMap.getFoodItem());
                Integer quantity = foodMenuItemQuantityMapRepository
                        .findByFoodMenuItemMapId(itemMap.getId()).getQuantity();
                foodItemsQuantity.put(foodItemDto, quantity);
            }
            foodMenuItemsQuantityDto.setFoodMenuItemsQuantity(foodItemsQuantity);
            foodMenuItemsQuantityDtoList.add(foodMenuItemsQuantityDto);
        }
        log.info("Leaving retrieveFoodMenuOfTheDay() service");
        return foodMenuItemsQuantityDtoList;
    }

    @Override
    public FoodMenuDto createFoodMenu(FoodMenuCreateDto foodMenuCreateDto) {
        log.info("Entering createFoodMenu() service");
        FoodMenuDto foodMenuDto = new FoodMenuDto();
        foodMenuDto.setName(foodMenuCreateDto.getName());
        foodMenuDto.setMenuAvailability(foodMenuCreateDto.getMenuAvailability());
        Instant now = Instant.now();
        foodMenuDto.setCreated(now);
        foodMenuDto.setModified(now);
        log.info("Leaving createFoodMenu() service");
        return processFoodMenu(foodMenuMapper.foodMenuDtoToFoodMenu(foodMenuDto));
    }

    @Override
    public FoodMenuDto updateFoodMenu(Long id, FoodMenuUpdateDto foodMenuUpdateDto) throws FoodMenuException {
        log.info("Entering updateFoodMenu() service");
        FoodMenu retrievedFoodMenu = foodMenuRepository.findById(id).orElseThrow(() ->
                new FoodMenuException("Food menu not found for id: " + id));

        FoodMenuDto foodMenuDto = new FoodMenuDto();

        foodMenuDto.setId(retrievedFoodMenu.getId());
        if(Objects.isNull(foodMenuUpdateDto.getName())){
            foodMenuDto.setName(retrievedFoodMenu.getName());
        } else {
            foodMenuDto.setName(foodMenuUpdateDto.getName());
        }
        if(Objects.isNull(foodMenuUpdateDto.getMenuAvailability())){
            foodMenuDto.setMenuAvailability(retrievedFoodMenu.getMenuAvailability());
        } else {
            foodMenuDto.setMenuAvailability(foodMenuUpdateDto.getMenuAvailability());
        }
        foodMenuDto.setCreated(retrievedFoodMenu.getCreated());
        foodMenuDto.setModified(Instant.now());

        log.info("Leaving updateFoodMenu() service");
        return processFoodMenu(foodMenuMapper.foodMenuDtoToFoodMenu(foodMenuDto));
    }

    @Override
    @Transactional
    public void deleteFoodMenu(Long id) throws FoodMenuException {
        log.info("Entering deleteFoodMenu() service");
        foodMenuRepository.findById(id).orElseThrow(() ->
                new FoodMenuException("Food menu not found for id: " + id));

        log.info("Getting FoodMenuItemMap records");
        List<FoodMenuItemMap> foodMenuItemMaps = foodMenuItemMapRepository.findByFoodMenuId(id);

        for(FoodMenuItemMap foodMenuItemMap: foodMenuItemMaps){
            log.info("Deleting FoodMenuItemQuantityMap records");
            foodMenuItemQuantityMapRepository.deleteByFoodMenuItemMapId(foodMenuItemMap.getId());
        }
        log.info("Deleting FoodMenuItemMap records");
        foodMenuItemMapRepository.deleteByFoodMenuId(id);
        log.info("Deleting FoodMenu record");
        foodMenuRepository.deleteById(id);
        log.info("Leaving deleteFoodMenu() service");
    }

    @Override
    public FoodMenuItemsDto addFoodItemToMenu(Long menuId, Long foodItemId) throws FoodMenuException, FoodItemException {
        log.info("Entering updateFoodMenuItems() service");
        FoodMenu retrievedFoodMenu = foodMenuRepository.findById(menuId).orElseThrow(() ->
                new FoodMenuException("Food menu not found for id: " + menuId));

        FoodMenuItemMap foodMenuItemMap = new FoodMenuItemMap();
        foodMenuItemMap.setFoodItem(foodItemMapper.foodItemDtoToFoodItem(foodItemService.retrieveFoodItem(foodItemId)));
        foodMenuItemMap.setFoodMenu(retrievedFoodMenu);
        foodMenuItemMapRepository.save(foodMenuItemMap);

        FoodMenuItemQuantityMap foodMenuItemQuantityMap = new FoodMenuItemQuantityMap();
        foodMenuItemQuantityMap.setFoodMenuItemMap(foodMenuItemMap);
        foodMenuItemQuantityMap.setQuantity(0);
        Instant now = Instant.now();
        foodMenuItemQuantityMap.setCreated(now);
        foodMenuItemQuantityMap.setModified(now);
        foodMenuItemQuantityMapRepository.save(foodMenuItemQuantityMap);

        FoodMenuItemsDto foodMenuItemsDto = new FoodMenuItemsDto(
                retrievedFoodMenu.getName(),
                retrievedFoodMenu.getMenuAvailability(),
                retrieveFoodMenuItems(menuId),
                retrievedFoodMenu.getCreated(),
                retrievedFoodMenu.getModified()
        );
        log.info("Leaving updateFoodMenuItems() service");
        return foodMenuItemsDto;
    }

    @Override
    public FoodMenuItemsQuantityDto updateFoodMenuItemQuantity(Long menuId, Long foodItemId, Integer quantity) throws FoodMenuException, FoodItemException {
        log.info("Entering updateFoodMenuItemQuantity() service");
        FoodMenuItemsDto retrievedFoodMenuDto = retrieveFoodMenu(menuId);
        List<FoodMenuItemMap> foodMenuItemMaps = foodMenuItemMapRepository.findByFoodMenuId(menuId);
        FoodMenuItemMap foodMenuItemMap = new FoodMenuItemMap();
        for(FoodMenuItemMap itemMap: foodMenuItemMaps){
            if (Objects.equals(itemMap.getFoodItem().getId(), foodItemId)){
                foodMenuItemMap = itemMap;
            }
        }
        FoodMenuItemQuantityMap foodMenuItemQuantityMap = foodMenuItemQuantityMapRepository
                .findByFoodMenuItemMapId(foodMenuItemMap.getId());
        foodMenuItemQuantityMap.setId(foodMenuItemMap.getId());
        foodMenuItemQuantityMap.setFoodMenuItemMap(foodMenuItemMap);
        foodMenuItemQuantityMap.setQuantity(quantity);
        foodMenuItemQuantityMap.setModified(Instant.now());

        FoodMenuItemQuantityMap savedFoodMenuItemQuantityMap = foodMenuItemQuantityMapRepository
                .save(foodMenuItemQuantityMap);
        Map<FoodItemDto, Integer> foodItemsQuantity = new HashMap<>();
        for(FoodMenuItemMap itemMap: foodMenuItemMaps){
            FoodItemDto foodItemDto = foodItemMapper.foodItemToFoodItemDto(itemMap.getFoodItem());
            Integer itemQuantity = foodMenuItemQuantityMapRepository
                    .findByFoodMenuItemMapId(itemMap.getId()).getQuantity();
            foodItemsQuantity.put(foodItemDto, itemQuantity);
        }

        FoodMenuItemsQuantityDto foodMenuItemsQuantityDto = new FoodMenuItemsQuantityDto();
        foodMenuItemsQuantityDto.setName(retrievedFoodMenuDto.getName());
        foodMenuItemsQuantityDto.setMenuAvailability(retrievedFoodMenuDto.getMenuAvailability());
        foodMenuItemsQuantityDto.setFoodMenuItemsQuantity(foodItemsQuantity);
        log.info("Leaving updateFoodMenuItemQuantity() service");
        return foodMenuItemsQuantityDto;
    }

    @Override
    @Transactional
    public void deleteFoodMenuItem(Long foodMenuId, Long foodItemId) throws FoodMenuException {
        log.info("Entering deleteFoodMenuItem() service");
        FoodMenuItemMap foodMenuItemMap = foodMenuItemMapRepository.findByFoodMenuIdAndFoodItemId(foodMenuId, foodItemId);
        System.out.println(foodMenuItemMap);
        foodMenuItemQuantityMapRepository.deleteByFoodMenuItemMapId(foodMenuItemMap.getId());
        foodMenuItemMapRepository.delete(foodMenuItemMap);
        log.info("Entering deleteFoodMenuItem() service");
    }


    private FoodMenuDto processFoodMenu(FoodMenu foodMenu){
        log.info("Entering processFoodMenu() service");
        FoodMenu savedFoodMenu = foodMenuRepository.save(foodMenu);
        log.info("Leaving processFoodMenu() service");
        return foodMenuMapper.foodMenuToFoodMenuDto(savedFoodMenu);
    }

    private List<FoodItemDto> retrieveFoodMenuItems(Long id){
        log.info("Entering retrieveFoodMenuItems() service");
        List<FoodMenuItemMap> foodMenuItemMaps = foodMenuItemMapRepository.findByFoodMenuId(id);

        List<FoodItemDto> foodMenuItems = new ArrayList<>();
        for(FoodMenuItemMap itemMap: foodMenuItemMaps){
            foodMenuItems.add(foodItemMapper.foodItemToFoodItemDto(itemMap.getFoodItem()));
        }
        log.info("Leaving retrieveFoodMenuItems() service");
        return foodMenuItems;
    }
}