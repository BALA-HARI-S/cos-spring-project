package net.breezeware.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import net.breezeware.dto.FoodItemDto;
import net.breezeware.exception.CustomExceptionHandler;
import net.breezeware.service.api.FoodItemService;
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
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    void givenRetrieveFoodItemsRequest_WhenRetrieveFoodItems_ThenReturnFoodItemsList() throws Exception {
        log.info("Entering RetrieveFoodItems Test");

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
        log.info("Leaving RetrieveFoodItems Test");
    }

    @Test
    void givenFoodItemId_WhenRetrieveFoodItem_ThenReturnFoodItemDto() throws Exception {
        log.info("Entering RetrieveFoodItem Test");

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
        log.info("Leaving RetrieveFoodItem Test");
    }

    @Test
    void givenFoodItemName_WhenRetrieveFoodItemByName_ThenReturnFoodItemDto() throws Exception {
        log.info("Entering RetrieveFoodItemByName Test");

        // given
        FoodItemDto foodItemDto = new FoodItemDto();
        foodItemDto.setId(ID);
        foodItemDto.setName(FOOD_ITEM_NAME);
        foodItemDto.setPrice(PRICE);
        foodItemDto.setCreated(FIXED_INSTANT);
        foodItemDto.setModified(FIXED_INSTANT);

        // when
        when(foodItemService.retrieveFoodItemByName(anyString())).thenReturn(foodItemDto);

        // then
        mockMvc.perform(get(FoodItemController.BASE_URL + "/by-name/Dosa")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Dosa")));
        log.info("Leaving RetrieveFoodItemByName Test");
    }

    @Test
    void givenFoodItemDto_WhenCreateFoodItem_ThenReturnFoodItemDto() throws Exception {
        log.info("Entering CreateFoodItem Test");

        // given
        FoodItemDto foodItemDto = new FoodItemDto();
        foodItemDto.setId(ID);
        foodItemDto.setName(FOOD_ITEM_NAME);
        foodItemDto.setPrice(PRICE);
        foodItemDto.setCreated(FIXED_INSTANT);
        foodItemDto.setModified(FIXED_INSTANT);

        // when
        when(foodItemService.createFoodItem(any(FoodItemDto.class))).thenReturn(foodItemDto);

        // then
        mockMvc.perform(post(FoodItemController.BASE_URL)
                        .content(objectMapper.writeValueAsString(foodItemDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(FOOD_ITEM_NAME)));
        log.info("Leaving CreateFoodItem Test");
    }

    @Test
    void givenFoodItemDto_WhenUpdateFoodItem_ThenReturnFoodItemDto() throws Exception {
        log.info("Entering UpdateFoodItem Test");

        // given
        FoodItemDto foodItemDto = new FoodItemDto();
        foodItemDto.setId(ID);
        foodItemDto.setName(FOOD_ITEM_NAME);
        foodItemDto.setPrice(PRICE);
        foodItemDto.setCreated(FIXED_INSTANT);
        foodItemDto.setModified(FIXED_INSTANT);

        // when
        when(foodItemService.updateFoodItem(anyLong(), any(FoodItemDto.class))).thenReturn(foodItemDto);

        // then
        mockMvc.perform(patch(FoodItemController.BASE_URL + "/2")
                        .content(objectMapper.writeValueAsString(foodItemDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(FOOD_ITEM_NAME)));
        log.info("Leaving UpdateFoodItem Test");
    }

    @Test
    void givenDeleteFoodItemRequest_WhenDeleteFoodItem_ThenFoodItemDeleted() throws Exception {
        log.info("Entering DeleteFoodItem Test");
        // given ID
        // when
        doNothing().when(foodItemService).deleteFoodItem(ID);

        // then
        mockMvc.perform(delete(FoodItemController.BASE_URL + "/" + ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        log.info("Leaving DeleteFoodItem Test");
    }
}