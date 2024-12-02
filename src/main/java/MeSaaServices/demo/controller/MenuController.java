package MeSaaServices.demo.controller;

import MeSaaServices.demo.model.dtos.MenuDTO;
import MeSaaServices.demo.model.enrichements.EnrichedMenu;
import MeSaaServices.demo.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/menus")
public class MenuController {

    @Autowired
    private MenuService menuService;

    // GET /menus -> List of enriched menus (id, name, description, list of foods)
    @GetMapping
    public ResponseEntity<List<EnrichedMenu>> getAllMenus() {
        List<EnrichedMenu> enrichedMenus = menuService.getAllMenus();
        return ResponseEntity.ok(enrichedMenus);
    }

    // GET /menus/{menuId} -> Get a single enriched menu by ID
    @GetMapping("/{menuId}")
    public ResponseEntity<EnrichedMenu> getMenuById(@PathVariable String menuId) {
        EnrichedMenu enrichedMenu = menuService.getMenuById(menuId);
        return enrichedMenu != null
                ? ResponseEntity.ok(enrichedMenu)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // POST /menus -> Create a new menu (expects a MenuDTO as payload)
    @PostMapping
    public ResponseEntity<EnrichedMenu> createMenu(@RequestBody MenuDTO menuDTO) {
        EnrichedMenu createdMenu = menuService.createMenu(menuDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMenu);
    }

    // PUT /menus/{menuId} -> Edit an existing menu (expects a MenuDTO as payload)
    @PutMapping("/{menuId}")
    public ResponseEntity<EnrichedMenu> updateMenu(@PathVariable String menuId, @RequestBody MenuDTO menuDTO) {
        EnrichedMenu updatedMenu = menuService.updateMenu(menuId, menuDTO);
        return updatedMenu != null
                ? ResponseEntity.ok(updatedMenu)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // DELETE /menus/{menuId} -> Delete a menu by its ID
    @DeleteMapping("/{menuId}")
    public ResponseEntity<Void> deleteMenu(@PathVariable String menuId) {
        boolean isDeleted = menuService.deleteMenu(menuId);
        return isDeleted
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
