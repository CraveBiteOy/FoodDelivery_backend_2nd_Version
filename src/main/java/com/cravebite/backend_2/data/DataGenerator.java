package com.cravebite.backend_2.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cravebite.backend_2.models.entities.MenuItem;
import com.cravebite.backend_2.models.entities.Restaurant;
import com.cravebite.backend_2.models.entities.RestaurantOwner;
import com.cravebite.backend_2.models.entities.User;
import com.cravebite.backend_2.repository.MenuItemRepository;
import com.cravebite.backend_2.repository.RestaurantOwnerRepository;
import com.cravebite.backend_2.repository.RestaurantRepository;
import com.cravebite.backend_2.repository.UserRepository;

import org.springframework.transaction.annotation.Transactional;

@Component
public class DataGenerator {

        @Autowired
        private RestaurantOwnerRepository restaurantOwnerRepository;

        @Autowired
        private RestaurantRepository restaurantRepository;

        @Autowired
        private MenuItemRepository menuItemRepository;

        @Autowired
        private UserRepository userRepository;

        @Transactional
        public void generateData() {

                // Create users
                User user1 = createUser("johnsmith", "password");
                User user2 = createUser("me", "password");

                userRepository.save(user1);
                userRepository.save(user2);

                // Create a new RestaurantOwner and persist
                RestaurantOwner restaurantOwner = new RestaurantOwner();
                restaurantOwner.setUser(user1);
                restaurantOwnerRepository.save(restaurantOwner);

                // Create a new Restaurant and persist
                Restaurant restaurant1 = createRestaurant("John's Pizza", "123 Main St", "12345", "Anytown", 40.7128,
                                74.0060,
                                restaurantOwner);
                restaurantRepository.save(restaurant1);

                // different restaurant
                Restaurant restaurant2 = createRestaurant("Leon's Pizza", "223 Main St", "2222", "Anytown", 41.7128,
                                71.0060,
                                restaurantOwner);
                restaurantRepository.save(restaurant2);

                // Create a new MenuItem and persist for restaurant1
                MenuItem menuItem1 = createMenuItem("Pepperoni Pizza", "Delicious pizza with pepperoni and cheese",
                                10.99, true,
                                restaurant1);
                menuItemRepository.save(menuItem1);

                MenuItem menuItem4 = createMenuItem("biryani", "yummy!", 20.99, true,
                                restaurant1);
                menuItemRepository.save(menuItem4);

                MenuItem menuItem5 = createMenuItem("chiken masala", "yummy!", 22.99, true,
                                restaurant1);
                menuItemRepository.save(menuItem5);

                MenuItem menuItem2 = createMenuItem("Cheese Pizza", "Delicious pizza with cheese", 9.99, true,
                                restaurant1);
                menuItemRepository.save(menuItem2);

                MenuItem menuItem3 = createMenuItem("Pepperoni Pizza", "Delicious pizza with pepperoni and cheese",
                                10.99, true,
                                restaurant2);
                menuItemRepository.save(menuItem3);

        }

        private User createUser(String username, String password) {
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                return user;
        }

        private Restaurant createRestaurant(String name, String address, String zipcode, String city, double latitude,
                        double longitude, RestaurantOwner owner) {
                Restaurant restaurant = new Restaurant();
                restaurant.setName(name);
                restaurant.setAddress(address);
                restaurant.setZipcode(zipcode);
                restaurant.setCity(city);
                restaurant.setLatitude(latitude);
                restaurant.setLongitude(longitude);
                restaurant.setRestaurantOwner(owner);
                return restaurant;
        }

        private MenuItem createMenuItem(String name, String description, double price, boolean isAvailable,
                        Restaurant restaurant) {
                MenuItem menuItem = new MenuItem();
                menuItem.setName(name);
                menuItem.setDescription(description);
                menuItem.setPrice(price);
                menuItem.setIsAvailable(isAvailable);
                menuItem.setRestaurant(restaurant);
                return menuItem;
        }
}
