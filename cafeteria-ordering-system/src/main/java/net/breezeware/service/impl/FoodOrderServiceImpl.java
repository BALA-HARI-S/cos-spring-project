package net.breezeware.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.breezeware.dao.FoodOrderRepository;
import net.breezeware.dao.OrderFoodItemMapRepository;
import net.breezeware.dto.foodOrderDto.CreateFoodOrderDto;
import net.breezeware.dto.foodOrderDto.FoodOrderDto;
import net.breezeware.dto.foodOrderDto.UpdateFoodOrderDto;
import net.breezeware.dto.foodItemDto.FoodItemDto;
import net.breezeware.entity.FoodItem;
import net.breezeware.entity.FoodOrder;
import net.breezeware.entity.OrderFoodItemMap;
import net.breezeware.entity.OrderStatus;
import net.breezeware.exception.FoodItemException;
import net.breezeware.exception.FoodOrderException;
import net.breezeware.mapper.FoodItemMapper;
import net.breezeware.mapper.FoodOrderMapper;
import net.breezeware.service.api.FoodItemService;
import net.breezeware.service.api.FoodOrderService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class FoodOrderServiceImpl implements FoodOrderService {

    private final FoodOrderMapper foodOrderMapper;

    private final FoodOrderRepository foodOrderRepository;

    private final OrderFoodItemMapRepository orderFoodItemMapRepository;

    private final FoodItemService foodItemService;

    private final FoodItemMapper foodItemMapper;

    @Override
    public List<FoodOrderDto> retrieveFoodOrders() throws FoodItemException {
        log.info("Entering retrieveFoodOrders() service");
        List<FoodOrder> foodOrderList = foodOrderRepository.findAll();
        List<FoodOrderDto> foodOrderDtoList = new ArrayList<>();

        for(FoodOrder foodOrder: foodOrderList){
            FoodOrderDto foodOrderDto = new FoodOrderDto();
            foodOrderDto.setId(foodOrder.getId());
            foodOrderDto.setTotalCost(foodOrder.getTotalCost());
            foodOrderDto.setOrderStatus(foodOrder.getOrderStatus());
            foodOrderDto.setCreated(foodOrder.getCreated());

            List<OrderFoodItemMap> orderFoodItemMaps = orderFoodItemMapRepository.findByFoodOrderId(foodOrder.getId());
            Map<FoodItemDto, Integer> orderFoodItemQuantityMap = new HashMap<>();

            for (OrderFoodItemMap itemMap: orderFoodItemMaps){
                FoodItemDto foodItemDto = foodItemService.retrieveFoodItem(itemMap.getFoodItem().getId());
                Integer quantity = itemMap.getQuantity();
                orderFoodItemQuantityMap.put(foodItemDto, quantity);
            }

            foodOrderDto.setFoodItemsQuantityMap(orderFoodItemQuantityMap);
            foodOrderDtoList.add(foodOrderDto);
        }

        log.info("Leaving retrieveFoodOrders() service");
        return foodOrderDtoList;
    }

    @Override
    public FoodOrderDto retrieveFoodOrder(Long id) throws FoodOrderException, FoodItemException {
        log.info("Entering retrieveFoodOrder() service");
        FoodOrder foodOrder = foodOrderRepository.findById(id).orElseThrow(() ->
                new FoodOrderException("Order not found for id: " + id));

        FoodOrderDto foodOrderDto = new FoodOrderDto();
        foodOrderDto.setId(foodOrder.getId());
        foodOrderDto.setTotalCost(foodOrder.getTotalCost());
        foodOrderDto.setOrderStatus(foodOrder.getOrderStatus());
        foodOrderDto.setCreated(foodOrder.getCreated());

        List<OrderFoodItemMap> orderFoodItemMaps = orderFoodItemMapRepository.findByFoodOrderId(foodOrder.getId());
        Map<FoodItemDto, Integer> orderFoodItemQuantityMap = new HashMap<>();

        for (OrderFoodItemMap itemMap: orderFoodItemMaps){
            FoodItemDto foodItemDto = foodItemService.retrieveFoodItem(itemMap.getFoodItem().getId());
            Integer quantity = itemMap.getQuantity();
            orderFoodItemQuantityMap.put(foodItemDto, quantity);
        }

        foodOrderDto.setFoodItemsQuantityMap(orderFoodItemQuantityMap);
        log.info("Leaving retrieveFoodOrder() service");
        return foodOrderDto;
    }

    @Override
    public FoodOrderDto createFoodOrder(CreateFoodOrderDto createFoodOrderDto) throws FoodItemException {
        log.info("Entering createFoodOrder() service");
        FoodOrder foodOrder = new FoodOrder();
        foodOrder.setCustomerId(createFoodOrderDto.getCustomerId());
        foodOrder.setOrderStatus(OrderStatus.ORDER_CART);
        foodOrder.setCreated(Instant.now());
        double totalCost = 0.00;

        for(Long foodItemId: createFoodOrderDto.getFoodItemsQuantityMap().keySet()){
            FoodItemDto foodItem = foodItemService.retrieveFoodItem(foodItemId);
            Integer quantity = createFoodOrderDto.getFoodItemsQuantityMap().get(foodItemId);
            totalCost += foodItem.getPrice() * quantity;
        }

        foodOrder.setTotalCost(totalCost);
        FoodOrder savedFoodOrder = foodOrderRepository.save(foodOrder);

        Map<FoodItemDto, Integer> foodItemQuantity = new HashMap<>();
        for(Long foodItemId: createFoodOrderDto.getFoodItemsQuantityMap().keySet()){
            FoodItemDto foodItemDto = foodItemService.retrieveFoodItem(foodItemId);
            FoodItem foodItem = foodItemMapper.foodItemDtoToFoodItem(foodItemDto);
            Integer quantity = createFoodOrderDto.getFoodItemsQuantityMap().get(foodItemId);

            foodItemQuantity.put(foodItemDto, quantity);

            OrderFoodItemMap orderFoodItemMap = new OrderFoodItemMap();
            orderFoodItemMap.setFoodOrder(savedFoodOrder);
            orderFoodItemMap.setFoodItem(foodItem);
            orderFoodItemMap.setQuantity(quantity);
            orderFoodItemMapRepository.save(orderFoodItemMap);
        }
        FoodOrderDto foodOrderDto = new FoodOrderDto();
        foodOrderDto.setId(savedFoodOrder.getId());
        foodOrderDto.setCustomerId(savedFoodOrder.getCustomerId());
        foodOrderDto.setTotalCost(savedFoodOrder.getTotalCost());
        foodOrderDto.setOrderStatus(savedFoodOrder.getOrderStatus());
        foodOrderDto.setCreated(savedFoodOrder.getCreated());
        foodOrderDto.setFoodItemsQuantityMap(foodItemQuantity);

        log.info("Leaving createFoodOrder() service");
        return foodOrderDto;
    }

    @Override
    public FoodOrderDto updateFoodOrder(Long orderId,UpdateFoodOrderDto updateFoodOrderDto) throws FoodOrderException, FoodItemException {
        log.info("Entering updateFoodOrder() service");
        FoodOrder foodOrder = foodOrderRepository.findById(orderId).orElseThrow(() ->
                new FoodOrderException("Order not found for id: " + orderId));
        if(!Objects.isNull(updateFoodOrderDto.getOrderStatus())){
            foodOrder.setOrderStatus(updateFoodOrderDto.getOrderStatus());
        }
        if(!Objects.isNull(updateFoodOrderDto.getTotalCost())){
            foodOrder.setTotalCost(updateFoodOrderDto.getTotalCost());
        }
        FoodOrder savedFoodOrder = foodOrderRepository.save(foodOrder);

        log.info("Leaving updateFoodOrder() service");
        return retrieveFoodOrder(savedFoodOrder.getId());
    }

    @Override
    public FoodOrderDto addFoodItemToOrder(Long orderId, Long foodItemId, Integer quantity) throws FoodOrderException, FoodItemException {
        log.info("Entering addFoodItemToOrder() service");
        FoodOrder foodOrder = foodOrderRepository.findById(orderId).orElseThrow(() ->
                new FoodOrderException("Order not found for id: " + orderId));

        FoodItem foodItem = foodItemMapper.foodItemDtoToFoodItem(foodItemService.retrieveFoodItem(foodItemId));

        OrderFoodItemMap orderFoodItemMap = new OrderFoodItemMap();
        orderFoodItemMap.setFoodOrder(foodOrder);
        orderFoodItemMap.setFoodItem(foodItem);
        orderFoodItemMap.setQuantity(quantity);
        OrderFoodItemMap savedOrderItem = orderFoodItemMapRepository.save(orderFoodItemMap);

        double totalCost = foodOrder.getTotalCost();
        totalCost += savedOrderItem.getFoodItem().getPrice() * savedOrderItem.getQuantity();
        foodOrder.setTotalCost(totalCost);
        FoodOrder savedFoodOrder = foodOrderRepository.save(foodOrder);

        log.info("Leaving addFoodItemToOrder() service");
        return retrieveFoodOrder(savedFoodOrder.getId());
    }

    @Override
    public void deleteOrderFoodItem(Long orderId, Long foodItemId) throws FoodOrderException {
        log.info("Entering deleteOrderFoodItem() service");
        FoodOrder foodOrder = foodOrderRepository.findById(orderId).orElseThrow(() ->
                new FoodOrderException("Order not found for id: " + orderId));

        List<OrderFoodItemMap> orderFoodItemMaps = orderFoodItemMapRepository.findByFoodOrderId(orderId);
        for(OrderFoodItemMap itemMap: orderFoodItemMaps){
            if(Objects.equals(itemMap.getFoodItem().getId(), foodItemId)){
                double totalCost = foodOrder.getTotalCost();
                totalCost -= itemMap.getFoodItem().getPrice() * itemMap.getQuantity();
                foodOrder.setTotalCost(totalCost);
                foodOrderRepository.save(foodOrder);
                orderFoodItemMapRepository.delete(itemMap);
            }
        }
        log.info("Leaving deleteOrderFoodItem() service");
    }

    @Override
    @Transactional
    public void deleteFoodOrder(Long orderId) throws FoodOrderException {
        log.info("Entering deleteFoodOrder() service");
        FoodOrder foodOrder = foodOrderRepository.findById(orderId).orElseThrow(() ->
                new FoodOrderException("Order not found for id: " + orderId));
        orderFoodItemMapRepository.deleteByFoodOrderId(foodOrder.getId());
        foodOrderRepository.delete(foodOrder);
        log.info("Leaving deleteFoodOrder() service");
    }
}
