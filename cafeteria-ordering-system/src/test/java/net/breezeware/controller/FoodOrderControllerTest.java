package net.breezeware.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import net.breezeware.dto.food.item.FoodItemDto;
import net.breezeware.dto.food.order.CreateFoodOrderDto;
import net.breezeware.dto.food.order.FoodOrderDto;
import net.breezeware.dto.food.order.UpdateFoodOrderDto;
import net.breezeware.entity.OrderStatus;
import net.breezeware.exception.CustomExceptionHandler;
import net.breezeware.service.api.FoodOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
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
    @Tag("retrieveFoodOrder")
    void givenRetrieveFoodOrdersGetRequest_WhenRetrieveFoodOrders_ThenReturnFoodOrdersDtoList() throws Exception {
        log.info("Entering givenRetrieveFoodOrdersGetRequest_WhenRetrieveFoodOrders_ThenReturnFoodOrdersDtoList() controller");
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
        log.info("Leaving givenRetrieveFoodOrdersGetRequest_WhenRetrieveFoodOrders_ThenReturnFoodOrdersDtoList() controller");
    }

    @Test
    @Tag("retrieveFoodOrder")
    void givenRetrieveFoodOrderByIdGetRequest_WhenRetrieveFoodOrder_ThenReturnFoodOrdersDto() throws Exception {
        log.info("Entering givenRetrieveFoodOrderByIdGetRequest_WhenRetrieveFoodOrder_ThenReturnFoodOrdersDto() controller");
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
        log.info("Leaving givenRetrieveFoodOrderByIdGetRequest_WhenRetrieveFoodOrder_ThenReturnFoodOrdersDto() controller");
    }

    @Test
    @Tag("createFoodOrder")
    void givenCreateFoodOrderDtoPostRequest_WhenCreateFoodOrder_ThenReturnFoodOrdersDto() throws Exception {
        log.info("Entering givenCreateFoodOrderDtoPostRequest_WhenCreateFoodOrder_ThenReturnFoodOrdersDto() controller");
        // given
        FoodItemDto foodItemDto1 = new FoodItemDto(1L, "Dosa", 15.0, FIXED_INSTANT, FIXED_INSTANT);

        Map<FoodItemDto, Integer> foodItemQuantityMap = new HashMap<>();
        foodItemQuantityMap.put(foodItemDto1,1);

        Map<Long, Integer> orderFoodItemQuantityMap = new HashMap<>();
        orderFoodItemQuantityMap.put(CUSTOMER_ID,1);

        CreateFoodOrderDto createfoodOrderDto = new CreateFoodOrderDto();
        createfoodOrderDto.setCustomerId(CUSTOMER_ID);
        createfoodOrderDto.setMenuId(1L);
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
        log.info("Leaving givenCreateFoodOrderDtoPostRequest_WhenCreateFoodOrder_ThenReturnFoodOrdersDto() controller");
    }

    @Test
    @Tag("updateFoodOrder")
    void givenUpdateFoodOrderDtoPatchRequest_WhenUpdateFoodOrder_ThenReturnFoodOrdersDto() throws Exception {
        log.info("Entering givenUpdateFoodOrderDtoPatchRequest_WhenUpdateFoodOrder_ThenReturnFoodOrdersDto() controller");
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

        log.info("Leaving givenUpdateFoodOrderDtoPatchRequest_WhenUpdateFoodOrder_ThenReturnFoodOrdersDto() controller");
    }

    @Test
    @Tag("updateFoodOrder")
    void givenFoodOrderIdFoodItemIdQuantityPostRequest_WhenAddFoodItemToOrder_ThenReturnFoodOrdersDto() throws Exception {
        log.info("Entering givenFoodOrderIdFoodItemIdQuantityPostRequest_WhenAddFoodItemToOrder_ThenReturnFoodOrdersDto() controller");
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
        when(foodOrderService.addFoodItemToOrder(anyLong(),anyLong(),anyLong(),anyInt())).thenReturn(foodOrderDto1);
        mockMvc.perform(post(FoodOrderController.BASE_URL + "/add-food-item")
                        .param("orderId", "1")
                        .param("menuId", "1")
                        .param("foodItemId", "1")
                        .param("quantity", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.customerId", is(1)))
                .andExpect(jsonPath("$.totalCost", is(TOTAL_COST)));

        // then
        log.info("Leaving givenFoodOrderIdFoodItemIdQuantityPostRequest_WhenAddFoodItemToOrder_ThenReturnFoodOrdersDto() controller");
    }

    @Test
    @Tag("deleteFoodOrder")
    void givenFoodOrderIDFoodItemIdDeleteRequest_WhenDeleteOrderFoodItem_ThenOrderItemDeleted() throws Exception {
        log.info("Entering givenFoodOrderIDFoodItemIdDeleteRequest_WhenDeleteOrderFoodItem_ThenOrderItemDeleted() controller");
        // given order id and item id
        // when
        doNothing().when(foodOrderService).deleteOrderFoodItem(ORDER_ID, 1L);

        // then
        mockMvc.perform(delete(FoodOrderController.BASE_URL + "/delete-food-item")
                        .param("orderId", "1")
                        .param("foodItemId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        log.info("Leaving givenFoodOrderIDFoodItemIdDeleteRequest_WhenDeleteOrderFoodItem_ThenOrderItemDeleted() controller");
    }

    @Test
    @Tag("deleteFoodOrder")
    void givenFoodOrderIDDeleteRequest_WhenDeleteFoodOrder_ThenOrderDeleted() throws Exception {
        log.info("Entering givenFoodOrderIDDeleteRequest_WhenDeleteFoodOrder_ThenOrderDeleted() controller");
        // given order id
        // when
        doNothing().when(foodOrderService).deleteFoodOrder(ORDER_ID);

        // then
        mockMvc.perform(delete(FoodOrderController.BASE_URL + "/delete/" + ORDER_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        log.info("Leaving givenFoodOrderIDDeleteRequest_WhenDeleteFoodOrder_ThenOrderDeleted() controller");
    }
}