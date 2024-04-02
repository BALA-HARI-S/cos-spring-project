package net.breezeware.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.breezeware.dao.*;
import net.breezeware.dto.foodItemDto.FoodItemDto;
import net.breezeware.dto.foodOrderDto.CreateFoodOrderDto;
import net.breezeware.dto.foodOrderDto.FoodOrderDto;
import net.breezeware.dto.foodOrderDto.UpdateFoodOrderDto;
import net.breezeware.entity.FoodItem;
import net.breezeware.entity.FoodOrder;
import net.breezeware.entity.OrderFoodItemMap;
import net.breezeware.entity.OrderStatus;
import net.breezeware.exception.FoodItemException;
import net.breezeware.exception.FoodMenuException;
import net.breezeware.exception.FoodOrderException;
import net.breezeware.mapper.FoodItemMapper;
import net.breezeware.service.api.FoodItemService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@Slf4j
class FoodOrderServiceImplTest {

    public static final long ORDER_ID = 1L;
    public static final long CUSTOMER_ID = 1L;
    public static final double TOTAL_COST = 15.0;
    public static final Instant FIXED_INSTANT = Instant.now();

    @Mock
    private FoodMenuRepository foodMenuRepository;

    @Mock
    private FoodMenuItemMapRepository foodMenuItemMapRepository;

    @Mock
    FoodMenuItemQuantityMapRepository foodMenuItemQuantityMapRepository;

    @Mock
    private FoodOrderRepository foodOrderRepository;

    @Mock
    private OrderFoodItemMapRepository orderFoodItemMapRepository;

    @Mock
    private FoodItemService foodItemService;

    @Mock
    private FoodItemMapper foodItemMapper;

    @InjectMocks
    private FoodOrderServiceImpl foodOrderService;

    @BeforeEach
    void setUp() {
        log.info("Entering Test setUp()");
        MockitoAnnotations.openMocks(this);
        foodOrderService = new FoodOrderServiceImpl(foodMenuRepository,foodMenuItemMapRepository,foodMenuItemQuantityMapRepository,foodOrderRepository,
                orderFoodItemMapRepository,foodItemService,foodItemMapper);
        log.info("Leaving Test setUp()");
    }

    @Test
    void givenRetrieveFoodOrdersRequest_WhenRetrieveFoodOrders_ThenReturnFoodOrders() throws FoodItemException {
        log.info("Entering retrieveFoodOrders() Test");
        // given
        List<FoodOrder> foodOrderList = new ArrayList<>();
        FoodOrder foodOrder1 = new FoodOrder();
        foodOrder1.setId(ORDER_ID);
        foodOrder1.setTotalCost(TOTAL_COST);
        foodOrder1.setOrderStatus(OrderStatus.ORDER_CART);
        foodOrder1.setCreated(FIXED_INSTANT);
        foodOrderList.add(foodOrder1);

        // when
        when(foodOrderRepository.findAll()).thenReturn(foodOrderList);
        List<FoodOrderDto> foodOrderDtoList = foodOrderService.retrieveFoodOrders();

        // then
        Assertions.assertThat(1).isEqualTo(foodOrderDtoList.size());
        Assertions.assertThat(CUSTOMER_ID).isEqualTo(foodOrderDtoList.get(0).getId());
        log.info("Leaving retrieveFoodOrders() Test");
    }

    @Test
    void givenInvalidFoodOrderId_WhenRetrieveFoodOrder_ThenThrowsException() throws FoodOrderException, FoodItemException {
        log.info("Entering retrieveFoodOrder() Test");
        // given food order id

        // when
        when(foodOrderRepository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        Assertions.assertThatThrownBy(() -> foodOrderService.retrieveFoodOrder(ORDER_ID))
                .isInstanceOf(FoodOrderException.class)
                .hasMessage("Order not found for id: " + ORDER_ID);
        log.info("Leaving retrieveFoodOrder() Test");
    }

    @Test
    void givenFoodOrderId_WhenRetrieveFoodOrder_ThenReturnFoodOrder() throws FoodOrderException, FoodItemException {
        log.info("Entering retrieveFoodOrder() Test");
        // given
        FoodItemDto foodItemDto1 = new FoodItemDto(1L, "Dosa", 15.0, FIXED_INSTANT, FIXED_INSTANT);

        FoodOrder foodOrder1 = new FoodOrder();
        foodOrder1.setId(ORDER_ID);
        foodOrder1.setTotalCost(TOTAL_COST);
        foodOrder1.setOrderStatus(OrderStatus.ORDER_CART);
        foodOrder1.setCreated(FIXED_INSTANT);

        // when
        when(foodOrderRepository.findById(anyLong())).thenReturn(Optional.of(foodOrder1));
        FoodOrderDto retrievedFoodOrderDto = foodOrderService.retrieveFoodOrder(ORDER_ID);

        // then
        Assertions.assertThat(CUSTOMER_ID).isEqualTo(retrievedFoodOrderDto.getId());
        Assertions.assertThat(TOTAL_COST).isEqualTo(retrievedFoodOrderDto.getTotalCost());
        log.info("Leaving retrieveFoodOrder() Test");
    }

    @Test
    void givenCreateFoodOrderDto_WhenCreateFoodOrder_ThenReturnFoodOrderDto() throws FoodItemException, FoodOrderException, FoodMenuException {
        log.info("Entering createFoodOrder() Test");
        // given
        FoodItem foodItem1 = new FoodItem(1L, "Dosa", 15.0, FIXED_INSTANT, FIXED_INSTANT);
        FoodItemDto foodItemDto1 = new FoodItemDto(1L, "Dosa", 15.0, FIXED_INSTANT, FIXED_INSTANT);

        Map<Long, Integer> orderFoodItemQuantityMap = new HashMap<>();
        orderFoodItemQuantityMap.put(CUSTOMER_ID,1);

        Map<FoodItemDto, Integer> foodItemQuantityMap = new HashMap<>();
        foodItemQuantityMap.put(foodItemDto1,1);

        CreateFoodOrderDto createfoodOrderDto = new CreateFoodOrderDto();
        createfoodOrderDto.setCustomerId(CUSTOMER_ID);
        createfoodOrderDto.setFoodItemsQuantityMap(orderFoodItemQuantityMap);

        FoodOrder foodOrder1 = new FoodOrder();
        foodOrder1.setId(ORDER_ID);
        foodOrder1.setCustomerId(CUSTOMER_ID);
        foodOrder1.setTotalCost(TOTAL_COST);
        foodOrder1.setOrderStatus(OrderStatus.ORDER_CART);
        foodOrder1.setCreated(FIXED_INSTANT);

        OrderFoodItemMap orderFoodItemMap1 = new OrderFoodItemMap();
        orderFoodItemMap1.setId(1L);
        orderFoodItemMap1.setFoodOrder(foodOrder1);
        orderFoodItemMap1.setFoodItem(foodItem1);
        orderFoodItemMap1.setQuantity(1);

        // when
        when(foodItemService.retrieveFoodItem(anyLong())).thenReturn(foodItemDto1);
        when(orderFoodItemMapRepository.save(any(OrderFoodItemMap.class))).thenReturn(orderFoodItemMap1);
        when(foodOrderRepository.save(any(FoodOrder.class))).thenReturn(foodOrder1);
        FoodOrderDto foodOrderDto = foodOrderService.createFoodOrder(createfoodOrderDto);

        // then
        Assertions.assertThat(ORDER_ID).isEqualTo(foodOrderDto.getId());
        Assertions.assertThat(CUSTOMER_ID).isEqualTo(foodOrderDto.getCustomerId());
        Assertions.assertThat(foodItemQuantityMap).containsExactlyEntriesOf(foodOrderDto.getFoodItemsQuantityMap());
        log.info("Leaving createFoodOrder() Test");
    }

    @Test
    void givenTotalCost_WhenUpdateFoodOrder_ThenReturnFoodOrderDto() throws FoodOrderException, FoodItemException {
        log.info("Entering updateFoodOrder() Test");
        // given
        FoodItemDto foodItemDto1 = new FoodItemDto(1L, "Dosa", 15.0, FIXED_INSTANT, FIXED_INSTANT);

        Map<FoodItemDto, Integer> foodItemQuantityMap = new HashMap<>();
        foodItemQuantityMap.put(foodItemDto1,1);

        UpdateFoodOrderDto updateFoodOrderDto = new UpdateFoodOrderDto();
        updateFoodOrderDto.setTotalCost(100.00);

        FoodOrder foodOrder1 = new FoodOrder();
        foodOrder1.setId(ORDER_ID);
        foodOrder1.setCustomerId(CUSTOMER_ID);
        foodOrder1.setTotalCost(TOTAL_COST);
        foodOrder1.setOrderStatus(OrderStatus.ORDER_CART);
        foodOrder1.setCreated(FIXED_INSTANT);

        FoodOrderDto foodOrderDto1 = new FoodOrderDto();
        foodOrderDto1.setId(ORDER_ID);
        foodOrderDto1.setCustomerId(CUSTOMER_ID);
        foodOrderDto1.setFoodItemsQuantityMap(foodItemQuantityMap);
        foodOrderDto1.setTotalCost(TOTAL_COST);
        foodOrderDto1.setOrderStatus(OrderStatus.ORDER_CART);
        foodOrderDto1.setCreated(FIXED_INSTANT);

        // when
        when(foodOrderRepository.findById(anyLong())).thenReturn(Optional.of(foodOrder1));
        when(foodOrderRepository.save(any(FoodOrder.class))).thenReturn(foodOrder1);
        FoodOrderDto savedFoodOrderDto = foodOrderService.updateFoodOrder(ORDER_ID, updateFoodOrderDto);

        // then
        Assertions.assertThat(ORDER_ID).isEqualTo(savedFoodOrderDto.getId());
        Assertions.assertThat(100.00).isEqualTo(savedFoodOrderDto.getTotalCost());
        log.info("Leaving updateFoodOrder() Test");
    }

    @Test
    void givenInvalidFoodOrderIdFoodItemIdQuantity_WhenAddFoodItemToOrder_ThenThrowsException() throws FoodItemException, FoodOrderException {
        log.info("Entering addFoodItemToOrder() Test");
        // given
        // when
        when(foodOrderRepository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        Assertions.assertThatThrownBy(() -> foodOrderService.addFoodItemToOrder(ORDER_ID, 1L, 1L, 1))
                .isInstanceOf(FoodOrderException.class)
                .hasMessage("Order not found for id: " + ORDER_ID);
        log.info("Leaving addFoodItemToOrder() Test");
    }

    @Test
    void givenFoodOrderIdFoodItemIdQuantity_WhenAddFoodItemToOrder_ThenReturnFoodOrderDto() throws FoodItemException, FoodOrderException, FoodMenuException {
        log.info("Entering addFoodItemToOrder() Test");
        // given
        FoodItem foodItem1 = new FoodItem(1L, "Dosa", 15.0, FIXED_INSTANT, FIXED_INSTANT);
        FoodItemDto foodItemDto1 = new FoodItemDto(1L, "Dosa", 15.0, FIXED_INSTANT, FIXED_INSTANT);

        Map<FoodItemDto, Integer> foodItemQuantityMap = new HashMap<>();
        foodItemQuantityMap.put(foodItemDto1,1);

        FoodOrder foodOrder1 = new FoodOrder();
        foodOrder1.setId(ORDER_ID);
        foodOrder1.setCustomerId(CUSTOMER_ID);
        foodOrder1.setTotalCost(TOTAL_COST);
        foodOrder1.setOrderStatus(OrderStatus.ORDER_CART);
        foodOrder1.setCreated(FIXED_INSTANT);

        FoodOrderDto foodOrderDto1 = new FoodOrderDto();
        foodOrderDto1.setId(ORDER_ID);
        foodOrderDto1.setCustomerId(CUSTOMER_ID);
        foodOrderDto1.setFoodItemsQuantityMap(foodItemQuantityMap);
        foodOrderDto1.setTotalCost(TOTAL_COST);
        foodOrderDto1.setOrderStatus(OrderStatus.ORDER_CART);
        foodOrderDto1.setCreated(FIXED_INSTANT);

        List<OrderFoodItemMap> orderFoodItemMaps = new ArrayList<>();
        OrderFoodItemMap orderFoodItemMap1 = new OrderFoodItemMap();
        orderFoodItemMap1.setId(1L);
        orderFoodItemMap1.setFoodOrder(foodOrder1);
        orderFoodItemMap1.setFoodItem(foodItem1);
        orderFoodItemMap1.setQuantity(1);
        orderFoodItemMaps.add(orderFoodItemMap1);

        // when
        when(foodOrderRepository.findById(anyLong())).thenReturn(Optional.of(foodOrder1));
        when(foodItemService.retrieveFoodItem(anyLong())).thenReturn(foodItemDto1);
        when(orderFoodItemMapRepository.save(any(OrderFoodItemMap.class))).thenReturn(orderFoodItemMap1);
        when(foodOrderRepository.save(any(FoodOrder.class))).thenReturn(foodOrder1);
        when(orderFoodItemMapRepository.findByFoodOrderId(anyLong())).thenReturn(orderFoodItemMaps);

        FoodOrderDto foodOrderDto = foodOrderService.addFoodItemToOrder(ORDER_ID, 1L, 1L, 1);

        // then
        Assertions.assertThat(ORDER_ID).isEqualTo(foodOrderDto.getId());
        Assertions.assertThat(foodItemQuantityMap).containsExactlyEntriesOf(foodOrderDto.getFoodItemsQuantityMap());
        log.info("Leaving addFoodItemToOrder() Test");
    }

    @Test
    void givenInvalidFoodOrderId_WhenDeleteOrderFoodItem_ThenThrowsException() throws FoodOrderException {
        log.info("Entering deleteOrderFoodItem() Test");
        // given
        // when
        when(foodOrderRepository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        Assertions.assertThatThrownBy(() -> foodOrderService.deleteOrderFoodItem(ORDER_ID, 1L))
                .isInstanceOf(FoodOrderException.class)
                .hasMessage("Order not found for id: " + ORDER_ID);
        log.info("Leaving deleteOrderFoodItem() Test");
    }

    @Test
    void givenFoodOrderIdFoodItemId_WhenDeleteOrderFoodItem_ThenOrderFoodItemMapDeleted() throws FoodOrderException {
        log.info("Entering deleteOrderFoodItem() Test");
        // given
        FoodItem foodItem1 = new FoodItem(1L, "Dosa", 15.0, FIXED_INSTANT, FIXED_INSTANT);

        FoodOrder foodOrder1 = new FoodOrder();
        foodOrder1.setId(ORDER_ID);
        foodOrder1.setCustomerId(CUSTOMER_ID);
        foodOrder1.setTotalCost(TOTAL_COST);
        foodOrder1.setOrderStatus(OrderStatus.ORDER_CART);
        foodOrder1.setCreated(FIXED_INSTANT);

        List<OrderFoodItemMap> orderFoodItemMaps = new ArrayList<>();
        OrderFoodItemMap orderFoodItemMap1 = new OrderFoodItemMap();
        orderFoodItemMap1.setId(1L);
        orderFoodItemMap1.setFoodOrder(foodOrder1);
        orderFoodItemMap1.setFoodItem(foodItem1);
        orderFoodItemMap1.setQuantity(1);
        orderFoodItemMaps.add(orderFoodItemMap1);

        // when
        when(foodOrderRepository.findById(anyLong())).thenReturn(Optional.of(foodOrder1));
        when(orderFoodItemMapRepository.findByFoodOrderId(anyLong())).thenReturn(orderFoodItemMaps);
        when(foodOrderRepository.save(any(FoodOrder.class))).thenReturn(foodOrder1);
        doNothing().when(orderFoodItemMapRepository).delete(orderFoodItemMap1);
        foodOrderService.deleteOrderFoodItem(ORDER_ID, 1L);

        // then
        verify(orderFoodItemMapRepository, times(1)).delete(orderFoodItemMap1);
        log.info("Leaving deleteOrderFoodItem() Test");
    }

    @Test
    void givenInvalidFoodOrderId_WhenDeleteFoodOrder_ThenThrowsException() throws FoodOrderException {
        log.info("Entering deleteOrderFoodItem() Test");
        // given
        // when
        when(foodOrderRepository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        Assertions.assertThatThrownBy(() -> foodOrderService.deleteFoodOrder(ORDER_ID))
                .isInstanceOf(FoodOrderException.class)
                .hasMessage("Order not found for id: " + ORDER_ID);
        log.info("Leaving deleteOrderFoodItem() Test");
    }

    @Test
    void givenFoodOrderId_WhenDeleteFoodOrder_ThenFoodOrderDeleted() throws FoodOrderException {
        log.info("Entering deleteOrderFoodItem() Test");
        // given
        FoodOrder foodOrder1 = new FoodOrder();
        foodOrder1.setId(ORDER_ID);
        foodOrder1.setCustomerId(CUSTOMER_ID);
        foodOrder1.setTotalCost(TOTAL_COST);
        foodOrder1.setOrderStatus(OrderStatus.ORDER_CART);
        foodOrder1.setCreated(FIXED_INSTANT);

        // when
        doNothing().when(orderFoodItemMapRepository).deleteByFoodOrderId(anyLong());
        doNothing().when(foodOrderRepository).delete(any(FoodOrder.class));
        when(foodOrderRepository.findById(anyLong())).thenReturn(Optional.of(foodOrder1));
        foodOrderService.deleteFoodOrder(ORDER_ID);

        // then
        verify(orderFoodItemMapRepository, times(1)).deleteByFoodOrderId(ORDER_ID);
        verify(foodOrderRepository, times(1)).delete(foodOrder1);
        log.info("Leaving deleteOrderFoodItem() Test");
    }
}