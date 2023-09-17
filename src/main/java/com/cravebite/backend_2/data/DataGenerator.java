package com.cravebite.backend_2.data;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
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
                User user1 = createUser("john", "password");
                User user2 = createUser("prince", "password");
                User user3 = createUser("jade", "password");
                User user4 = createUser("jade", "password");
                User user5 = createUser("jade", "password");
                User user6 = createUser("jade", "password");
                User user7 = createUser("jade", "password");
                User user8 = createUser("jade", "password");
                User user9 = createUser("jade", "password");
                User user10 = createUser("jade", "password");

                userRepository.save(user1);
                userRepository.save(user2);
                userRepository.save(user3);
                userRepository.save(user4);
                userRepository.save(user5);
                userRepository.save(user6);
                userRepository.save(user7);
                userRepository.save(user8);
                userRepository.save(user9);
                userRepository.save(user10);

                // Create a new RestaurantOwner and persist
                RestaurantOwner restaurantOwner1 = new RestaurantOwner();
                restaurantOwner1.setUser(user1);
                restaurantOwnerRepository.save(restaurantOwner1);

                RestaurantOwner restaurantOwner2 = new RestaurantOwner();
                restaurantOwner2.setUser(user2);
                restaurantOwnerRepository.save(restaurantOwner2);

                RestaurantOwner restaurantOwner3 = new RestaurantOwner();
                restaurantOwner3.setUser(user3);
                restaurantOwnerRepository.save(restaurantOwner3);

                RestaurantOwner restaurantOwner4 = new RestaurantOwner();
                restaurantOwner4.setUser(user4);
                restaurantOwnerRepository.save(restaurantOwner4);

                RestaurantOwner restaurantOwner5 = new RestaurantOwner();
                restaurantOwner5.setUser(user5);
                restaurantOwnerRepository.save(restaurantOwner5);

                RestaurantOwner restaurantOwner6 = new RestaurantOwner();
                restaurantOwner6.setUser(user6);
                restaurantOwnerRepository.save(restaurantOwner6);

                // Create restaurants in Oulu
                Restaurant restaurant1 = createRestaurant("Restaurant 1", "Oulu Center Address 1", "Postal Code 1",
                                "Oulu", 65.0121, 25.4651, restaurantOwner1);
                restaurantRepository.save(restaurant1);

                Restaurant restaurant2 = createRestaurant("Restaurant 2", "Oulu Center Address 2", "Postal Code 2",
                                "Oulu", 65.0222, 25.4752, restaurantOwner1);
                restaurantRepository.save(restaurant2);

                Restaurant restaurant3 = createRestaurant("Restaurant 3", "Oulu Center Address 3", "Postal Code 3",
                                "Oulu", 65.0323, 25.4853, restaurantOwner2);
                restaurantRepository.save(restaurant3);

                Restaurant restaurant4 = createRestaurant("Restaurant 4", "Oulu Center Address 4", "Postal Code 4",
                                "Oulu", 65.0424, 25.4954, restaurantOwner2);
                restaurantRepository.save(restaurant4);

                Restaurant restaurant5 = createRestaurant("Restaurant 5", "Oulu Center Address 5", "Postal Code 5",
                                "Oulu", 65.0525, 25.5055, restaurantOwner2);
                restaurantRepository.save(restaurant5);

                Restaurant restaurant6 = createRestaurant("Restaurant 6", "Oulu Center Address 6", "Postal Code 6",
                                "Oulu", 65.0626, 25.5156, restaurantOwner3);
                restaurantRepository.save(restaurant6);

                Restaurant restaurant7 = createRestaurant("Restaurant 7", "Oulu Center Address 7", "Postal Code 7",
                                "Oulu", 65.0727, 25.5257, restaurantOwner3);
                restaurantRepository.save(restaurant7);

                Restaurant restaurant8 = createRestaurant("Restaurant 8", "Oulu Center Address 8", "Postal Code 8",
                                "Oulu", 65.0828, 25.5358, restaurantOwner3);
                restaurantRepository.save(restaurant8);

                Restaurant restaurant9 = createRestaurant("Restaurant 9", "Oulu Center Address 9", "Postal Code 9",
                                "Oulu", 65.0929, 25.5459, restaurantOwner4);
                restaurantRepository.save(restaurant9);

                Restaurant restaurant10 = createRestaurant("Restaurant 10", "Oulu Center Address 10", "Postal Code 10",
                                "Oulu", 65.1030, 25.5560, restaurantOwner4);
                restaurantRepository.save(restaurant10);

                // Create restaurants outside Oulu
                Restaurant restaurant11 = createRestaurant("Pudasjärvi Pizza", "Pudasjärvi Address", "93100",
                                "Pudasjärvi", 65.3666, 26.9625, restaurantOwner2);
                restaurantRepository.save(restaurant11);

                Restaurant restaurant12 = createRestaurant("Vaala Veggie", "Vaala Address", "91710", "Vaala", 64.5585,
                                26.7516, restaurantOwner5);
                restaurantRepository.save(restaurant12);

                Restaurant restaurant13 = createRestaurant("Kärsämäki Kebab", "Kärsämäki Address", "86710", "Kärsämäki",
                                63.9850, 25.7570, restaurantOwner5);
                restaurantRepository.save(restaurant13);

                Restaurant restaurant14 = createRestaurant("Pyhäntä Pies", "Pyhäntä Address", "93280", "Pyhäntä",
                                64.2789, 26.0194, restaurantOwner6);
                restaurantRepository.save(restaurant14);

                Restaurant restaurant15 = createRestaurant("Paltamo Pasta", "Paltamo Address", "88300", "Paltamo",
                                64.4030, 27.7294, restaurantOwner6);
                restaurantRepository.save(restaurant15);

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
                // restaurant.setLatitude(latitude);
                // restaurant.setLongitude(longitude);
                restaurant.setRestaurantOwner(owner);

                Point location = new GeometryFactory().createPoint(new Coordinate(latitude, longitude));
                restaurant.setRestaurantPoint(location);

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
