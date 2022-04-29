import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

class RestaurantTest {
    Restaurant restaurant;
    LocalTime openingTime;
    LocalTime closingTime;
    @BeforeEach
    public void setup() {
        openingTime  = LocalTime.parse("10:30:00");
        closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        Restaurant spy_restaurant = spy(restaurant);

        LocalTime inputTime = LocalTime.parse("13:30:00");
        doReturn(inputTime).when(spy_restaurant).getCurrentTime();

        assertTrue(spy_restaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        Restaurant spy_restaurant = spy(restaurant);

        LocalTime inputTime = LocalTime.parse("07:00:00");
        doReturn(inputTime).when(spy_restaurant).getCurrentTime();

        assertFalse(spy_restaurant.isRestaurantOpen());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    @Test
    public void failed_test_case_for_cost_of_selected_items_if_one_of_the_item_is_not_exist_in_menu() throws itemNotFoundException {
        List<String> selectedItems = new ArrayList<String>();

        selectedItems.add("Sweet corn soup");
        selectedItems.add("Butter Chicken");
        assertThrows(itemNotFoundException.class,
                ()->restaurant.getTotalCostOfSelectedItems(selectedItems));
    }
    @Test
    public void get_total_cost_of_selected_items() throws itemNotFoundException{
        List<Item> selectedItems = restaurant.getMenu();
        List<String> selectedItemsNames = new ArrayList<>();
        for(Item item : selectedItems) {
            selectedItemsNames.add(item.getName());
        }
        int totalCost = restaurant.getTotalCostOfSelectedItems(selectedItemsNames);

        assertEquals(388, totalCost);
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}