package net.breezeware.controller;

import lombok.extern.slf4j.Slf4j;
import net.breezeware.dto.foodItemDto.FoodItemDto;
import net.breezeware.dto.foodOrderDto.FoodOrderDto;
import net.breezeware.entity.OrderStatus;
import net.breezeware.exception.CustomExceptionHandler;
import net.breezeware.service.api.DeliveryService;
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
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
class DeliveryControllerTest {
    public static final long ORDER_ID = 1L;
    public static final long CUSTOMER_ID = 1L;
    public static final double TOTAL_COST = 15.0;
    public static final Instant FIXED_INSTANT = Instant.now();
    @Mock
    private DeliveryService deliveryService;

    @InjectMocks
    private DeliveryController deliveryController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        log.info("Entering Test setUp()");
        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(deliveryController)
                .setControllerAdvice(new CustomExceptionHandler())
                .build();

        log.info("Leaving Test setUp()");
    }

    @Test
    @Tag("updateFoodOrder")
    void givenOrderId_WhenUpdateFoodOrderStatus_ThenReturnUpdatedFoodOrderDto() throws Exception {
        log.info("Entering givenOrderId_WhenUpdateFoodOrderStatus_ThenReturnUpdatedFoodOrderDto() Test");
        // given
        Map<FoodItemDto, Integer> foodItemQuantityMap = new HashMap<>();
        FoodOrderDto updatedfoodOrderDto = new FoodOrderDto(ORDER_ID,CUSTOMER_ID,foodItemQuantityMap,TOTAL_COST, OrderStatus.ORDER_DELIVERED,FIXED_INSTANT);

        // when
        when(deliveryService.updateFoodOrderStatus(ORDER_ID)).thenReturn(updatedfoodOrderDto);

        // then
        mockMvc.perform(patch(DeliveryController.BASE_URL + "/update/order-status/" + ORDER_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderStatus", is("ORDER_DELIVERED")));
        log.info("Leaving givenOrderId_WhenUpdateFoodOrderStatus_ThenReturnUpdatedFoodOrderDto() Test");
    }
}