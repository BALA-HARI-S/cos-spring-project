package net.breezeware.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import net.breezeware.dto.foodItemDto.FoodItemDto;
import net.breezeware.dto.foodOrderDto.CreateFoodOrderDto;
import net.breezeware.dto.foodOrderDto.FoodOrderDto;
import net.breezeware.dto.foodOrderDto.UpdateFoodOrderDto;
import net.breezeware.entity.OrderStatus;
import net.breezeware.exception.CustomExceptionHandler;
import net.breezeware.service.api.FoodOrderService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
class FoodOrderControllerTest {
    public static final long ORDER_ID = 1L;
    public static final long CUSTOMER_ID = 1L;
    public static final double TOTAL_COST = 15.0;
    public static final Instant FIXED_INSTANT = Instant.now();
    @Mock
    private FoodOrderService foodOrderService;

    @InjectMocks
    private FoodOrderController foodOrderController;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());


    @BeforeEach
    void setUp() {
        log.info("Entering Test Setup()");
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(foodOrderController)
                .setControllerAdvice(new CustomExceptionHandler())
                .build();
        log.info("Leaving Test Setup()");
    }

    @Test
    void givenRetrieveFoodOrdersGetRequest_WhenRetrieveFoodOrders_ThenReturnFoodOrdersDtoList() throws Exception {
        log.info("Entering retrieveFoodOrders() controller");
        // given
        FoodItemDto foodItemDto1 = new FoodItemDto(1L, "Dosa", 15.0, FIXED_INSTANT, FIXED_INSTANT);

        Map<FoodItemDto, Integer> foodItemQuantityMap = new HashMap<>();
        foodItemQuantityMap.put(foodItemDto1,1);

        List<FoodOrderDto> foodOrderDtoList = new ArrayList<>();
        FoodOrderDto foodOrderDto1 = new FoodOrderDto();
        foodOrderDto1.setId(ORDER_ID);
        foodOrderDto1.setCustomerId(CUSTOMER_ID);
        foodOrderDto1.setFoodItemsQuantityMap(foodItemQuantityMap);
        foodOrderDto1.setTotalCost(TOTAL_COST);
        foodOrderDto1.setOrderStatus(OrderStatus.ORDER_CART);
        foodOrderDto1.setCreated(FIXED_INSTANT);
        foodOrderDtoList.add(foodOrderDto1);

        // when
        when(foodOrderService.retrieveFoodOrders()).thenReturn(foodOrderDtoList);

        // then
        mockMvc.perform(get(FoodOrderController.BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1)));
        log.info("Leaving retrieveFoodOrders() controller");
    }

    @Test
    void givenRetrieveFoodOrderByIdGetRequest_WhenRetrieveFoodOrder_ThenReturnFoodOrdersDto() throws Exception {
        log.info("Entering retrieveFoodOrder() controller");
        // given
        FoodItemDto foodItemDto1 = new FoodItemDto(1L, "Dosa", 15.0, FIXED_INSTANT, FIXED_INSTANT);

        Map<FoodItemDto, Integer> foodItemQuantityMap = new HashMap<>();
        foodItemQuantityMap.put(foodItemDto1,1);

        FoodOrderDto foodOrderDto = new FoodOrderDto();
        foodOrderDto.setId(ORDER_ID);
        foodOrderDto.setCustomerId(CUSTOMER_ID);
        foodOrderDto.setFoodItemsQuantityMap(foodItemQuantityMap);
        foodOrderDto.setTotalCost(TOTAL_COST);
        foodOrderDto.setOrderStatus(OrderStatus.ORDER_CART);
        foodOrderDto.setCreated(FIXED_INSTANT);

        // when
        when(foodOrderService.retrieveFoodOrder(anyLong())).thenReturn(foodOrderDto);

        // then
        mockMvc.perform(get(FoodOrderController.BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.customerId", is(1)))
                .andExpect(jsonPath("$.totalCost", is(TOTAL_COST)));
        log.info("Leaving retrieveFoodOrder() controller");
    }

    @Test
    void givenCreateFoodOrderDtoPostRequest_WhenCreateFoodOrder_ThenReturnFoodOrdersDto() throws Exception {
        log.info("Entering createFoodOrder() controller");
        // given
        FoodItemDto foodItemDto1 = new FoodItemDto(1L, "Dosa", 15.0, FIXED_INSTANT, FIXED_INSTANT);

        Map<FoodItemDto, Integer> foodItemQuantityMap = new HashMap<>();
        foodItemQuantityMap.put(foodItemDto1,1);

        Map<Long, Integer> orderFoodItemQuantityMap = new HashMap<>();
        orderFoodItemQuantityMap.put(CUSTOMER_ID,1);

        CreateFoodOrderDto createfoodOrderDto = new CreateFoodOrderDto();
        createfoodOrderDto.setCustomerId(CUSTOMER_ID);
        createfoodOrderDto.setFoodItemsQuantityMap(orderFoodItemQuantityMap);

        FoodOrderDto foodOrderDto1 = new FoodOrderDto();
        foodOrderDto1.setId(ORDER_ID);
        foodOrderDto1.setCustomerId(CUSTOMER_ID);
        foodOrderDto1.setFoodItemsQuantityMap(foodItemQuantityMap);
        foodOrderDto1.setTotalCost(TOTAL_COST);
        foodOrderDto1.setOrderStatus(OrderStatus.ORDER_CART);
        foodOrderDto1.setCreated(FIXED_INSTANT);

        // when
        when(foodOrderService.createFoodOrder(any(CreateFoodOrderDto.class))).thenReturn(foodOrderDto1);

        // then
        mockMvc.perform(post(FoodOrderController.BASE_URL)
                        .content(objectMapper.writeValueAsString(createfoodOrderDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.customerId", is(1)))
                .andExpect(jsonPath("$.totalCost", is(TOTAL_COST)));
        log.info("Leaving createFoodOrder() controller");
    }

    @Test
    void givenUpdateFoodOrderDtoPatchRequest_WhenUpdateFoodOrder_ThenReturnFoodOrdersDto() throws Exception {
        log.info("Entering updateFoodOrder() controller");
        // given
        FoodItemDto foodItemDto1 = new FoodItemDto(1L, "Dosa", 15.0, FIXED_INSTANT, FIXED_INSTANT);

        Map<FoodItemDto, Integer> foodItemQuantityMap = new HashMap<>();
        foodItemQuantityMap.put(foodItemDto1,1);

        UpdateFoodOrderDto updateFoodOrderDto = new UpdateFoodOrderDto();
        updateFoodOrderDto.setTotalCost(TOTAL_COST);

        FoodOrderDto foodOrderDto1 = new FoodOrderDto();
        foodOrderDto1.setId(ORDER_ID);
        foodOrderDto1.setCustomerId(CUSTOMER_ID);
        foodOrderDto1.setFoodItemsQuantityMap(foodItemQuantityMap);
        foodOrderDto1.setTotalCost(TOTAL_COST);
        foodOrderDto1.setOrderStatus(OrderStatus.ORDER_CART);
        foodOrderDto1.setCreated(FIXED_INSTANT);

        // when
        when(foodOrderService.updateFoodOrder(anyLong(),any(UpdateFoodOrderDto.class))).thenReturn(foodOrderDto1);

        // then
        mockMvc.perform(patch(FoodOrderController.BASE_URL + "/update/1")
                        .content(objectMapper.writeValueAsString(updateFoodOrderDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.customerId", is(1)))
                .andExpect(jsonPath("$.totalCost", is(TOTAL_COST)));

        log.info("Leaving updateFoodOrder() controller");
    }

    @Test
    void givenFoodOrderIdFoodItemIdQuantityPostRequest_WhenAddFoodItemToOrder_ThenReturnFoodOrdersDto() throws Exception {
        log.info("Entering addFoodItemToOrder() controller");
        // given
        FoodItemDto foodItemDto1 = new FoodItemDto(1L, "Dosa", 15.0, FIXED_INSTANT, FIXED_INSTANT);

        Map<FoodItemDto, Integer> foodItemQuantityMap = new HashMap<>();
        foodItemQuantityMap.put(foodItemDto1,1);

        FoodOrderDto foodOrderDto1 = new FoodOrderDto();
        foodOrderDto1.setId(ORDER_ID);
        foodOrderDto1.setCustomerId(CUSTOMER_ID);
        foodOrderDto1.setFoodItemsQuantityMap(foodItemQuantityMap);
        foodOrderDto1.setTotalCost(TOTAL_COST);
        foodOrderDto1.setOrderStatus(OrderStatus.ORDER_CART);
        foodOrderDto1.setCreated(FIXED_INSTANT);
        // when
        when(foodOrderService.addFoodItemToOrder(anyLong(),anyLong(),anyInt())).thenReturn(foodOrderDto1);
        mockMvc.perform(post(FoodOrderController.BASE_URL + "/add-food-item")
                        .param("orderId", "1")
                        .param("foodItemId", "1")
                        .param("quantity", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.customerId", is(1)))
                .andExpect(jsonPath("$.totalCost", is(TOTAL_COST)));

        // then
        log.info("Leaving addFoodItemToOrder() controller");
    }

    @Test
    void givenFoodOrderIDFoodItemIdDeleteRequest_WhenDeleteOrderFoodItem_ThenOrderItemDeleted() throws Exception {
        log.info("Entering deleteOrderFoodItem() controller");
        // given order id and item id
        // when
        doNothing().when(foodOrderService).deleteOrderFoodItem(ORDER_ID, 1L);

        // then
        mockMvc.perform(delete(FoodOrderController.BASE_URL + "/delete-food-item")
                        .param("orderId", "1")
                        .param("foodItemId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        log.info("Leaving deleteOrderFoodItem() controller");
    }

    @Test
    void givenFoodOrderIDDeleteRequest_WhenDeleteFoodOrder_ThenOrderDeleted() throws Exception {
        log.info("Entering deleteFoodOrder() controller");
        // given order id
        // when
        doNothing().when(foodOrderService).deleteFoodOrder(ORDER_ID);

        // then
        mockMvc.perform(delete(FoodOrderController.BASE_URL + "/delete/" + ORDER_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        log.info("Leaving deleteFoodOrder() controller");
    }
}