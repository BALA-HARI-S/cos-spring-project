package net.breezeware.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.breezeware.dto.FoodMenuDto;
import net.breezeware.exception.FoodMenuException;
import net.breezeware.service.api.FoodMenuService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(FoodMenuController.BASE_URL)
@Tag(name = "Food Menu Management")
@AllArgsConstructor
@Slf4j
public class FoodMenuController {
    static final String BASE_URL = "/food-menu";

    private FoodMenuService foodMenuService;

    @GetMapping
    public List<FoodMenuDto> retrieveFoodMenus() throws FoodMenuException {
        log.info("Entering retrieveFoodMenus() controller");
        List<FoodMenuDto> foodMenuDtoList = foodMenuService.retrieveFoodMenus();
        log.info("Leaving retrieveFoodMenus() controller");
        return foodMenuDtoList;
    }

    @GetMapping("/{id}")
    public FoodMenuDto retrieveFoodMenu(@PathVariable Long id) throws FoodMenuException {
        log.info("Entering retrieveFoodMenu() by id controller");
        FoodMenuDto foodMenuDto = foodMenuService.retrieveFoodMenu(id);
        log.info("Leaving retrieveFoodMenu() by id controller");
        return foodMenuDto;
    }

    @GetMapping("/by-name/{name}")
    public FoodMenuDto retrieveFoodMenu(@PathVariable String name) throws FoodMenuException {
        log.info("Entering retrieveFoodMenu() by name controller");
        FoodMenuDto foodMenuDto = foodMenuService.retrieveFoodMenu(name);
        log.info("Leaving retrieveFoodMenu() by name controller");
        return foodMenuDto;
    }

    @PostMapping
    public FoodMenuDto createFoodMenu(@Valid @RequestBody FoodMenuDto foodMenuDto) throws FoodMenuException {
        log.info("Entering createFoodMenu() controller");
        FoodMenuDto createdFoodMenuDto = foodMenuService.createFoodMenu(foodMenuDto);
        log.info("Leaving createFoodMenu() controller");
        return createdFoodMenuDto;
    }

    @PatchMapping("/{id}")
    public FoodMenuDto updateFoodMenu(@PathVariable Long id,@Valid @RequestBody FoodMenuDto foodMenuDto) throws FoodMenuException {
        log.info("Entering updateFoodMenu() controller");
        FoodMenuDto updatedFoodMenuDto = foodMenuService.updateFoodMenu(id,foodMenuDto);
        log.info("Leaving updateFoodMenu() controller");
        return updatedFoodMenuDto;
    }

    @DeleteMapping("/{id}")
    public void deleteFoodMenu(@PathVariable Long id) throws FoodMenuException {
        log.info("Entering updateFoodMenu() controller");
        foodMenuService.deleteFoodMenu(id);
        log.info("Leaving updateFoodMenu() controller");
    }
}
