package MeSaaServices.demo.service;
import MeSaaServices.demo.model.dtos.MenuDTO;
import MeSaaServices.demo.model.enrichements.EnrichedMenu;
import MeSaaServices.demo.model.entities.Food;
import MeSaaServices.demo.model.entities.Menu;
import MeSaaServices.demo.model.repositories.FoodRepository;
import MeSaaServices.demo.model.repositories.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private FoodRepository foodRepository;

    // Obtener todos los menús enriquecidos (con lista de Food)
    public List<EnrichedMenu> getAllMenus() {
        List<Menu> menus = menuRepository.findAll();
        return convertToEnrichedMenus(menus);
    }

    // Obtener un menú enriquecido por su ID
    public EnrichedMenu getMenuById(String menuId) {
        Optional<Menu> menuOptional = menuRepository.findById(menuId);
        if (menuOptional.isPresent()) {
            Menu menu = menuOptional.get();
            return convertToEnrichedMenu(menu);
        }
        return null;
    }

    // Crear un menú a partir de un MenuDTO y devolver un EnrichedMenu
    public EnrichedMenu createMenu(MenuDTO menuDTO) {
        Menu menu = new Menu();
        menu.setName(menuDTO.getName());
        menu.setDescription(menuDTO.getDescription());
        menu = menuRepository.save(menu);
        return convertToEnrichedMenu(menu);
    }

    // Actualizar un menú
    public EnrichedMenu updateMenu(String menuId, MenuDTO menuDTO) {
        Optional<Menu> existingMenuOptional = menuRepository.findById(menuId);
        if (existingMenuOptional.isPresent()) {
            Menu existingMenu = existingMenuOptional.get();
            existingMenu.setName(menuDTO.getName());
            existingMenu.setDescription(menuDTO.getDescription());
            existingMenu = menuRepository.save(existingMenu);
            return convertToEnrichedMenu(existingMenu);
        }
        return null;
    }

    // Eliminar un menú
    public boolean deleteMenu(String menuId) {
        if (menuRepository.existsById(menuId)) {
            menuRepository.deleteById(menuId);
            return true;
        }
        return false;
    }

    // Método para convertir un Menu a EnrichedMenu
    private EnrichedMenu convertToEnrichedMenu(Menu menu) {
        List<Food> foods = foodRepository.findByMenu(menu.getId());
        EnrichedMenu enrichedMenu = new EnrichedMenu();
        enrichedMenu.setId(menu.getId());
        enrichedMenu.setName(menu.getName());
        enrichedMenu.setDescription(menu.getDescription());
        enrichedMenu.setFoods(foods);
        return enrichedMenu;
    }

    // Método para convertir una lista de Menu a EnrichedMenu
    private List<EnrichedMenu> convertToEnrichedMenus(List<Menu> menus) {
        return menus.stream()
                .map(this::convertToEnrichedMenu)
                .toList();
    }
}