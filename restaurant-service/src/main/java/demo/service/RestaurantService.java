package demo.service;


import demo.model.Restaurant;

public interface RestaurantService {
    void createRestaurant(Restaurant restaurant);
    Restaurant getRestaurantByName(String name);
}
