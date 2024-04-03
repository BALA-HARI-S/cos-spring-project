package net.breezeware.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.breezeware.dao.*;
import net.breezeware.dto.foodItemDto.FoodItemDto;
import net.breezeware.dto.foodOrderDto.CreateFoodOrderDto;
import net.breezeware.dto.foodOrderDto.FoodOrderDto;
import net.breezeware.dto.foodOrderDto.UpdateFoodOrderDto;
import net.breezeware.entity.*;
import net.breezeware.exception.FoodItemException;
import net.breezeware.exception.FoodMenuException;
import net.breezeware.exception.FoodOrderException;
import net.breezeware.mapper.FoodItemMapper;
import net.breezeware.service.api.FoodItemService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
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
        log.info("Leaving Test setUp()");
    }

    @Test
    @Tag("retrieveFoodOrder")
    void givenRetrieveFoodOrdersRequest_WhenRetrieveFoodOrders_ThenReturnFoodOrders() throws FoodItemException {
        log.info("Entering givenRetrieveFoodOrdersRequest_WhenRetrieveFoodOrders_ThenReturnFoodOrders() Test");
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
        log.info("Leaving givenRetrieveFoodOrdersRequest_WhenRetrieveFoodOrders_ThenReturnFoodOrders() Test");
    }

    @Test
    @Tag("retrieveFoodOrder")
    void givenInvalidFoodOrderId_WhenRetrieveFoodOrder_ThenThrowsException() throws FoodOrderException, FoodItemException {
        log.info("Entering givenInvalidFoodOrderId_WhenRetrieveFoodOrder_ThenThrowsException() Test");
        // given food order id

        // when
        when(foodOrderRepository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        Assertions.assertThatThrownBy(() -> foodOrderService.retrieveFoodOrder(ORDER_ID))
                .isInstanceOf(FoodOrderException.class)
                .hasMessage("Order not found for id: " + ORDER_ID);
        log.info("Leaving givenInvalidFoodOrderId_WhenRetrieveFoodOrder_ThenThrowsException() Test");
    }

    @Test
    @Tag("retrieveFoodOrder")
    void givenFoodOrderId_WhenRetrieveFoodOrder_ThenReturnFoodOrder() throws FoodOrderException, FoodItemException {
        log.info("Entering givenFoodOrderId_WhenRetrieveFoodOrder_ThenReturnFoodOrder() Test");
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
        log.info("Leaving givenFoodOrderId_WhenRetrieveFoodOrder_ThenReturnFoodOrder() Test");
    }

    @Test
    @Tag("createFoodOrder")
    void givenCreateFoodOrderDto_WhenCreateFoodOrder_ThenReturnFoodOrderDto() throws FoodItemException, FoodOrderException, FoodMenuException {
        log.info("Entering givenCreateFoodOrderDto_WhenCreateFoodOrder_ThenReturnFoodOrderDto() Test");
        // given
        FoodItem foodItem = new FoodItem(1L, "Dosa", 15.0, FIXED_INSTANT, FIXED_INSTANT);

        FoodItemDto foodItemDto = new FoodItemDto(1L, "Dosa", 15.0, FIXED_INSTANT, FIXED_INSTANT);

        Set<Availability> availability = new HashSet<>(Arrays.asList(Availability.MONDAY,Availability.TUESDAY));
        FoodMenu foodMenu = new FoodMenu(1L,"Standard",FIXED_INSTANT,FIXED_INSTANT, availability);

        Map<Long, Integer> orderFoodItemQuantityMap = new HashMap<>();
        orderFoodItemQuantityMap.put(CUSTOMER_ID,1);

        Map<FoodItemDto, Integer> foodItemQuantityMap = new HashMap<>();
        foodItemQuantityMap.put(foodItemDto,1);

        CreateFoodOrderDto createfoodOrderDto = new CreateFoodOrderDto(CUSTOMER_ID, 1L, orderFoodItemQuantityMap);

        FoodOrder foodOrder = new FoodOrder(ORDER_ID,CUSTOMER_ID,TOTAL_COST,OrderStatus.ORDER_CART,FIXED_INSTANT);

        OrderFoodItemMap orderFoodItemMap = new OrderFoodItemMap(1L, foodOrder, foodMenu, foodItem, 1);

        FoodMenuItemMap foodMenuItemMap = new FoodMenuItemMap(1L, foodItem, foodMenu);

        FoodMenuItemQuantityMap foodMenuItemQuantityMap = new FoodMenuItemQuantityMap(1L,foodMenuItemMap,100,FIXED_INSTANT,FIXED_INSTANT);
        // when
        when(foodMenuRepository.findById(anyLong())).thenReturn(Optional.of(foodMenu));
        when(foodItemService.retrieveFoodItem(anyLong())).thenReturn(foodItemDto);
        when(foodMenuItemMapRepository.findByFoodMenuId(anyLong())).thenReturn(new ArrayList<>(List.of(foodMenuItemMap)));
        when(foodMenuItemQuantityMapRepository.findByFoodMenuItemMapId(anyLong())).thenReturn(foodMenuItemQuantityMap);
        when(orderFoodItemMapRepository.save(any(OrderFoodItemMap.class))).thenReturn(orderFoodItemMap);
        when(foodOrderRepository.save(any(FoodOrder.class))).thenReturn(foodOrder);
        FoodOrderDto foodOrderDto = foodOrderService.createFoodOrder(createfoodOrderDto);

        // then
        Assertions.assertThat(ORDER_ID).isEqualTo(foodOrderDto.getId());
        Assertions.assertThat(CUSTOMER_ID).isEqualTo(foodOrderDto.getCustomerId());
        Assertions.assertThat(foodItemQuantityMap).containsExactlyEntriesOf(foodOrderDto.getFoodItemsQuantityMap());
        log.info("Leaving givenCreateFoodOrderDto_WhenCreateFoodOrder_ThenReturnFoodOrderDto() Test");
    }

    @Test
    @Tag("updateFoodOrder")
    void givenTotalCost_WhenUpdateFoodOrder_ThenReturnFoodOrderDto() throws FoodOrderException, FoodItemException {
        log.info("Entering givenTotalCost_WhenUpdateFoodOrder_ThenReturnFoodOrderDto() Test");
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
        log.info("Leaving givenTotalCost_WhenUpdateFoodOrder_ThenReturnFoodOrderDto() Test");
    }

    @Test
    @Tag("updateFoodOrder")
    void givenInvalidFoodOrderIdFoodItemIdQuantity_WhenAddFoodItemToOrder_ThenThrowsException() throws FoodItemException, FoodOrderException {
        log.info("Entering givenInvalidFoodOrderIdFoodItemIdQuantity_WhenAddFoodItemToOrder_ThenThrowsException() Test");
        // given
        // when
        when(foodOrderRepository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        Assertions.assertThatThrownBy(() -> foodOrderService.addFoodItemToOrder(ORDER_ID, 1L, 1L, 1))
                .isInstanceOf(FoodOrderException.class)
                .hasMessage("Order not found for id: " + ORDER_ID);
        log.info("Leaving givenInvalidFoodOrderIdFoodItemIdQuantity_WhenAddFoodItemToOrder_ThenThrowsException() Test");
    }

    @Test
    @Tag("updateFoodOrder")
    void givenFoodOrderIdFoodItemIdQuantity_WhenAddFoodItemToOrder_ThenReturnFoodOrderDto() throws FoodItemException, FoodOrderException, FoodMenuException {
        log.info("Entering givenFoodOrderIdFoodItemIdQuantity_WhenAddFoodItemToOrder_ThenReturnFoodOrderDto() Test");
        // given
        FoodItem foodItem = new FoodItem(1L, "Dosa", 15.0, FIXED_INSTANT, FIXED_INSTANT);

        FoodItemDto foodItemDto = new FoodItemDto(1L, "Dosa", 15.0, FIXED_INSTANT, FIXED_INSTANT);

        Set<Availability> availability = new HashSet<>(Arrays.asList(Availability.MONDAY,Availability.TUESDAY));
        FoodMenu foodMenu = new FoodMenu(1L,"Standard",FIXED_INSTANT,FIXED_INSTANT, availability);

        Map<FoodItemDto, Integer> foodItemQuantityMap = new HashMap<>();

        FoodOrder foodOrder = new FoodOrder(ORDER_ID,CUSTOMER_ID,TOTAL_COST,OrderStatus.ORDER_CART,FIXED_INSTANT);
        FoodOrderDto foodOrderDto = new FoodOrderDto(ORDER_ID,CUSTOMER_ID,foodItemQuantityMap,TOTAL_COST,OrderStatus.ORDER_CART,FIXED_INSTANT);

        OrderFoodItemMap orderFoodItemMap = new OrderFoodItemMap(1L, foodOrder, foodMenu, foodItem, 1);

        FoodMenuItemMap foodMenuItemMap = new FoodMenuItemMap(1L, foodItem, foodMenu);

        FoodMenuItemQuantityMap foodMenuItemQuantityMap = new FoodMenuItemQuantityMap(1L,foodMenuItemMap,100,FIXED_INSTANT,FIXED_INSTANT);

        // when
        when(foodOrderRepository.findById(ORDER_ID)).thenReturn(Optional.of(foodOrder));
        when(foodMenuRepository.findById(anyLong())).thenReturn(Optional.of(foodMenu));
        when(foodItemService.retrieveFoodItem(anyLong())).thenReturn(foodItemDto);
        when(foodItemMapper.foodItemDtoToFoodItem(any(FoodItemDto.class))).thenReturn(foodItem);
        when(orderFoodItemMapRepository.findByFoodOrderIdAndFoodItemId(anyLong(),anyLong())).thenReturn(orderFoodItemMap);
        when(orderFoodItemMapRepository.save(any(OrderFoodItemMap.class))).thenReturn(orderFoodItemMap);
        when(foodOrderRepository.save(any(FoodOrder.class))).thenReturn(foodOrder);
        when(foodMenuItemMapRepository.findByFoodMenuId(anyLong())).thenReturn(new ArrayList<>(List.of(foodMenuItemMap)));
        when(foodMenuItemQuantityMapRepository.findByFoodMenuItemMapId(anyLong())).thenReturn(foodMenuItemQuantityMap);
        when(foodMenuItemQuantityMapRepository.findByFoodMenuItemMapId(anyLong())).thenReturn(foodMenuItemQuantityMap);
        when(foodMenuItemQuantityMapRepository.save(any(FoodMenuItemQuantityMap.class))).thenReturn(foodMenuItemQuantityMap);

        FoodOrderDto retrievedFoodOrderDto = foodOrderService.addFoodItemToOrder(ORDER_ID, 1L, 1L, 1);

        // then
        Assertions.assertThat(ORDER_ID).isEqualTo(retrievedFoodOrderDto.getId());
        Assertions.assertThat(foodItemQuantityMap).containsExactlyEntriesOf(retrievedFoodOrderDto.getFoodItemsQuantityMap());
        log.info("Leaving givenFoodOrderIdFoodItemIdQuantity_WhenAddFoodItemToOrder_ThenReturnFoodOrderDto() Test");
    }

    @Test
    @Tag("deleteFoodOrder")
    void givenInvalidFoodOrderId_WhenDeleteOrderFoodItem_ThenThrowsException() throws FoodOrderException {
        log.info("Entering givenInvalidFoodOrderId_WhenDeleteOrderFoodItem_ThenThrowsException() Test");
        // given
        // when
        when(foodOrderRepository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        Assertions.assertThatThrownBy(() -> foodOrderService.deleteOrderFoodItem(ORDER_ID, 1L))
                .isInstanceOf(FoodOrderException.class)
                .hasMessage("Order not found for id: " + ORDER_ID);
        log.info("Leaving givenInvalidFoodOrderId_WhenDeleteOrderFoodItem_ThenThrowsException() Test");
    }

    @Test
    @Tag("deleteFoodOrder")
    void givenFoodOrderIdFoodItemId_WhenDeleteOrderFoodItem_ThenOrderFoodItemMapDeleted() throws FoodOrderException {
        log.info("Entering givenFoodOrderIdFoodItemId_WhenDeleteOrderFoodItem_ThenOrderFoodItemMapDeleted() Test");
        // given
        FoodItem foodItem = new FoodItem(1L, "Dosa", 15.0, FIXED_INSTANT, FIXED_INSTANT);

        FoodItemDto foodItemDto = new FoodItemDto(1L, "Dosa", 15.0, FIXED_INSTANT, FIXED_INSTANT);

        Set<Availability> availability = new HashSet<>(Arrays.asList(Availability.MONDAY,Availability.TUESDAY));
        FoodMenu foodMenu = new FoodMenu(1L,"Standard",FIXED_INSTANT,FIXED_INSTANT, availability);

        Map<FoodItemDto, Integer> foodItemQuantityMap = new HashMap<>();

        FoodOrder foodOrder = new FoodOrder(ORDER_ID,CUSTOMER_ID,TOTAL_COST,OrderStatus.ORDER_CART,FIXED_INSTANT);
        FoodOrderDto foodOrderDto = new FoodOrderDto(ORDER_ID,CUSTOMER_ID,foodItemQuantityMap,TOTAL_COST,OrderStatus.ORDER_CART,FIXED_INSTANT);

        OrderFoodItemMap orderFoodItemMap = new OrderFoodItemMap(1L, foodOrder, foodMenu, foodItem, 1);

        FoodMenuItemMap foodMenuItemMap = new FoodMenuItemMap(1L, foodItem, foodMenu);

        FoodMenuItemQuantityMap foodMenuItemQuantityMap = new FoodMenuItemQuantityMap(1L,foodMenuItemMap,100,FIXED_INSTANT,FIXED_INSTANT);

        // when
        when(foodOrderRepository.findById(anyLong())).thenReturn(Optional.of(foodOrder));
        when(orderFoodItemMapRepository.findByFoodOrderId(anyLong())).thenReturn(new ArrayList<>(List.of(orderFoodItemMap)));
        when(foodOrderRepository.save(any(FoodOrder.class))).thenReturn(foodOrder);
        doNothing().when(orderFoodItemMapRepository).delete(orderFoodItemMap);
        when(foodMenuItemMapRepository.findByFoodMenuId(anyLong())).thenReturn(new ArrayList<>(List.of(foodMenuItemMap)));
        when(foodMenuItemQuantityMapRepository.findByFoodMenuItemMapId(anyLong())).thenReturn(foodMenuItemQuantityMap);
        when(foodMenuItemQuantityMapRepository.save(any(FoodMenuItemQuantityMap.class))).thenReturn(foodMenuItemQuantityMap);

        foodOrderService.deleteOrderFoodItem(ORDER_ID, 1L);

        // then
        verify(orderFoodItemMapRepository, times(1)).delete(orderFoodItemMap);
        log.info("Leaving givenFoodOrderIdFoodItemId_WhenDeleteOrderFoodItem_ThenOrderFoodItemMapDeleted() Test");
    }

    @Test
    @Tag("deleteFoodOrder")
    void givenInvalidFoodOrderId_WhenDeleteFoodOrder_ThenThrowsException() throws FoodOrderException {
        log.info("Entering givenInvalidFoodOrderId_WhenDeleteFoodOrder_ThenThrowsException() Test");
        // given
        // when
        when(foodOrderRepository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        Assertions.assertThatThrownBy(() -> foodOrderService.deleteFoodOrder(ORDER_ID))
                .isInstanceOf(FoodOrderException.class)
                .hasMessage("Order not found for id: " + ORDER_ID);
        log.info("Leaving givenInvalidFoodOrderId_WhenDeleteFoodOrder_ThenThrowsException() Test");
    }

    @Test
    @Tag("deleteFoodOrder")
    void givenFoodOrderId_WhenDeleteFoodOrder_ThenFoodOrderDeleted() throws FoodOrderException {
        log.info("Entering givenFoodOrderId_WhenDeleteFoodOrder_ThenFoodOrderDeleted() Test");
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
        log.info("Leaving givenFoodOrderId_WhenDeleteFoodOrder_ThenFoodOrderDeleted() Test");
    }
}