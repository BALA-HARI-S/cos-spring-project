package net.breezeware.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import net.breezeware.dto.foodItemDto.FoodItemDto;
import net.breezeware.dto.foodMenuDto.CreateFoodMenuDto;
import net.breezeware.dto.foodMenuDto.FoodMenuDto;
import net.breezeware.dto.foodMenuDto.FoodMenuItemsDto;
import net.breezeware.dto.foodMenuDto.FoodMenuItemsQuantityDto;
import net.breezeware.dto.foodMenuDto.UpdateFoodMenuDto;
import net.breezeware.entity.Availability;
import net.breezeware.exception.CustomExceptionHandler;
import net.breezeware.service.api.FoodMenuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
class FoodMenuControllerTest {
    private final Long MENU_ID = 1L;
    private final String MENU_NAME = "Standard";
    private final Set<Availability> AVAILABILITY = new HashSet<>(
            Arrays.asList(Availability.TUESDAY,Availability.MONDAY,Availability.WEDNESDAY));
    public static final Instant FIXED_INSTANT = Instant.now();
    @Mock
    private FoodMenuService foodMenuService;

    @InjectMocks
    private FoodMenuController foodMenuController;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @BeforeEach
    void setUp() {
        log.info("Entering Test setUp()");
        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(foodMenuController)
                .setControllerAdvice(CustomExceptionHandler.class)
                .build();
        log.info("Leaving Test setUp()");
    }

    @Test
    void givenRetrieveFoodMenusGetRequest_WhenRetrieveFoodMenus_ThenReturnFoodMenuDtoList() throws Exception {
        log.info("Entering givenRetrieveFoodMenusGetRequest_WhenRetrieveFoodMenus_ThenReturnFoodMenuDtoList() Test");
        // given
        List<FoodMenuDto> foodMenuDtoList = new ArrayList<>();
        FoodMenuDto foodMenuDto = new FoodMenuDto(MENU_ID,MENU_NAME,FIXED_INSTANT,FIXED_INSTANT, AVAILABILITY);
        foodMenuDtoList.add(foodMenuDto);

        // when
        when(foodMenuService.retrieveFoodMenus()).thenReturn(foodMenuDtoList);

        // then
        mockMvc.perform(get(FoodMenuController.BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1)));
        log.info("Leaving givenRetrieveFoodMenusGetRequest_WhenRetrieveFoodMenus_ThenReturnFoodMenuDtoList() Test");
    }

    @Test
    void givenRetrieveFoodMenuOfTheDayGetRequest_WhenRetrieveFoodMenuOfTheDay_ThenReturnFoodMenuDtoList() throws Exception {
        log.info("Entering givenRetrieveFoodMenuOfTheDayGetRequest_WhenRetrieveFoodMenuOfTheDay_ThenReturnFoodMenuDtoList() Test");
        // given
        FoodItemDto foodItemDto1 = new FoodItemDto(1L, "Dosa", 15.0, FIXED_INSTANT, FIXED_INSTANT);

        Map<FoodItemDto, Integer> foodMenuItemsQuantity = new HashMap<>();
        foodMenuItemsQuantity.put(foodItemDto1, 1);

        Set<Availability> availabilities = new HashSet<>();
        availabilities.add(Availability.MONDAY);

        List<FoodMenuItemsQuantityDto> foodMenuItemsQuantityDtos = new ArrayList<>();

        FoodMenuItemsQuantityDto foodMenuItemsQuantityDto = new FoodMenuItemsQuantityDto(
                MENU_NAME,availabilities,foodMenuItemsQuantity);
        foodMenuItemsQuantityDtos.add(foodMenuItemsQuantityDto);

        // when
        when(foodMenuService.retrieveFoodMenuOfTheDay()).thenReturn(foodMenuItemsQuantityDtos);

        // then
        mockMvc.perform(get(FoodMenuController.BASE_URL + "/today-menu")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].name", is(MENU_NAME)));
        log.info("Leaving givenRetrieveFoodMenuOfTheDayGetRequest_WhenRetrieveFoodMenuOfTheDay_ThenReturnFoodMenuDtoList() Test");
    }

    @Test
    void givenRetrieveFoodMenuGetRequest_WhenRetrieveFoodMenu_ThenReturnFoodMenuDto() throws Exception {
        log.info("Entering givenRetrieveFoodMenuGetRequest_WhenRetrieveFoodMenu_ThenReturnFoodMenuDto() Test");
        // given
        FoodItemDto foodItemDto1 = new FoodItemDto(1L, "Dosa", 15.0, FIXED_INSTANT, FIXED_INSTANT);
        List<FoodItemDto> foodItemDtos = new ArrayList<>();
        foodItemDtos.add(foodItemDto1);

        Set<Availability> availabilities = new HashSet<>();
        availabilities.add(Availability.MONDAY);
        FoodMenuItemsDto foodMenuItemsDto = new FoodMenuItemsDto(MENU_NAME,availabilities,foodItemDtos,FIXED_INSTANT,FIXED_INSTANT);

        // when
        when(foodMenuService.retrieveFoodMenu(anyLong())).thenReturn(foodMenuItemsDto);

        // then
        mockMvc.perform(get(FoodMenuController.BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(MENU_NAME)));
        log.info("Leaving givenRetrieveFoodMenuGetRequest_WhenRetrieveFoodMenu_ThenReturnFoodMenuDto() Test");
    }

    @Test
    void givenFoodMenuCreateDtoPostRequest_WhenCreateFoodMenu_ThenReturnFoodMenuDto() throws Exception {
        log.info("Entering givenFoodMenuCreateDtoPostRequest_WhenCreateFoodMenu_ThenReturnFoodMenuDto() Test");
        // given
        Set<Availability> availabilities = new HashSet<>();
        availabilities.add(Availability.MONDAY);
        CreateFoodMenuDto createFoodMenuDto = new CreateFoodMenuDto(MENU_NAME, availabilities);
        FoodMenuDto foodMenuDto = new FoodMenuDto(MENU_ID,MENU_NAME,FIXED_INSTANT,FIXED_INSTANT, AVAILABILITY);

        // when
        when(foodMenuService.createFoodMenu(any(CreateFoodMenuDto.class))).thenReturn(foodMenuDto);

        // then
        mockMvc.perform(post(FoodMenuController.BASE_URL)
                        .content(objectMapper.writeValueAsString(createFoodMenuDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(MENU_NAME)));
        log.info("Leaving givenFoodMenuCreateDtoPostRequest_WhenCreateFoodMenu_ThenReturnFoodMenuDto() Test");
    }

    @Test
    void givenFoodMenuUpdateDtoPatchRequest_WhenUpdateFoodMenu_ThenReturnFoodMenuDto() throws Exception {
        log.info("Entering givenFoodMenuUpdateDtoPatchRequest_WhenUpdateFoodMenu_ThenReturnFoodMenuDto() Test");
        // given
        Set<Availability> availabilities = new HashSet<>();
        availabilities.add(Availability.MONDAY);
        UpdateFoodMenuDto updateFoodMenuDto = new UpdateFoodMenuDto(MENU_NAME, availabilities);

        FoodMenuDto foodMenuDto = new FoodMenuDto(MENU_ID,MENU_NAME,FIXED_INSTANT,FIXED_INSTANT, AVAILABILITY);

        // when
        when(foodMenuService.updateFoodMenu(anyLong(),any(UpdateFoodMenuDto.class))).thenReturn(foodMenuDto);

        // then
        mockMvc.perform(patch(FoodMenuController.BASE_URL + "/update-menu/1")
                        .content(objectMapper.writeValueAsString(foodMenuDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(MENU_NAME)));
        log.info("Leaving givenFoodMenuUpdateDtoPatchRequest_WhenUpdateFoodMenu_ThenReturnFoodMenuDto() Test");
    }

    @Test
    void givenFoodMenuIdFoodItemIdPatchRequest_WhenUpdateFoodMenuItem_ThenReturnFoodMenuItemsDto() throws Exception {
        log.info("Entering givenFoodMenuIdFoodItemIdPatchRequest_WhenUpdateFoodMenuItem_ThenReturnFoodMenuItemsDto() Test");
        // given
        FoodItemDto foodItemDto1 = new FoodItemDto(1L, "Dosa", 15.0, FIXED_INSTANT, FIXED_INSTANT);
        List<FoodItemDto> foodItemDtos = new ArrayList<>();
        foodItemDtos.add(foodItemDto1);

        Set<Availability> availabilities = new HashSet<>();
        availabilities.add(Availability.MONDAY);
        FoodMenuItemsDto foodMenuItemsDto = new FoodMenuItemsDto(MENU_NAME,availabilities,foodItemDtos,FIXED_INSTANT,FIXED_INSTANT);
        // when
        when(foodMenuService.addFoodItemToMenu(anyLong(), anyLong())).thenReturn(foodMenuItemsDto);

        // then
        mockMvc.perform(patch(FoodMenuController.BASE_URL + "/add-food-item")
                        .param("menuId", "1")
                        .param("foodItemId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(MENU_NAME)));
        log.info("Leaving givenFoodMenuIdFoodItemIdPatchRequest_WhenUpdateFoodMenuItem_ThenReturnFoodMenuItemsDto() Test");
    }

    @Test
    void givenFoodMenuIdFoodItemIdQuantityPatchRequest_WhenUpdateFoodMenuItemQuantity_ThenReturnFoodMenuItemQuantityDto() throws Exception {
        log.info("Entering givenFoodMenuIdFoodItemIdQuantityPatchRequest_WhenUpdateFoodMenuItemQuantity_ThenReturnFoodMenuItemQuantityDto() Test");
        // given
        FoodItemDto foodItemDto1 = new FoodItemDto(1L, "Dosa", 15.0, FIXED_INSTANT, FIXED_INSTANT);

        Map<FoodItemDto, Integer> foodMenuItemsQuantity = new HashMap<>();
        foodMenuItemsQuantity.put(foodItemDto1, 1);

        Set<Availability> availabilities = new HashSet<>();
        availabilities.add(Availability.MONDAY);
        FoodMenuItemsQuantityDto updateFoodMenuItemQuantity = new FoodMenuItemsQuantityDto(MENU_NAME, availabilities, foodMenuItemsQuantity);
        // when
        when(foodMenuService.updateFoodMenuItemQuantity(anyLong(), anyLong(), anyInt())).thenReturn(updateFoodMenuItemQuantity);

        // then
        mockMvc.perform(patch(FoodMenuController.BASE_URL + "/update-food-item-quantity")
                        .param("menuId", "1")
                        .param("foodItemId", "1")
                        .param("quantity", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(MENU_NAME)));
        log.info("Leaving givenFoodMenuIdFoodItemIdQuantityPatchRequest_WhenUpdateFoodMenuItemQuantity_ThenReturnFoodMenuItemQuantityDto() Test");
    }

    @Test
    void givenFoodMenuIdDeleteRequest_WhenDeleteFoodMenu_ThenFoodMenuDeleted() throws Exception {
        log.info("Entering givenFoodMenuIdDeleteRequest_WhenDeleteFoodMenu_ThenFoodMenuDeleted() Test");
        // given
        // when
        doNothing().when(foodMenuService).deleteFoodMenu(anyLong());

        // then
        mockMvc.perform(delete(FoodMenuController.BASE_URL + "/delete-menu/1" )
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        log.info("Leaving givenFoodMenuIdDeleteRequest_WhenDeleteFoodMenu_ThenFoodMenuDeleted() Test");
    }

    @Test
    void givenFoodMenuIdFoodItemIdDeleteRequest_WhenDeleteFoodMenuItem_ThenFoodMenuItemDeleted() throws Exception {
        log.info("Entering givenFoodMenuIdFoodItemIdDeleteRequest_WhenDeleteFoodMenuItem_ThenFoodMenuItemDeleted() Test");
        // given
        // when
        doNothing().when(foodMenuService).deleteFoodMenuItem(anyLong(), anyLong());

        // then
        mockMvc.perform(delete(FoodMenuController.BASE_URL + "/delete-food-item" )
                        .param("foodMenuId", "1")
                        .param("foodItemId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        log.info("Leaving givenFoodMenuIdFoodItemIdDeleteRequest_WhenDeleteFoodMenuItem_ThenFoodMenuItemDeleted() Test");
    }
}