package net.breezeware.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import net.breezeware.dto.food.item.CreateFoodItemDto;
import net.breezeware.dto.food.item.FoodItemDto;
import net.breezeware.dto.food.item.UpdateFoodItemDto;
import net.breezeware.exception.CustomExceptionHandler;
import net.breezeware.service.api.FoodItemService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class FoodItemControllerTest {
    public static final long ID = 1L;
    public static final Instant FIXED_INSTANT = Instant.now();
    public static final String FOOD_ITEM_NAME = "Dosa";
    public static final double PRICE = 10.00;
    @Mock
    private FoodItemService foodItemService;

    @InjectMocks
    private FoodItemController foodItemController;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());


    @BeforeEach
    void setUp() {
        log.info("Entering Test Setup");
        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(foodItemController)
                .setControllerAdvice(new CustomExceptionHandler())
                .build();
        log.info("Leaving Test Setup");
    }

    @Test
    @Tag("retrieveFoodItem")
    void givenRetrieveFoodItemsRequest_WhenRetrieveFoodItems_ThenReturnFoodItemsList() throws Exception {
        log.info("Entering givenRetrieveFoodItemsRequest_WhenRetrieveFoodItems_ThenReturnFoodItemsList Test");

        // given
        List<FoodItemDto> foodItems = new ArrayList<>();
        FoodItemDto foodItem = new FoodItemDto();
        foodItem.setId(ID);
        foodItem.setName(FOOD_ITEM_NAME);
        foodItem.setPrice(PRICE);
        foodItem.setCreated(FIXED_INSTANT);
        foodItem.setModified(FIXED_INSTANT);
        foodItems.add(foodItem);

        // when
        when(foodItemService.retrieveFoodItems()).thenReturn(foodItems);

        // then
        mockMvc.perform(get(FoodItemController.BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1)));
        log.info("Leaving givenRetrieveFoodItemsRequest_WhenRetrieveFoodItems_ThenReturnFoodItemsList Test");
    }

    @Test
    @Tag("retrieveFoodItem")
    void givenFoodItemId_WhenRetrieveFoodItem_ThenReturnFoodItemDto() throws Exception {
        log.info("Entering givenFoodItemId_WhenRetrieveFoodItem_ThenReturnFoodItemDto Test");

        // given
        FoodItemDto foodItem = new FoodItemDto();
        foodItem.setId(ID);
        foodItem.setName(FOOD_ITEM_NAME);
        foodItem.setPrice(PRICE);
        foodItem.setCreated(FIXED_INSTANT);
        foodItem.setModified(FIXED_INSTANT);

        // when
        when(foodItemService.retrieveFoodItem(anyLong())).thenReturn(foodItem);

        // then
        mockMvc.perform(get(FoodItemController.BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Dosa")));
        log.info("Leaving givenFoodItemId_WhenRetrieveFoodItem_ThenReturnFoodItemDto Test");
    }

    @Test
    @Tag("createFoodItem")
    void givenFoodItemDto_WhenCreateFoodItem_ThenReturnFoodItemDto() throws Exception {
        log.info("Entering givenFoodItemDto_WhenCreateFoodItem_ThenReturnFoodItemDto Test");

        // given
        CreateFoodItemDto createFoodItemDto = new CreateFoodItemDto();
        createFoodItemDto.setName(FOOD_ITEM_NAME);
        createFoodItemDto.setPrice(PRICE);

        FoodItemDto foodItemDto = new FoodItemDto();
        foodItemDto.setId(ID);
        foodItemDto.setName(FOOD_ITEM_NAME);
        foodItemDto.setPrice(PRICE);
        foodItemDto.setCreated(FIXED_INSTANT);
        foodItemDto.setModified(FIXED_INSTANT);

        // when
        when(foodItemService.createFoodItem(any(CreateFoodItemDto.class))).thenReturn(foodItemDto);

        // then
        mockMvc.perform(post(FoodItemController.BASE_URL)
                        .content(objectMapper.writeValueAsString(createFoodItemDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(FOOD_ITEM_NAME)));
        log.info("Leaving givenFoodItemDto_WhenCreateFoodItem_ThenReturnFoodItemDto Test");
    }

    @Test
    @Tag("updateFoodItem")
    void givenFoodItemDto_WhenUpdateFoodItem_ThenReturnFoodItemDto() throws Exception {
        log.info("Entering givenFoodItemDto_WhenUpdateFoodItem_ThenReturnFoodItemDto Test");

        // given
        FoodItemDto foodItemDto = new FoodItemDto();
        foodItemDto.setId(ID);
        foodItemDto.setName(FOOD_ITEM_NAME);
        foodItemDto.setPrice(PRICE);
        foodItemDto.setCreated(FIXED_INSTANT);
        foodItemDto.setModified(FIXED_INSTANT);

        // when
        when(foodItemService.updateFoodItem(anyLong(), any(UpdateFoodItemDto.class))).thenReturn(foodItemDto);

        // then
        mockMvc.perform(patch(FoodItemController.BASE_URL + "/update/2")
                        .content(objectMapper.writeValueAsString(foodItemDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(FOOD_ITEM_NAME)));
        log.info("Leaving givenFoodItemDto_WhenUpdateFoodItem_ThenReturnFoodItemDto Test");
    }

    @Test
    @Tag("deleteFoodItem")
    void givenDeleteFoodItemRequest_WhenDeleteFoodItem_ThenFoodItemDeleted() throws Exception {
        log.info("Entering givenDeleteFoodItemRequest_WhenDeleteFoodItem_ThenFoodItemDeleted Test");
        // given ID
        // when
        doNothing().when(foodItemService).deleteFoodItem(ID);

        // then
        mockMvc.perform(delete(FoodItemController.BASE_URL + "/" + ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        log.info("Leaving givenDeleteFoodItemRequest_WhenDeleteFoodItem_ThenFoodItemDeleted Test");
    }
}