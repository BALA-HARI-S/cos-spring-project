package net.breezeware.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import net.breezeware.dao.FoodItemRepository;
import net.breezeware.dto.food.item.CreateFoodItemDto;
import net.breezeware.dto.food.item.FoodItemDto;
import net.breezeware.dto.food.item.UpdateFoodItemDto;
import net.breezeware.entity.FoodItem;
import net.breezeware.exception.FoodItemAlreadyExistException;
import net.breezeware.exception.FoodItemException;
import net.breezeware.mapper.FoodItemMapper;

import lombok.extern.slf4j.Slf4j;


@Slf4j
class FoodItemServiceImplTest {

    public static final long ID = 1L;
    public static final Instant FIXED_INSTANT = Instant.now();
    public static final String FOOD_ITEM_NAME = "Dosa";
    public static final double PRICE = 10.00;
    @Mock
    private FoodItemRepository foodItemRepository;

    @Mock
    private FoodItemMapper foodItemMapper;

    @InjectMocks
    private FoodItemServiceImpl foodItemService;

    @BeforeEach
    void setUp() throws Exception {
        log.info("Entering Test Setup");
        MockitoAnnotations.openMocks(this);
        log.info("leaving Test Setup");
    }

    @Test
    @Tag("retrieveFoodItem")
    void givenRetrieveFoodItemsRequest_WhenRetrieveFoodItems_ThenReturnFoodItemsList() throws FoodItemException {
        log.info("Entering givenRetrieveFoodItemsRequest_WhenRetrieveFoodItems_ThenReturnFoodItemsList() Test");

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

        Assertions.assertThat(foodItemDtos.size()).isEqualTo(1);
        log.info("Leaving givenRetrieveFoodItemsRequest_WhenRetrieveFoodItems_ThenReturnFoodItemsList() Test");
    }

    @Test
    @Tag("retrieveFoodItem")
    void givenFoodItemId_WhenRetrieveFoodItem_ThenTrowsException() {
        log.info("Entering givenFoodItemId_WhenRetrieveFoodItem_ThenTrowsException() Test");

        // given ID
        // when
        when(foodItemRepository.findById(ID)).thenReturn(Optional.empty());

        // then
        Assertions.assertThatThrownBy(() -> foodItemService.retrieveFoodItem(ID))
                .isInstanceOf(FoodItemException.class)
                .hasMessage("Food item not found for id: " + ID);
        log.info("Leaving givenFoodItemId_WhenRetrieveFoodItem_ThenTrowsException() Test");
    }

    @Test
    @Tag("retrieveFoodItem")
    void givenFoodItemId_WhenRetrieveFoodItem_ThenReturnFoodItemDto() throws FoodItemException {
        log.info("Entering givenFoodItemId_WhenRetrieveFoodItem_ThenReturnFoodItemDto() Test");

        // given
        FoodItem foodItem = new FoodItem();
        foodItem.setId(ID);
        foodItem.setName(FOOD_ITEM_NAME);
        foodItem.setPrice(PRICE);
        foodItem.setCreated(FIXED_INSTANT);
        foodItem.setModified(FIXED_INSTANT);

        FoodItemDto foodItemDto = new FoodItemDto();
        foodItemDto.setId(ID);
        foodItemDto.setName(FOOD_ITEM_NAME);
        foodItemDto.setPrice(PRICE);
        foodItemDto.setCreated(FIXED_INSTANT);
        foodItemDto.setModified(FIXED_INSTANT);

        // when
        when(foodItemMapper.foodItemToFoodItemDto(any(FoodItem.class))).thenReturn(foodItemDto);
        when(foodItemRepository.findById(ID)).thenReturn(Optional.of(foodItem));

        FoodItemDto retrievedFoodItem = foodItemService.retrieveFoodItem(ID);

        // then
        Assertions.assertThat(retrievedFoodItem.getId()).isEqualTo(ID);
        Assertions.assertThat(retrievedFoodItem.getName()).isEqualTo(FOOD_ITEM_NAME);
        log.info("Leaving givenFoodItemId_WhenRetrieveFoodItem_ThenReturnFoodItemDto() Test");
    }

    @Test
    @Tag("retrieveFoodItem")
    void givenFoodItemName_WhenRetrieveFoodItemByName_ThenTrowsException() {
        log.info("Entering givenFoodItemName_WhenRetrieveFoodItemByName_ThenTrowsException() Test");

        // given ID
        // when
        when(foodItemRepository.findById(ID)).thenReturn(Optional.empty());

        // then
        Assertions.assertThatThrownBy(() -> foodItemService.retrieveFoodItemByName(FOOD_ITEM_NAME))
                .isInstanceOf(FoodItemException.class)
                .hasMessage("Food item not found for name: " + FOOD_ITEM_NAME);
        log.info("Leaving givenFoodItemName_WhenRetrieveFoodItemByName_ThenTrowsException() Test");
    }

    @Test
    @Tag("retrieveFoodItem")
    void givenFoodItemName_WhenRetrieveFoodItemByName_ThenReturnFoodItemDto() throws FoodItemException {
        log.info("Entering givenFoodItemName_WhenRetrieveFoodItemByName_ThenReturnFoodItemDto() Test");

        // given
        FoodItem foodItem = new FoodItem();
        foodItem.setId(ID);
        foodItem.setName(FOOD_ITEM_NAME);
        foodItem.setPrice(PRICE);
        foodItem.setCreated(FIXED_INSTANT);
        foodItem.setModified(FIXED_INSTANT);

        FoodItemDto foodItemDto = new FoodItemDto();
        foodItemDto.setId(ID);
        foodItemDto.setName(FOOD_ITEM_NAME);
        foodItemDto.setPrice(PRICE);
        foodItemDto.setCreated(FIXED_INSTANT);
        foodItemDto.setModified(FIXED_INSTANT);
        // when
        when(foodItemMapper.foodItemToFoodItemDto(any(FoodItem.class))).thenReturn(foodItemDto);
        when(foodItemRepository.findByName(FOOD_ITEM_NAME)).thenReturn(Optional.of(foodItem));

        FoodItemDto retrievedFoodItemDto = foodItemService.retrieveFoodItemByName(FOOD_ITEM_NAME);

        // then
        Assertions.assertThat(retrievedFoodItemDto.getId()).isEqualTo(ID);
        Assertions.assertThat(retrievedFoodItemDto.getName()).isEqualTo(FOOD_ITEM_NAME);
        log.info("Leaving givenFoodItemName_WhenRetrieveFoodItemByName_ThenReturnFoodItemDto() Test");
    }

    @Test
    @Tag("createFoodItem")
    void givenFoodItemDto_WhenCreateFoodItem_ThenReturnFoodItemDto() throws FoodItemAlreadyExistException {
        log.info("Entering givenFoodItemDto_WhenCreateFoodItem_ThenReturnFoodItemDto() Test");

        // given
        CreateFoodItemDto createFoodItemDto = new CreateFoodItemDto();
        createFoodItemDto.setName(FOOD_ITEM_NAME);
        createFoodItemDto.setPrice(PRICE);

        FoodItem foodItem = new FoodItem();
        foodItem.setId(ID);
        foodItem.setName(FOOD_ITEM_NAME);
        foodItem.setPrice(PRICE);
        foodItem.setCreated(FIXED_INSTANT);
        foodItem.setModified(FIXED_INSTANT);

        FoodItemDto foodItemDto = new FoodItemDto();
        foodItemDto.setId(ID);
        foodItemDto.setName(FOOD_ITEM_NAME);
        foodItemDto.setPrice(PRICE);
        foodItemDto.setCreated(FIXED_INSTANT);
        foodItemDto.setModified(FIXED_INSTANT);
        // when
        when(foodItemMapper.foodItemToFoodItemDto(any(FoodItem.class))).thenReturn(foodItemDto);
        when(foodItemMapper.foodItemDtoToFoodItem(any(FoodItemDto.class))).thenReturn(foodItem);
        when(foodItemRepository.save(any(FoodItem.class))).thenReturn(foodItem);

        FoodItemDto createdFoodItem = foodItemService.createFoodItem(createFoodItemDto);

        // then
        Assertions.assertThat(createdFoodItem.getId()).isEqualTo(ID);
        Assertions.assertThat(createdFoodItem.getName()).isEqualTo(FOOD_ITEM_NAME);
        log.info("Leaving givenFoodItemDto_WhenCreateFoodItem_ThenReturnFoodItemDto() Test");
    }

    @Test
    @Tag("updateFoodItem")
    void givenFoodItemIdAndDto_WhenUpdateFoodItem_ThenTrowsException() {
        log.info("Entering givenFoodItemIdAndDto_WhenUpdateFoodItem_ThenTrowsException() Test");
        // given ID and Dto
        // when
        when(foodItemRepository.findById(ID)).thenReturn(Optional.empty());

        // then
        Assertions.assertThatThrownBy(() -> foodItemService.updateFoodItem(ID,any(UpdateFoodItemDto.class)))
                .isInstanceOf(FoodItemException.class)
                .hasMessage("Food item not found for id: " + ID);
        log.info("Leaving givenFoodItemIdAndDto_WhenUpdateFoodItem_ThenTrowsException() Test");
    }

    @Test
    @Tag("updateFoodItem")
    void givenFoodItemDtoWithNewName_WhenUpdateFoodItem_ThenReturnFoodItemDto() throws FoodItemException {
        log.info("Entering givenFoodItemDto_WhenUpdateFoodItem_ThenReturnFoodItemDto() Test");
        // given
        UpdateFoodItemDto updateFoodItemDto = new UpdateFoodItemDto();
        updateFoodItemDto.setName("Idli");

        FoodItem updateFoodItem = new FoodItem();
        updateFoodItem.setName("Idli");

        FoodItem savedFoodItem = new FoodItem();
        savedFoodItem.setId(ID);
        savedFoodItem.setName("Idli");
        savedFoodItem.setPrice(PRICE);
        savedFoodItem.setCreated(FIXED_INSTANT);
        savedFoodItem.setModified(FIXED_INSTANT);

        FoodItem existingFoodItem = new FoodItem();
        existingFoodItem.setId(ID);
        existingFoodItem.setName(FOOD_ITEM_NAME);
        existingFoodItem.setPrice(PRICE);
        existingFoodItem.setCreated(FIXED_INSTANT);
        existingFoodItem.setModified(FIXED_INSTANT);

        FoodItemDto foodItemDto = new FoodItemDto();
        foodItemDto.setId(ID);
        foodItemDto.setName("Idli");
        foodItemDto.setPrice(PRICE);
        foodItemDto.setCreated(FIXED_INSTANT);
        foodItemDto.setModified(FIXED_INSTANT);
        // when
        when(foodItemMapper.foodItemToFoodItemDto(any(FoodItem.class))).thenReturn(foodItemDto);
        when(foodItemMapper.foodItemDtoToFoodItem(any(FoodItemDto.class))).thenReturn(updateFoodItem);
        when(foodItemRepository.findById(ID)).thenReturn(Optional.of(existingFoodItem));
        when(foodItemRepository.save(any(FoodItem.class))).thenReturn(savedFoodItem);
        FoodItemDto updatedFoodItem = foodItemService.updateFoodItem(ID, updateFoodItemDto);

        // then
        Assertions.assertThat(updatedFoodItem.getId()).isEqualTo(ID);
        Assertions.assertThat(updatedFoodItem.getName()).isEqualTo("Idli");
        log.info("Leaving givenFoodItemDto_WhenUpdateFoodItem_ThenReturnFoodItemDto() Test");
    }

    @Test
    @Tag("updateFoodItem")
    void givenFoodItemDtoWithNewPrice_WhenUpdateFoodItem_ThenReturnFoodItemDto() throws FoodItemException {
        log.info("Entering givenFoodItemPriceInDto_WhenUpdateFoodItem_ThenReturnFoodItemDto() Test");
        // given
        double newPrice = 15.0;
        UpdateFoodItemDto updateFoodItemDto = new UpdateFoodItemDto();
        updateFoodItemDto.setPrice(newPrice);

        FoodItem updateFoodItem = new FoodItem();
        updateFoodItem.setPrice(newPrice);

        FoodItem savedFoodItem = new FoodItem();
        savedFoodItem.setId(ID);
        savedFoodItem.setName(FOOD_ITEM_NAME);
        savedFoodItem.setPrice(newPrice);
        savedFoodItem.setCreated(FIXED_INSTANT);
        savedFoodItem.setModified(FIXED_INSTANT);

        FoodItem existingFoodItem = new FoodItem();
        existingFoodItem.setId(ID);
        existingFoodItem.setName(FOOD_ITEM_NAME);
        existingFoodItem.setPrice(PRICE);
        existingFoodItem.setCreated(FIXED_INSTANT);
        existingFoodItem.setModified(FIXED_INSTANT);

        FoodItemDto foodItemDto = new FoodItemDto();
        foodItemDto.setId(ID);
        foodItemDto.setName(FOOD_ITEM_NAME);
        foodItemDto.setPrice(newPrice);
        foodItemDto.setCreated(FIXED_INSTANT);
        foodItemDto.setModified(FIXED_INSTANT);
        // when
        when(foodItemMapper.foodItemToFoodItemDto(any(FoodItem.class))).thenReturn(foodItemDto);
        when(foodItemMapper.foodItemDtoToFoodItem(any(FoodItemDto.class))).thenReturn(updateFoodItem);
        when(foodItemRepository.findById(ID)).thenReturn(Optional.of(existingFoodItem));
        when(foodItemRepository.save(any(FoodItem.class))).thenReturn(savedFoodItem);
        FoodItemDto updatedFoodItem = foodItemService.updateFoodItem(ID, updateFoodItemDto);

        // then
        Assertions.assertThat(updatedFoodItem.getId()).isEqualTo(ID);
        Assertions.assertThat(updatedFoodItem.getName()).isEqualTo(FOOD_ITEM_NAME);
        Assertions.assertThat(updatedFoodItem.getPrice()).isEqualTo(newPrice);

        log.info("Leaving givenFoodItemPriceInDto_WhenUpdateFoodItem_ThenReturnFoodItemDto() Test");
    }

    @Test
    @Tag("deleteFoodItem")
    void givenInvalidFoodItemId_WhenDeleteFoodItem_ThenTrowsException() {
        log.info("Entering givenInvalidFoodItemId_WhenDeleteFoodItem_ThenTrowsException() Test");

        // given ID
        // when
        when(foodItemRepository.findById(ID)).thenReturn(Optional.empty());

        // then
        Assertions.assertThatThrownBy(() -> foodItemService.deleteFoodItem(ID))
                .isInstanceOf(FoodItemException.class)
                .hasMessage("Food item not found for id: " + ID);
        log.info("Leaving givenInvalidFoodItemId_WhenDeleteFoodItem_ThenTrowsException() Test");
    }

    @Test
    @Tag("deleteFoodItem")
    void givenDeleteFoodItemRequest_WhenDeleteFoodItem_ThenFoodItemDeleted() throws FoodItemException {
        log.info("Entering givenDeleteFoodItemRequest_WhenDeleteFoodItem_ThenFoodItemDeleted() Test");

        // given
        FoodItem foodItem = new FoodItem();
        foodItem.setId(ID);
        foodItem.setName(FOOD_ITEM_NAME);
        foodItem.setPrice(PRICE);
        foodItem.setCreated(FIXED_INSTANT);
        foodItem.setModified(FIXED_INSTANT);

        // when
        when(foodItemRepository.findById(ID)).thenReturn(Optional.of(foodItem));
        doNothing().when(foodItemRepository).deleteById(anyLong());
        foodItemService.deleteFoodItem(ID);

        // then
        verify(foodItemRepository, times(1)).deleteById(ID);
        log.info("Leaving givenDeleteFoodItemRequest_WhenDeleteFoodItem_ThenFoodItemDeleted() Test");
    }
}