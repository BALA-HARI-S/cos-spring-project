package net.breezeware.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.breezeware.dao.FoodMenuItemMapRepository;
import net.breezeware.dao.FoodMenuItemQuantityMapRepository;
import net.breezeware.dao.FoodMenuRepository;
import net.breezeware.dto.FoodMenuDto;
import net.breezeware.entity.FoodMenu;
import net.breezeware.entity.FoodMenuItemMap;
import net.breezeware.exception.FoodMenuException;
import net.breezeware.mapper.FoodMenuMapper;
import net.breezeware.service.api.FoodMenuService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class FoodMenuServiceImpl implements FoodMenuService {

    private final FoodMenuRepository foodMenuRepository;

    private final FoodMenuMapper foodMenuMapper;

    private final FoodMenuItemMapRepository foodMenuItemMapRepository;

    private final FoodMenuItemQuantityMapRepository foodMenuItemQuantityMapRepository;

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
    public FoodMenuDto retrieveFoodMenu(Long id) throws FoodMenuException {
        log.info("Entering retrieveFoodMenu() service");
        FoodMenu foodMenu = foodMenuRepository.findById(id).orElseThrow( () ->
                new FoodMenuException("Food menu not found for id: " + id));
        FoodMenuDto foodMenuDto = foodMenuMapper.foodMenuToFoodMenuDto(foodMenu);
        log.info("Leaving retrieveFoodMenu() service");
        return foodMenuDto;
    }

    @Override
    public FoodMenuDto retrieveFoodMenu(String name) throws FoodMenuException {
        log.info("Entering retrieveFoodMenu() service");
        FoodMenu foodMenu = foodMenuRepository.findByName(name).orElseThrow( () ->
                new FoodMenuException("Food menu not found for name: " + name));
        FoodMenuDto foodMenuDto = foodMenuMapper.foodMenuToFoodMenuDto(foodMenu);
        log.info("Leaving retrieveFoodMenu() service");
        return foodMenuDto;
    }

    @Override
    public FoodMenuDto createFoodMenu(FoodMenuDto foodMenuDto) {
        log.info("Entering createFoodMenu() service");
        Instant now = Instant.now();
        foodMenuDto.setCreated(now);
        foodMenuDto.setModified(now);
        log.info("Leaving createFoodMenu() service");
        return processFoodMenu(foodMenuMapper.foodMenuDtoToFoodMenu(foodMenuDto));
    }

    @Override
    public FoodMenuDto updateFoodMenu(Long id, FoodMenuDto foodMenuDto) throws FoodMenuException {
        log.info("Entering updateFoodMenu() service");
        FoodMenu retrievedFoodMenu = foodMenuRepository.findById(id).orElseThrow(() ->
                new FoodMenuException("Food menu not found for id: " + id));

        FoodMenu foodMenu = foodMenuMapper.foodMenuDtoToFoodMenu(foodMenuDto);

        foodMenu.setId(retrievedFoodMenu.getId());
        if(Objects.isNull(foodMenuDto.getName())){
            foodMenu.setName(retrievedFoodMenu.getName());
        }
        if(Objects.isNull(foodMenuDto.getMenuAvailability())){
            foodMenu.setMenuAvailability(retrievedFoodMenu.getMenuAvailability());
        }
        foodMenu.setCreated(retrievedFoodMenu.getCreated());
        foodMenu.setModified(Instant.now());

        log.info("Leaving updateFoodMenu() service");
        return processFoodMenu(foodMenu);
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

    private FoodMenuDto processFoodMenu(FoodMenu foodMenu){
        log.info("Entering processFoodMenu() service");
        FoodMenu savedFoodMenu = foodMenuRepository.save(foodMenu);
        log.info("Leaving processFoodMenu() service");
        return foodMenuMapper.foodMenuToFoodMenuDto(savedFoodMenu);
    }
}
