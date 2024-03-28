package net.breezeware.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.breezeware.dao.FoodMenuItemMapRepository;
import net.breezeware.dao.FoodMenuItemQuantityMapRepository;
import net.breezeware.dao.FoodMenuRepository;
import net.breezeware.dto.foodMenuDto.FoodMenuCreateDto;
import net.breezeware.dto.foodMenuDto.FoodMenuDto;
import net.breezeware.dto.foodMenuDto.FoodMenuItemsDto;
import net.breezeware.dto.foodMenuDto.FoodMenuUpdateDto;
import net.breezeware.entity.Availability;
import net.breezeware.entity.FoodMenu;
import net.breezeware.exception.FoodItemException;
import net.breezeware.exception.FoodMenuException;
import net.breezeware.mapper.FoodItemMapper;
import net.breezeware.mapper.FoodMenuMapper;
import net.breezeware.service.api.FoodItemService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.*;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@Slf4j
class FoodMenuServiceImplTest {

    private final Long ID = 1L;
    private final String MENU_NAME = "Standard";
    private final Set<Availability> AVAILABILITY = new HashSet<>(
            Arrays.asList(Availability.TUESDAY,Availability.MONDAY,Availability.WEDNESDAY));
    public static final Instant FIXED_INSTANT = Instant.now();

    @Mock
    private FoodMenuRepository foodMenuRepository;
    @Mock
    private FoodMenuItemMapRepository foodMenuItemMapRepository;
    @Mock
    private FoodMenuItemQuantityMapRepository foodMenuItemQuantityMapRepository;
    @Mock
    private FoodItemService foodItemService;

    @InjectMocks
    private FoodMenuServiceImpl foodMenuService;

    @BeforeEach
    void setUp(){
        log.info("Entering Test setUp()");
        MockitoAnnotations.openMocks(this);
        foodMenuService = new FoodMenuServiceImpl(foodMenuRepository,FoodMenuMapper.INSTANCE
                ,foodMenuItemMapRepository,foodMenuItemQuantityMapRepository,foodItemService,FoodItemMapper.INSTANCE);
        log.info("Leaving Test setUp()");
    }

    @Test
    void givenRetrieveFoodMenuRequest_WhenRetrieveFoodMenus_ThenThrowsException() throws FoodMenuException {
        log.info("Entering retrieveFoodMenus() Test");

        // given
        List<FoodMenu> foodMenuList = new ArrayList<>();

        // when
        when(foodMenuRepository.findAll()).thenReturn(foodMenuList);

        // then
        Assertions.assertThatThrownBy(() -> foodMenuService.retrieveFoodMenus())
                .isInstanceOf(FoodMenuException.class)
                .hasMessage("No food menus found");
        log.info("Leaving retrieveFoodMenus() Test");
    }

    @Test
    void givenRetrieveFoodMenuRequest_WhenRetrieveFoodMenus_ThenReturnFoodMenuDtoList() throws FoodMenuException {
        log.info("Entering retrieveFoodMenus() Test");

        // given
        List<FoodMenu> foodMenuList = new ArrayList<>();
        FoodMenu foodMenu = new FoodMenu(ID,MENU_NAME,FIXED_INSTANT,FIXED_INSTANT, AVAILABILITY);
        foodMenuList.add(foodMenu);

        // when
        when(foodMenuRepository.findAll()).thenReturn(foodMenuList);
        List<FoodMenuDto> retrievedFoodMenus = foodMenuService.retrieveFoodMenus();

        // then
        Assertions.assertThat(1).isEqualTo(retrievedFoodMenus.size());
        log.info("Leaving retrieveFoodMenus() Test");
    }

    @Test
    void givenInvalidFoodMenuId_WhenRetrieveFoodMenu_ThenThrowsException() throws FoodMenuException {
        log.info("Entering retrieveFoodMenu() by id Test");
        // given ID
        // when
        when(foodMenuRepository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        Assertions.assertThatThrownBy(() -> foodMenuService.retrieveFoodMenu(ID))
                .isInstanceOf(FoodMenuException.class)
                .hasMessage("Food menu not found for id: " + ID);
        log.info("Leaving retrieveFoodMenu() by id Test");
    }

    @Test
    void givenFoodMenuId_WhenRetrieveFoodMenu_ThenReturnFoodMenuDto() throws FoodMenuException, FoodItemException {
        log.info("Entering retrieveFoodMenu() by id Test");
        // given
        FoodMenu foodMenu = new FoodMenu(ID,MENU_NAME,FIXED_INSTANT,FIXED_INSTANT, AVAILABILITY);

        // when
        when(foodMenuRepository.findById(anyLong())).thenReturn(Optional.of(foodMenu));
        FoodMenuItemsDto retrievedFoodMenu = foodMenuService.retrieveFoodMenu(ID);

        // then
        Assertions.assertThat(MENU_NAME).isEqualTo(retrievedFoodMenu.getName());
        log.info("Leaving retrieveFoodMenu() by id Test");
    }

    @Test
    void givenFoodMenuDto_WhenCreateFoodMenu_ThenReturnFoodMenuDto() {
        log.info("Entering createFoodMenu() Test");
        // given
        FoodMenuCreateDto foodMenuDto = new FoodMenuCreateDto(MENU_NAME,AVAILABILITY);
        FoodMenu foodMenu = new FoodMenu(ID,MENU_NAME,FIXED_INSTANT,FIXED_INSTANT, AVAILABILITY);

        // when
        when(foodMenuRepository.save(any(FoodMenu.class))).thenReturn(foodMenu);
        FoodMenuDto savedFoodMenu = foodMenuService.createFoodMenu(foodMenuDto);

        // then
        Assertions.assertThat(ID).isEqualTo(savedFoodMenu.getId());
        Assertions.assertThat(MENU_NAME).isEqualTo(savedFoodMenu.getName());
        log.info("Leaving createFoodMenu() Test");
    }

    @Test
    void givenInvalidFoodMenuId_WhenUpdateFoodMenu_ThenReturnFoodMenuDto() throws FoodMenuException {
        log.info("Entering updateFoodMenu() Exception Test");
        // given
        FoodMenuUpdateDto foodMenuDto = new FoodMenuUpdateDto();
        foodMenuDto.setName("Special");

        // when
        when(foodMenuRepository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        Assertions.assertThatThrownBy(() -> foodMenuService.updateFoodMenu(ID,foodMenuDto))
                .isInstanceOf(FoodMenuException.class)
                .hasMessage("Food menu not found for id: " + ID);
        log.info("Leaving updateFoodMenu() Exception Test");
    }

    @Test
    void givenFoodMenuDtoWithNameAndId_WhenUpdateFoodMenu_ThenReturnFoodMenuDto() throws FoodMenuException {
        log.info("Entering updateFoodMenu() name Test");
        // given
        FoodMenuUpdateDto foodMenuDto = new FoodMenuUpdateDto();
        foodMenuDto.setName("Special");
        FoodMenu existingFoodMenu = new FoodMenu(ID,MENU_NAME,FIXED_INSTANT,FIXED_INSTANT, AVAILABILITY);
        FoodMenu savedFoodMenu = new FoodMenu(ID,"special",FIXED_INSTANT,FIXED_INSTANT, AVAILABILITY);

        // when
        when(foodMenuRepository.findById(anyLong())).thenReturn(Optional.of(existingFoodMenu));
        when(foodMenuRepository.save(any(FoodMenu.class))).thenReturn(savedFoodMenu);
        FoodMenuDto savedFoodMenuDto = foodMenuService.updateFoodMenu(ID,foodMenuDto);

        // then
        Assertions.assertThat(ID).isEqualTo(savedFoodMenuDto.getId());
        Assertions.assertThat("special").isEqualTo(savedFoodMenuDto.getName());
        Assertions.assertThat(AVAILABILITY).isEqualTo(savedFoodMenuDto.getMenuAvailability());
        log.info("Leaving updateFoodMenu() name Test");
    }

    @Test
    void givenFoodMenuDtoWithAvailabilityAndId_WhenUpdateFoodMenu_ThenReturnFoodMenuDto() throws FoodMenuException {
        log.info("Entering updateFoodMenu() availability Test");
        // given
        FoodMenuUpdateDto foodMenuDto = new FoodMenuUpdateDto();
        Set<Availability> newAvailability = new HashSet<>(Arrays.asList(Availability.MONDAY,Availability.TUESDAY));
        foodMenuDto.setMenuAvailability(newAvailability);
        FoodMenu existingFoodMenu = new FoodMenu(ID,MENU_NAME,FIXED_INSTANT,FIXED_INSTANT, AVAILABILITY);
        FoodMenu savedFoodMenu = new FoodMenu(ID,MENU_NAME,FIXED_INSTANT,FIXED_INSTANT, newAvailability);

        // when
        when(foodMenuRepository.findById(anyLong())).thenReturn(Optional.of(existingFoodMenu));
        when(foodMenuRepository.save(any(FoodMenu.class))).thenReturn(savedFoodMenu);
        FoodMenuDto savedFoodMenuDto = foodMenuService.updateFoodMenu(ID,foodMenuDto);

        // then
        Assertions.assertThat(ID).isEqualTo(savedFoodMenuDto.getId());
        Assertions.assertThat(MENU_NAME).isEqualTo(savedFoodMenuDto.getName());
        Assertions.assertThat(newAvailability).isEqualTo(savedFoodMenuDto.getMenuAvailability());
        log.info("Leaving updateFoodMenu() availability Test");
    }

    @Test
    void givenInvalidFoodMenuId_WhenDeleteFoodMenu_ThenThrowsException() throws FoodMenuException {
        log.info("Entering deleteFoodMenu() Test");
        // when
        when(foodMenuRepository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        Assertions.assertThatThrownBy(() -> foodMenuService.deleteFoodMenu(ID))
                .isInstanceOf(FoodMenuException.class)
                .hasMessage("Food menu not found for id: " + ID);


        log.info("Leaving deleteFoodMenu() Test");
    }

    @Test
    void givenFoodMenuId_WhenDeleteFoodMenu_ThenFoodMenuDeleted() throws FoodMenuException {
        log.info("Entering deleteFoodMenu() Test");
        // given
        FoodMenu foodMenu = new FoodMenu(ID,MENU_NAME,FIXED_INSTANT,FIXED_INSTANT, AVAILABILITY);

        // when
        when(foodMenuRepository.findById(anyLong())).thenReturn(Optional.of(foodMenu));
        doNothing().when(foodMenuRepository).deleteById(anyLong());
        foodMenuService.deleteFoodMenu(ID);

        // then
        verify(foodMenuRepository, times(1)).deleteById(ID);
        log.info("Leaving deleteFoodMenu() Test");
    }
}