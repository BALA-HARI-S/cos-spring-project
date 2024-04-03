package net.breezeware.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.breezeware.dao.FoodOrderRepository;
import net.breezeware.dto.foodItemDto.FoodItemDto;
import net.breezeware.dto.foodOrderDto.FoodOrderDto;
import net.breezeware.entity.FoodOrder;
import net.breezeware.entity.OrderStatus;
import net.breezeware.exception.FoodItemException;
import net.breezeware.exception.FoodOrderException;
import net.breezeware.service.api.FoodOrderService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.when;

@Slf4j
class DeliveryServiceImplTest {
    public static final long ORDER_ID = 1L;
    public static final long CUSTOMER_ID = 1L;
    public static final double TOTAL_COST = 15.0;
    public static final Instant FIXED_INSTANT = Instant.now();
    @Mock
    private FoodOrderRepository foodOrderRepository;

    @Mock
    private FoodOrderService foodOrderService;

    @InjectMocks
    private DeliveryServiceImpl deliveryService;

    @BeforeEach
    void setUp() {
        log.info("Entering Test setUp()");
        MockitoAnnotations.openMocks(this);
        log.info("Leaving Test setUp()");
    }

    @Test
    @Tag("updateFoodOrder")
    void givenOrderId_WhenUpdateFoodOrderStatus_ThenThrowsException() throws FoodOrderException, FoodItemException {
        log.info("Entering givenOrderId_WhenUpdateFoodOrderStatus_ThenThrowsException() Test");
        // given
        // when
        when(foodOrderRepository.findById(ORDER_ID)).thenReturn(Optional.empty());

        // then
        Assertions.assertThatThrownBy(() -> deliveryService.updateFoodOrderStatus(ORDER_ID))
                .isInstanceOf(FoodOrderException.class)
                .hasMessage("Food order not found for id: " + ORDER_ID);

        log.info("Leaving givenOrderId_WhenUpdateFoodOrderStatus_ThenThrowsException() Test");
    }

    @Test
    @Tag("updateFoodOrder")
    void givenOrderId_WhenUpdateFoodOrderStatus_ThenReturnUpdatedFoodOrderDto() throws FoodOrderException, FoodItemException {
        log.info("Entering givenOrderId_WhenUpdateFoodOrderStatus_ThenReturnUpdatedFoodOrderDto() Test");
        // given
        FoodOrder foodOrder = new FoodOrder(ORDER_ID,CUSTOMER_ID,TOTAL_COST, OrderStatus.ORDER_CART,FIXED_INSTANT);
        FoodOrder updatedfoodOrder = new FoodOrder(ORDER_ID,CUSTOMER_ID,TOTAL_COST, OrderStatus.ORDER_DELIVERED,FIXED_INSTANT);
        Map<FoodItemDto, Integer> foodItemQuantityMap = new HashMap<>();
        FoodOrderDto updatedfoodOrderDto = new FoodOrderDto(ORDER_ID,CUSTOMER_ID,foodItemQuantityMap,TOTAL_COST,OrderStatus.ORDER_DELIVERED,FIXED_INSTANT);

        // when
        when(foodOrderRepository.findById(ORDER_ID)).thenReturn(Optional.of(foodOrder));
        when(foodOrderRepository.save(foodOrder)).thenReturn(updatedfoodOrder);
        when(foodOrderService.retrieveFoodOrder(ORDER_ID)).thenReturn(updatedfoodOrderDto);

        FoodOrderDto updatedFoodOrder = deliveryService.updateFoodOrderStatus(ORDER_ID);

        // then
        Assertions.assertThat(updatedFoodOrder.getId()).isEqualTo(ORDER_ID);
        Assertions.assertThat(updatedFoodOrder.getOrderStatus()).isEqualTo(OrderStatus.ORDER_DELIVERED);
        log.info("Leaving givenOrderId_WhenUpdateFoodOrderStatus_ThenReturnUpdatedFoodOrderDto() Test");
    }
}