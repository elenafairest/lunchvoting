package ru.javaops.lunchvoting.web.menuItem;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.lunchvoting.model.MenuItem;
import ru.javaops.lunchvoting.repository.MenuItemRepository;
import ru.javaops.lunchvoting.service.MenuItemService;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.javaops.lunchvoting.util.validation.ValidationUtil.assureIdConsistent;
import static ru.javaops.lunchvoting.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminMenuItemController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class AdminMenuItemController {
    static final String REST_URL = "/api/admin/menuItems";
    private final MenuItemRepository repository;

    private final MenuItemService service;

    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> get(@PathVariable int id) {
        log.info("get menu item {}", id);
        return ResponseEntity.of(repository.findById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete menu item {}", id);
        repository.deleteExisted(id);
    }

    @GetMapping
    public List<MenuItem> getAll() {
        log.info("getAll menu items");
        return repository.findAll();
    }


    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody MenuItem menuItem, @PathVariable int id, @RequestParam int restaurantId) {
        log.info("update menu item {}", id);
        assureIdConsistent(menuItem, id);
        repository.checkBelong(id, restaurantId);
        service.save(menuItem, restaurantId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuItem> createWithLocation(@Valid @RequestBody MenuItem menuItem, @RequestParam int restaurantId) {
        log.info("create new menu item");
        checkNew(menuItem);
        MenuItem created = service.save(menuItem, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping("/filter")
    public List<MenuItem> getFiltered(@RequestParam @Nullable Integer restaurantId,
                                      @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("getAll menu items for restaurant {} for date {}", restaurantId, date);
        return repository.getFiltered(restaurantId, date);
    }
}
