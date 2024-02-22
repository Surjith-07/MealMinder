import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Initialize the MenuService with a capacity of 100 items per cache
        MenuService menuService = new MenuService(100);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Menu Management System!");

        while (true) {
            System.out.println("\nEnter your choice:");
            System.out.println("1) Add a new menu item");
            System.out.println("2) Get a menu item");
            System.out.println("3) View menu items by mealtime");
            System.out.println("4) View menu items by category");
            System.out.println("5) View about the Cache");
            System.out.println("6) Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addMenuItem(menuService, scanner);
                    break;
                case 2:
                    getMenuItem(menuService, scanner);
                    break;
                case 3:
                    viewMenuByMealtime(menuService, scanner);
                    break;
                case 4:
                    viewMenuByCategory(menuService, scanner);
                    break;
                case 5:
                    CacheAction(menuService,scanner);
                    break;
                case 6:
                    System.out.println("Exiting...!!!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    // Function to handle cache-related actions
    private static void CacheAction(MenuService menuService, Scanner scanner) {
        while (true) {
            // Display cache action options
            System.out.println("\nEnter your choice:");
            System.out.println("a) Get a MenuItem in Cache");
            System.out.println("b) View menu in Cache");
            System.out.println("c) Return to main menu");
    
            String choice = scanner.nextLine().toLowerCase();
    
            switch (choice) {
                case "a":
                    getMenuItemInCache(menuService, scanner);
                    break;
                case "b":
                    viewMenuItemsInCache(menuService,scanner);
                    break;
                case "c":
                    return; 
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    // Function to get a MenuItem from cache
    private static void getMenuItemInCache(MenuService menuService, Scanner scanner) {
        System.out.println("Enter meal type (breakfast, lunch, dinner, snacks):");
        String mealType = scanner.nextLine();
        System.out.println("Enter item name:");
        String itemName = scanner.nextLine();
    
        MenuItem menuItem = menuService.getMenuItem(mealType, itemName);
        if (menuItem != null) {
            System.out.println("Found menu item in Cache:\nItem\tCategory" + menuItem);
        } else {
            System.out.println("Menu item not found in Cache.");
        }
    }
    
    // Function to view menu items in cache
    private static void viewMenuItemsInCache(MenuService menuService,Scanner scanner) {
        System.out.println("Enter meal type (breakfast, lunch, dinner, snacks):");
        String mealType = scanner.nextLine();
        menuService.printMenuItems(mealType);
    }
    
    // Function to add a new menu item
    private static void addMenuItem(MenuService menuService, Scanner scanner) {
        System.out.println("Enter meal type (breakfast, lunch, dinner, snacks):");
        String mealType = scanner.nextLine();
        System.out.println("Enter item name:");
        String itemName = scanner.nextLine();
        System.out.println("Enter item category (starters, main course, desserts, beverages):");
        String itemCategory = scanner.nextLine();

        menuService.addMenuItem(mealType, itemName, itemCategory);
    }

    // Function to get a menu item
    private static void getMenuItem(MenuService menuService, Scanner scanner) {
        System.out.println("Enter meal type (breakfast, lunch, dinner, snacks):");
        String mealType = scanner.nextLine();
        System.out.println("Enter item name:");
        String itemName = scanner.nextLine();
        System.out.println("MealItem\t");
        MenuItem menuItem = menuService.getMenuItem(mealType, itemName);
        if (menuItem != null) {
            System.out.println("Found menu item:\n Item\tCategory" + menuItem);
        } else {
            System.out.println("Menu item not found.");
        }
    }

    // Function to view menu items by mealtime
    private static void viewMenuByMealtime(MenuService menuService, Scanner scanner) {
        System.out.println("Enter mealtime:");
        String mealtime = scanner.nextLine();
        menuService.printMenu(mealtime);
    }

    // Function to view menu items by category
    private static void viewMenuByCategory(MenuService menuService, Scanner scanner) {
        System.out.println("Enter category:");
        String category = scanner.nextLine();
        menuService.printMenuForCategory(category);
    }
}
