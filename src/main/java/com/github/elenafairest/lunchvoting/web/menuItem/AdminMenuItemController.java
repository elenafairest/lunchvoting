package com.github.elenafairest.lunchvoting.web.menuItem;

import com.github.elenafairest.lunchvoting.model.MenuItem;
import com.github.elenafairest.lunchvoting.repository.MenuItemRepository;
import com.github.elenafairest.lunchvoting.service.MenuItemService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static com.github.elenafairest.lunchvoting.util.validation.ValidationUtil.assureIdConsistent;
import static com.github.elenafairest.lunchvoting.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminMenuItemController.URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class AdminMenuItemController {
    static final String URL = "/api/admin/restaurants";
    private final MenuItemRepository repository;

    private final MenuItemService service;

    @GetMapping("/{restaurantId}/menu-items/{id}")
    public ResponseEntity<MenuItem> get(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("get menu item {} from restaurant {}", id, restaurantId);
        return ResponseEntity.of(repository.get(id, restaurantId));
    }

    @DeleteMapping("/{restaurantId}/menu-items/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = "restaurantsWithMenu", allEntries = true)
    public void delete(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("delete menu item {}", id);
        MenuItem menuItem = repository.checkBelong(id, restaurantId);
        repository.delete(menuItem);
    }

    @GetMapping("/{restaurantId}/menu-items")
    public List<MenuItem> getAll(@PathVariable int restaurantId) {
        log.info("getAll menu items for restaurant {}", restaurantId);
        return repository.getAll(restaurantId);
    }


    @PutMapping(value = "/{restaurantId}/menu-items/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @CacheEvict(value = "restaurantsWithMenu", allEntries = true)
    public void update(@Valid @RequestBody MenuItem menuItem, @PathVariable int restaurantId, @PathVariable int id) {
        log.info("update menu item {} for restaurant {}", id, restaurantId);
        assureIdConsistent(menuItem, id);
        repository.checkBelong(id, restaurantId);
        service.save(menuItem, restaurantId);
    }

    @PostMapping(value = "/{restaurantId}/menu-items", consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(value = "restaurantsWithMenu", allEntries = true)
    public ResponseEntity<MenuItem> createWithLocation(@Valid @RequestBody MenuItem menuItem, @PathVariable int restaurantId) {
        log.info("create new menu item for restaurant {}", restaurantId);
        checkNew(menuItem);
        MenuItem created = service.save(menuItem, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping("/{restaurantId}/menu-items/by-date")
    public List<MenuItem> getByDate(@PathVariable Integer restaurantId,
                                    @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate menuDate) {
        log.info("getAll menu items for restaurant {} for date {}", restaurantId, menuDate);
        return repository.getByDate(restaurantId, menuDate);
    }
}
