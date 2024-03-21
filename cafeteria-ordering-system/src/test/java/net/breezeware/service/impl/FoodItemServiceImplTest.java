package net.breezeware.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.breezeware.dao.FoodItemRepository;
import net.breezeware.dto.FoodItemDto;
import net.breezeware.entity.FoodItem;
import net.breezeware.exception.FoodItemException;
import net.breezeware.mapper.FoodItemMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@Slf4j
class FoodItemServiceImplTest {

    public static final long ID = 1L;
    public static final Instant FIXED_INSTANT = Instant.now();
    public static final String FOOD_ITEM_NAME = "Dosa";
    public static final double PRICE = 10.00;
    @Mock
    FoodItemRepository foodItemRepository;

    @InjectMocks
    FoodItemServiceImpl foodItemService;

    @BeforeEach
    void setUp() throws Exception {
        log.info("Entering Test Setup");
        MockitoAnnotations.openMocks(this);
        foodItemService = new FoodItemServiceImpl(foodItemRepository, FoodItemMapper.INSTANCE);
        log.info("leaving Test Setup");
    }

    @Test
    void givenRetrieveFoodItemsRequest_WhenRetrieveFoodItems_ThenTrowsException() throws FoodItemException {
        log.info("Entering RetrieveFoodItems Test");

        // given
        List<FoodItem> emptyFoodItemsList = new ArrayList<>();

        // when
        when(foodItemRepository.findAll()).thenReturn(emptyFoodItemsList);

        // then
        Assertions.assertThatThrownBy(() -> foodItemService.retrieveFoodItems())
                .isInstanceOf(FoodItemException.class)
                .hasMessage("No food Items found");
        log.info("Leaving RetrieveFoodItems Test");
    }

    @Test
    void givenRetrieveFoodItemsRequest_WhenRetrieveFoodItems_ThenReturnFoodItemsList() throws FoodItemException {
        log.info("Entering RetrieveFoodItems Test");

        // given
        List<FoodItem> foodItems = new ArrayList<>();
        FoodItem foodItem = new FoodItem();
        foodItem.setId(ID);
        foodItem.setName(FOOD_ITEM_NAME);
        foodItem.setPrice(PRICE);
        foodItem.setCreated(FIXED_INSTANT);
        foodItem.setModified(FIXED_INSTANT);
        foodItems.add(foodItem);

        // when
        when(foodItemRepository.findAll()).thenReturn(foodItems);
        List<FoodItemDto> foodItemDtos = foodItemService.retrieveFoodItems();

        // then
        verify(foodItemRepository, times(1)).findAll();

        Assertions.assertThat(1).isEqualTo(foodItemDtos.size());
        log.info("Leaving RetrieveFoodItems Test");
    }

    @Test
    void givenFoodItemId_WhenRetrieveFoodItem_ThenTrowsException() throws FoodItemException {
        log.info("Entering RetrieveFoodItem Test");

        // given ID
        // when
        when(foodItemRepository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        Assertions.assertThatThrownBy(() -> foodItemService.retrieveFoodItem(ID))
                .isInstanceOf(FoodItemException.class)
                .hasMessage("Food item not found for id: " + ID);
        log.info("Leaving RetrieveFoodItem Test");
    }

    @Test
    void givenFoodItemId_WhenRetrieveFoodItem_ThenReturnFoodItemDto() throws FoodItemException {
        log.info("Entering RetrieveFoodItem Test");

        // given
        FoodItem foodItem = new FoodItem();
        foodItem.setId(ID);
        foodItem.setName(FOOD_ITEM_NAME);
        foodItem.setPrice(PRICE);
        foodItem.setCreated(FIXED_INSTANT);
        foodItem.setModified(FIXED_INSTANT);

        // when
        when(foodItemRepository.findById(anyLong())).thenReturn(Optional.of(foodItem));

        FoodItemDto foodItemDto = foodItemService.retrieveFoodItem(ID);

        // then
        Assertions.assertThat(1L).isEqualTo(foodItemDto.getId());
        Assertions.assertThat("Dosa").isEqualTo(foodItemDto.getName());
        log.info("Leaving RetrieveFoodItem");
    }

    @Test
    void givenFoodItemName_WhenRetrieveFoodItemByName_ThenTrowsException() throws FoodItemException {
        log.info("Entering RetrieveFoodItemByName Test");

        // given ID
        // when
        when(foodItemRepository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        Assertions.assertThatThrownBy(() -> foodItemService.retrieveFoodItemByName(FOOD_ITEM_NAME))
                .isInstanceOf(FoodItemException.class)
                .hasMessage("Food item not found for name: " + FOOD_ITEM_NAME);
        log.info("Leaving RetrieveFoodItemByName Test");
    }

    @Test
    void givenFoodItemName_WhenRetrieveFoodItemByName_ThenReturnFoodItemDto() throws FoodItemException {
        log.info("Entering RetrieveFoodItemByName Test");

        // given
        FoodItem foodItem = new FoodItem();
        foodItem.setId(ID);
        foodItem.setName(FOOD_ITEM_NAME);
        foodItem.setPrice(PRICE);
        foodItem.setCreated(FIXED_INSTANT);
        foodItem.setModified(FIXED_INSTANT);

        // when
        when(foodItemRepository.findByName(anyString())).thenReturn(Optional.of(foodItem));

        FoodItemDto foodItemDto = foodItemService.retrieveFoodItemByName(FOOD_ITEM_NAME);

        // then
        Assertions.assertThat(1L).isEqualTo(foodItemDto.getId());
        Assertions.assertThat("Dosa").isEqualTo(foodItemDto.getName());
        log.info("Leaving RetrieveFoodItemByName Test");
    }

    @Test
    void givenFoodItemDto_WhenCreateFoodItem_ThenReturnFoodItemDto() throws FoodItemException {
        log.info("Entering CreateFoodItem Test");

        // given
        FoodItemDto foodItemDto = new FoodItemDto();
        foodItemDto.setName(FOOD_ITEM_NAME);
        foodItemDto.setPrice(PRICE);

        FoodItem foodItem = new FoodItem();
        foodItem.setId(ID);
        foodItem.setName(FOOD_ITEM_NAME);
        foodItem.setPrice(PRICE);

        // when
        when(foodItemRepository.save(any(FoodItem.class))).thenReturn(foodItem);

        FoodItemDto createdFoodItem = foodItemService.createFoodItem(foodItemDto);

        // then
        Assertions.assertThat(1L).isEqualTo(createdFoodItem.getId());
        Assertions.assertThat("Dosa").isEqualTo(createdFoodItem.getName());
        log.info("Leaving CreateFoodItem Test");
    }

    @Test
    void givenFoodItemIdAndDto_WhenUpdateFoodItem_ThenTrowsException() throws FoodItemException {
        log.info("Entering UpdateFoodItem Test");
        // given ID and Dto
        // when
        when(foodItemRepository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        Assertions.assertThatThrownBy(() -> foodItemService.updateFoodItem(ID,any(FoodItemDto.class)))
                .isInstanceOf(FoodItemException.class)
                .hasMessage("Food item not found for id: " + ID);
        log.info("Leaving UpdateFoodItem Test");
    }

    @Test
    void givenFoodItemDto_WhenUpdateFoodItem_ThenReturnFoodItemDto() throws FoodItemException {
        log.info("Entering UpdateFoodItem Test");
        // given
        FoodItemDto updateFoodItemDto = new FoodItemDto();
        updateFoodItemDto.setName("Idli");
        updateFoodItemDto.setPrice(PRICE);

        FoodItem FoodItem = new FoodItem();
        FoodItem.setId(ID);
        FoodItem.setName("Idli");
        updateFoodItemDto.setPrice(PRICE);

        // when
        when(foodItemRepository.findById(anyLong())).thenReturn(Optional.of(FoodItem));
        when(foodItemRepository.save(any(FoodItem.class))).thenReturn(FoodItem);
        FoodItemDto updatedFoodItem = foodItemService.updateFoodItem(ID, updateFoodItemDto);

        // then
        Assertions.assertThat(1L).isEqualTo(updatedFoodItem.getId());
        Assertions.assertThat("Idli").isEqualTo(updatedFoodItem.getName());
        log.info("Leaving UpdateFoodItem Test");
    }

    @Test
    void givenFoodItemNameInDto_WhenUpdateFoodItem_ThenReturnFoodItemDto() throws FoodItemException {
        log.info("Entering UpdateFoodItem Test");
        // given
        FoodItemDto foodItemDto = new FoodItemDto();
        String newFoodItemName = "Idli";
        foodItemDto.setName(newFoodItemName);

        FoodItem excistingFoodItem = new FoodItem();
        excistingFoodItem.setId(ID);
        excistingFoodItem.setName(FOOD_ITEM_NAME);
        excistingFoodItem.setPrice(PRICE);

        FoodItem savedFoodItem = new FoodItem();
        savedFoodItem.setId(ID);
        savedFoodItem.setName(newFoodItemName);
        savedFoodItem.setPrice(PRICE);

        // when
        when(foodItemRepository.findById(anyLong())).thenReturn(Optional.of(excistingFoodItem));
        when(foodItemRepository.save(any(FoodItem.class))).thenReturn(savedFoodItem);
        FoodItemDto updatedFoodItem = foodItemService.updateFoodItem(ID, foodItemDto);

        // then
        Assertions.assertThat(1L).isEqualTo(updatedFoodItem.getId());
        Assertions.assertThat(newFoodItemName).isEqualTo(updatedFoodItem.getName());
        log.info("Leaving UpdateFoodItem Test");
    }

    @Test
    void givenFoodItemPriceInDto_WhenUpdateFoodItem_ThenReturnFoodItemDto() throws FoodItemException {
        log.info("Entering UpdateFoodItem Test");
        // given
        double newPrice = 15.0;
        FoodItemDto foodItemDto = new FoodItemDto();
        foodItemDto.setPrice(newPrice);

        FoodItem excistingFoodItem = new FoodItem();
        excistingFoodItem.setId(ID);
        excistingFoodItem.setName(FOOD_ITEM_NAME);
        foodItemDto.setPrice(PRICE);

        FoodItem savedFoodItem = new FoodItem();
        savedFoodItem.setId(ID);
        savedFoodItem.setName(FOOD_ITEM_NAME);
        savedFoodItem.setPrice(newPrice);

        // when
        when(foodItemRepository.findById(anyLong())).thenReturn(Optional.of(excistingFoodItem));
        when(foodItemRepository.save(any(FoodItem.class))).thenReturn(savedFoodItem);
        FoodItemDto updatedFoodItem = foodItemService.updateFoodItem(ID, foodItemDto);

        // then
        Assertions.assertThat(1L).isEqualTo(updatedFoodItem.getId());
        Assertions.assertThat(FOOD_ITEM_NAME).isEqualTo(updatedFoodItem.getName());
        Assertions.assertThat(newPrice).isEqualTo(updatedFoodItem.getPrice());

        log.info("Leaving UpdateFoodItem Test");
    }

    @Test
    void givenFoodItemId_WhenDeleteFoodItem_ThenTrowsException() throws FoodItemException {
        log.info("Entering DeleteFoodItem Test");

        // given ID
        // when
        when(foodItemRepository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        Assertions.assertThatThrownBy(() -> foodItemService.deleteFoodItem(ID))
                .isInstanceOf(FoodItemException.class)
                .hasMessage("Food item not found for id: " + ID);
        log.info("Leaving DeleteFoodItem Test");
    }

    @Test
    void givenDeleteFoodItemRequest_WhenDeleteFoodItem_ThenFoodItemDeleted() throws FoodItemException {
        log.info("Entering DeleteFoodItem Test");

        // given
        FoodItem foodItem = new FoodItem();
        foodItem.setId(ID);
        foodItem.setName(FOOD_ITEM_NAME);
        foodItem.setPrice(PRICE);
        foodItem.setCreated(FIXED_INSTANT);
        foodItem.setModified(FIXED_INSTANT);

        // when
        when(foodItemRepository.findById(anyLong())).thenReturn(Optional.of(foodItem));
        doNothing().when(foodItemRepository).deleteById(anyLong());
        foodItemService.deleteFoodItem(ID);

        // then
        verify(foodItemRepository, times(1)).deleteById(ID);
        log.info("Leaving DeleteFoodItem Test");
    }
}