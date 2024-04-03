package net.breezeware.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.breezeware.dao.FoodMenuItemMapRepository;
import net.breezeware.dao.FoodMenuItemQuantityMapRepository;
import net.breezeware.dao.FoodMenuRepository;
import net.breezeware.dto.foodMenuDto.CreateFoodMenuDto;
import net.breezeware.dto.foodMenuDto.FoodMenuDto;
import net.breezeware.dto.foodMenuDto.FoodMenuItemsDto;
import net.breezeware.dto.foodMenuDto.UpdateFoodMenuDto;
import net.breezeware.entity.Availability;
import net.breezeware.entity.FoodMenu;
import net.breezeware.exception.FoodItemException;
import net.breezeware.exception.FoodMenuException;
import net.breezeware.mapper.FoodMenuMapper;
import net.breezeware.service.api.FoodItemService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    private FoodMenuMapper foodMenuMapper;
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
        log.info("Leaving Test setUp()");
    }

    @Test
    @Tag("retrieveFoodMenu")
    void givenRetrieveFoodMenuRequest_WhenRetrieveFoodMenus_ThenThrowsException() throws FoodMenuException {
        log.info("Entering givenRetrieveFoodMenuRequest_WhenRetrieveFoodMenus_ThenThrowsException() Test");

        // given
        List<FoodMenu> foodMenuList = new ArrayList<>();

        // when
        when(foodMenuRepository.findAll()).thenReturn(foodMenuList);

        // then
        Assertions.assertThatThrownBy(() -> foodMenuService.retrieveFoodMenus())
                .isInstanceOf(FoodMenuException.class)
                .hasMessage("No food menus found");
        log.info("Leaving givenRetrieveFoodMenuRequest_WhenRetrieveFoodMenus_ThenThrowsException() Test");
    }

    @Test
    @Tag("retrieveFoodMenu")
    void givenRetrieveFoodMenuRequest_WhenRetrieveFoodMenus_ThenReturnFoodMenuDtoList() throws FoodMenuException {
        log.info("Entering givenRetrieveFoodMenuRequest_WhenRetrieveFoodMenus_ThenReturnFoodMenuDtoList() Test");

        // given
        List<FoodMenu> foodMenuList = new ArrayList<>();
        FoodMenu foodMenu = new FoodMenu(ID,MENU_NAME,FIXED_INSTANT,FIXED_INSTANT, AVAILABILITY);
        foodMenuList.add(foodMenu);
        FoodMenuDto foodMenuDto = new FoodMenuDto(ID,MENU_NAME,FIXED_INSTANT,FIXED_INSTANT, AVAILABILITY);

        // when
        when(foodMenuMapper.foodMenuToFoodMenuDto(any(FoodMenu.class))).thenReturn(foodMenuDto);
        when(foodMenuRepository.findAll()).thenReturn(foodMenuList);
        List<FoodMenuDto> retrievedFoodMenus = foodMenuService.retrieveFoodMenus();

        // then
        Assertions.assertThat(retrievedFoodMenus.size()).isEqualTo(1);
        log.info("Leaving givenRetrieveFoodMenuRequest_WhenRetrieveFoodMenus_ThenReturnFoodMenuDtoList() Test");
    }

    @Test
    @Tag("retrieveFoodMenu")
    void givenInvalidFoodMenuId_WhenRetrieveFoodMenu_ThenThrowsException() throws FoodMenuException {
        log.info("Entering givenInvalidFoodMenuId_WhenRetrieveFoodMenu_ThenThrowsException() by id Test");
        // given ID
        // when
        when(foodMenuRepository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        Assertions.assertThatThrownBy(() -> foodMenuService.retrieveFoodMenu(ID))
                .isInstanceOf(FoodMenuException.class)
                .hasMessage("Food menu not found for id: " + ID);
        log.info("Leaving givenInvalidFoodMenuId_WhenRetrieveFoodMenu_ThenThrowsException() by id Test");
    }

    @Test
    @Tag("retrieveFoodMenu")
    void givenFoodMenuId_WhenRetrieveFoodMenu_ThenReturnFoodMenuDto() throws FoodMenuException, FoodItemException {
        log.info("Entering givenFoodMenuId_WhenRetrieveFoodMenu_ThenReturnFoodMenuDto() by id Test");
        // given
        FoodMenu foodMenu = new FoodMenu(ID,MENU_NAME,FIXED_INSTANT,FIXED_INSTANT, AVAILABILITY);

        // when
        when(foodMenuRepository.findById(anyLong())).thenReturn(Optional.of(foodMenu));
        FoodMenuItemsDto retrievedFoodMenu = foodMenuService.retrieveFoodMenu(ID);

        // then
        Assertions.assertThat(retrievedFoodMenu.getName()).isEqualTo(MENU_NAME);
        log.info("Leaving givenFoodMenuId_WhenRetrieveFoodMenu_ThenReturnFoodMenuDto() by id Test");
    }

    @Test
    @Tag("createFoodMenu")
    void givenFoodMenuDto_WhenCreateFoodMenu_ThenReturnFoodMenuDto() {
        log.info("Entering givenFoodMenuDto_WhenCreateFoodMenu_ThenReturnFoodMenuDto() Test");
        // given
        CreateFoodMenuDto createFoodMenuDto = new CreateFoodMenuDto(MENU_NAME,AVAILABILITY);
        FoodMenu foodMenu = new FoodMenu(ID,MENU_NAME,FIXED_INSTANT,FIXED_INSTANT, AVAILABILITY);
        FoodMenuDto foodMenuDto = new FoodMenuDto(ID,MENU_NAME,FIXED_INSTANT,FIXED_INSTANT, AVAILABILITY);

        // when
        when(foodMenuMapper.foodMenuToFoodMenuDto(any(FoodMenu.class))).thenReturn(foodMenuDto);
        when(foodMenuMapper.foodMenuDtoToFoodMenu(any(FoodMenuDto.class))).thenReturn(foodMenu);
        when(foodMenuRepository.save(any(FoodMenu.class))).thenReturn(foodMenu);
        FoodMenuDto savedFoodMenu = foodMenuService.createFoodMenu(createFoodMenuDto);

        // then
        Assertions.assertThat(savedFoodMenu.getId()).isEqualTo(ID);
        Assertions.assertThat(savedFoodMenu.getName()).isEqualTo(MENU_NAME);
        log.info("Leaving givenFoodMenuDto_WhenCreateFoodMenu_ThenReturnFoodMenuDto() Test");
    }

    @Test
    @Tag("updateFoodMenu")
    void givenInvalidFoodMenuId_WhenUpdateFoodMenu_ThenReturnFoodMenuDto() throws FoodMenuException {
        log.info("Entering givenInvalidFoodMenuId_WhenUpdateFoodMenu_ThenReturnFoodMenuDto() Exception Test");
        // given
        UpdateFoodMenuDto foodMenuDto = new UpdateFoodMenuDto();
        foodMenuDto.setName("Special");

        // when
        when(foodMenuRepository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        Assertions.assertThatThrownBy(() -> foodMenuService.updateFoodMenu(ID,foodMenuDto))
                .isInstanceOf(FoodMenuException.class)
                .hasMessage("Food menu not found for id: " + ID);
        log.info("Leaving givenInvalidFoodMenuId_WhenUpdateFoodMenu_ThenReturnFoodMenuDto() Exception Test");
    }

    @Test
    @Tag("updateFoodMenu")
    void givenFoodMenuDtoWithNameAndId_WhenUpdateFoodMenu_ThenReturnFoodMenuDto() throws FoodMenuException {
        log.info("Entering givenFoodMenuDtoWithNameAndId_WhenUpdateFoodMenu_ThenReturnFoodMenuDto() name Test");
        // given
        UpdateFoodMenuDto updateFoodMenuDto = new UpdateFoodMenuDto();
        updateFoodMenuDto.setName("Special");
        FoodMenu existingFoodMenu = new FoodMenu(ID,MENU_NAME,FIXED_INSTANT,FIXED_INSTANT, AVAILABILITY);
        FoodMenu savedFoodMenu = new FoodMenu(ID,"special",FIXED_INSTANT,FIXED_INSTANT, AVAILABILITY);
        FoodMenuDto foodMenuDto = new FoodMenuDto(ID,"special",FIXED_INSTANT,FIXED_INSTANT, AVAILABILITY);

        // when
        when(foodMenuMapper.foodMenuToFoodMenuDto(any(FoodMenu.class))).thenReturn(foodMenuDto);
        when(foodMenuMapper.foodMenuDtoToFoodMenu(any(FoodMenuDto.class))).thenReturn(savedFoodMenu);
        when(foodMenuRepository.findById(ID)).thenReturn(Optional.of(existingFoodMenu));
        when(foodMenuRepository.save(any(FoodMenu.class))).thenReturn(savedFoodMenu);
        FoodMenuDto savedFoodMenuDto = foodMenuService.updateFoodMenu(ID,updateFoodMenuDto);

        // then
        Assertions.assertThat(savedFoodMenuDto.getId()).isEqualTo(ID);
        Assertions.assertThat(savedFoodMenuDto.getName()).isEqualTo("special");
        Assertions.assertThat(savedFoodMenuDto.getMenuAvailability()).isEqualTo(AVAILABILITY);
        log.info("Leaving givenFoodMenuDtoWithNameAndId_WhenUpdateFoodMenu_ThenReturnFoodMenuDto() name Test");
    }

    @Test
    @Tag("updateFoodMenu")
    void givenFoodMenuDtoWithAvailabilityAndId_WhenUpdateFoodMenu_ThenReturnFoodMenuDto() throws FoodMenuException {
        log.info("Entering givenFoodMenuDtoWithAvailabilityAndId_WhenUpdateFoodMenu_ThenReturnFoodMenuDto() availability Test");
        // given
        UpdateFoodMenuDto updateFoodMenuDto = new UpdateFoodMenuDto();
        Set<Availability> newAvailability = new HashSet<>(Arrays.asList(Availability.MONDAY,Availability.TUESDAY));
        updateFoodMenuDto.setMenuAvailability(newAvailability);

        FoodMenu existingFoodMenu = new FoodMenu(ID,MENU_NAME,FIXED_INSTANT,FIXED_INSTANT, AVAILABILITY);
        FoodMenu savedFoodMenu = new FoodMenu(ID,MENU_NAME,FIXED_INSTANT,FIXED_INSTANT, newAvailability);
        FoodMenuDto foodMenuDto = new FoodMenuDto(ID,MENU_NAME,FIXED_INSTANT,FIXED_INSTANT, newAvailability);

        // when
        when(foodMenuMapper.foodMenuToFoodMenuDto(any(FoodMenu.class))).thenReturn(foodMenuDto);
        when(foodMenuMapper.foodMenuDtoToFoodMenu(any(FoodMenuDto.class))).thenReturn(savedFoodMenu);

        when(foodMenuRepository.findById(ID)).thenReturn(Optional.of(existingFoodMenu));
        when(foodMenuRepository.save(any(FoodMenu.class))).thenReturn(savedFoodMenu);
        FoodMenuDto savedFoodMenuDto = foodMenuService.updateFoodMenu(ID,updateFoodMenuDto);

        // then
        Assertions.assertThat(savedFoodMenuDto.getId()).isEqualTo(ID);
        Assertions.assertThat(savedFoodMenuDto.getName()).isEqualTo(MENU_NAME);
        Assertions.assertThat(savedFoodMenuDto.getMenuAvailability()).isEqualTo(newAvailability);
        log.info("Leaving givenFoodMenuDtoWithAvailabilityAndId_WhenUpdateFoodMenu_ThenReturnFoodMenuDto() availability Test");
    }

    @Test
    @Tag("deleteFoodMenu")
    void givenInvalidFoodMenuId_WhenDeleteFoodMenu_ThenThrowsException() throws FoodMenuException {
        log.info("Entering givenInvalidFoodMenuId_WhenDeleteFoodMenu_ThenThrowsException() Test");
        // when
        when(foodMenuRepository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        Assertions.assertThatThrownBy(() -> foodMenuService.deleteFoodMenu(ID))
                .isInstanceOf(FoodMenuException.class)
                .hasMessage("Food menu not found for id: " + ID);


        log.info("Leaving givenInvalidFoodMenuId_WhenDeleteFoodMenu_ThenThrowsException() Test");
    }

    @Test
    @Tag("deleteFoodMenu")
    void givenFoodMenuId_WhenDeleteFoodMenu_ThenFoodMenuDeleted() throws FoodMenuException {
        log.info("Entering givenFoodMenuId_WhenDeleteFoodMenu_ThenFoodMenuDeleted() Test");
        // given
        FoodMenu foodMenu = new FoodMenu(ID,MENU_NAME,FIXED_INSTANT,FIXED_INSTANT, AVAILABILITY);

        // when
        when(foodMenuRepository.findById(anyLong())).thenReturn(Optional.of(foodMenu));
        doNothing().when(foodMenuRepository).deleteById(anyLong());
        foodMenuService.deleteFoodMenu(ID);

        // then
        verify(foodMenuRepository, times(1)).deleteById(ID);
        log.info("Leaving givenFoodMenuId_WhenDeleteFoodMenu_ThenFoodMenuDeleted() Test");
    }
}