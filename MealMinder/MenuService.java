import java.util.*;

class MenuService {
    private final LRUCache breakfastCache;
    private final LRUCache lunchCache;
    private final LRUCache dinnerCache;
    private final LRUCache snacksCache;
    private final int maxSize;
    private final Map<String, Map<String, MenuItem>> mealtimeCache;
    private final Map<String, List<MenuItem>> categoryCache;

    // Constructor to initialize caches
    public MenuService(int maxSize) {
        this.maxSize = maxSize;
        breakfastCache = new LRUCache(maxSize);//each cache have the 100 size of memory 
                                            //whenever it will getting accessed or new thing added on it will change Cache Order Based On LRU[Least Recently Used]
        lunchCache = new LRUCache(maxSize);
        dinnerCache = new LRUCache(maxSize);
        snacksCache = new LRUCache(maxSize);
        mealtimeCache = new HashMap<>();
        categoryCache = new HashMap<>();
        initializeCache(); // Initialize mealtime and category caches
    }

    // Method to initialize mealtime and category caches
    public void initializeCache() {
        // Initialize mealtime cache for each meal type
        mealtimeCache.put("breakfast", new LinkedHashMap<>());
        mealtimeCache.put("lunch", new LinkedHashMap<>());
        mealtimeCache.put("dinner", new LinkedHashMap<>());
        mealtimeCache.put("snacks", new LinkedHashMap<>());

        // Initialize category cache for each category
        categoryCache.put("starters", new ArrayList<>());
        categoryCache.put("main course", new ArrayList<>());
        categoryCache.put("desserts", new ArrayList<>());
        categoryCache.put("beverages", new ArrayList<>());
    }

    // Method to add a menu item to the cache
    public void addMenuItem(String meal, String name, String category) {
        // Get the cache corresponding to the meal type
        LRUCache cache = getCacheForMeal(meal);
        if (cache != null) {
            // Create a new MenuItem object
            MenuItem menuItem = new MenuItem(name, category);
            // Put the item in the cache
            cache.put(name, menuItem);
            // Add the item to mealtime cache
            addToMealtimeCache(meal, name, menuItem);
            // Add the item to category cache
            addToCategoryCache(category, menuItem);
            System.out.println("Item added Successfully...!!!");
        } else {
            System.out.println("Invalid meal type. Please try again.");
        }
    }

    // Method to add an item to the mealtime cache
    private void addToMealtimeCache(String mealtime, String name, MenuItem menuItem) {
        mealtimeCache.computeIfAbsent(mealtime, k -> new LinkedHashMap<>()).put(name, menuItem);
    }

    // Method to add an item to the category cache
    private void addToCategoryCache(String category, MenuItem menuItem) {
        categoryCache.computeIfAbsent(category, k -> new ArrayList<>()).add(menuItem);
    }

    // Method to get menu items by mealtime
    public List<MenuItem> getMenuItemsByMealtime(String mealtime) {
        return new ArrayList<>(mealtimeCache.getOrDefault(mealtime, Collections.emptyMap()).values());
    }

    // Method to get menu items by mealtime and category
    public List<MenuItem> getMenuItemsByMealtimeAndCategory(String mealtime, String category) {
        List<MenuItem> items = new ArrayList<>();
        for (MenuItem item : getMenuItemsByMealtime(mealtime)) {
            if (item.getCategory().equalsIgnoreCase(category)) {
                items.add(item);
            }
        }
        return items;
    }

    // Method to print menu for a specific mealtime
    public void printMenu(String mealtime) {
        if (!mealtimeCache.containsKey(mealtime)) {
            throw new IllegalArgumentException("Invalid mealtime: " + mealtime);
        }
        System.out.println("Menu for " + mealtime + ":\nItems\tCategories");
        Map<String, MenuItem> mealtimeMenu = mealtimeCache.get(mealtime);
        for (Map.Entry<String, MenuItem> entry : mealtimeMenu.entrySet()) {
            MenuItem menuItem = entry.getValue();
            System.out.println(menuItem.getName() + "\t[" + menuItem.getCategory() + "]");
        }
    }

    // Method to print menu for a specific category
    public void printMenuForCategory(String category) {
        if (!categoryCache.containsKey(category)) {
            throw new IllegalArgumentException("Invalid category: " + category);
        }
        System.out.println("Menu for " + category + ":\nItems");
        List<MenuItem> categoryMenu = categoryCache.get(category);
        for (MenuItem menuItem : categoryMenu) {
            System.out.println(menuItem.getName());
        }
    }

    // Method to get a menu item from cache
    public MenuItem getMenuItem(String meal, String name) {
        LRUCache cache = getCacheForMeal(meal);
        if (cache != null) {
            return cache.get(name);
        } else {
            System.out.println("Invalid meal type. Please try again.");
            return null;
        }
    }

    // Method to remove a menu item from cache
    public void removeMenuItem(String meal, String name) {
        LRUCache cache = getCacheForMeal(meal);
        if (cache != null) {
            cache.remove(name);
        } else {
            System.out.println("Invalid meal type. Please try again.");
        }
    }

    // Method to print menu items for a specific meal
    public void printMenuItems(String meal) {
        LRUCache cache = getCacheForMeal(meal);
        if (cache != null) {
            System.out.println(meal.toUpperCase() + " MENU:\n" + "Items\tCategories");
            for (MenuItem item : cache.getItems().values()) {
                System.out.println(item);
            }
        }
    }

    // Method to get the cache for a specific meal
    private LRUCache getCacheForMeal(String meal) {
        switch (meal.toLowerCase()) {
            case "breakfast":
                return breakfastCache;
            case "lunch":
                return lunchCache;
            case "dinner":
                return dinnerCache;
            case "snacks":
                return snacksCache;
            default:
                return null;
        }
    }
}
