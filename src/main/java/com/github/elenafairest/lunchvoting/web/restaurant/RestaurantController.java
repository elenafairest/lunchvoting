package com.github.elenafairest.lunchvoting.web.restaurant;

import com.github.elenafairest.lunchvoting.model.Restaurant;
import com.github.elenafairest.lunchvoting.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = RestaurantController.URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class RestaurantController {
    static final String URL = "/api/restaurants";

    private final RestaurantRepository repository;

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> get(@PathVariable int id) {
        log.info("get restaurant {}", id);
        return ResponseEntity.of(repository.findById(id));
    }

    @GetMapping("/with-menu/{id}")
    public ResponseEntity<Restaurant> getWithMenu(@PathVariable int id) {
        log.info("get restaurant {} with menu", id);
        return ResponseEntity.of(repository.getWithMenu(id));
    }

    @GetMapping("/with-menu")
    @Cacheable("restaurantsWithMenu")
    public List<Restaurant> getAllWithMenu() {
        log.info("get restaurants with menu");
        return repository.getAllWithMenu();
    }

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("get all restaurants");
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }
}
