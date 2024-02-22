import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class LRUCache {
    private int capacity;
    private Map<String, MenuItem> map; // Map to store items
    private Map<String, Long> accessTimeMap; // Map to track access time of items
    
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new LinkedHashMap<>(); // LinkedHashMap maintains insertion order
        this.accessTimeMap = new LinkedHashMap<>(); // LinkedHashMap maintains insertion order of access time

        // Schedule periodic eviction of expired entries[every 6 hrs to check]
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::evictExpiredEntries, 6, 6, TimeUnit.HOURS); // Schedule eviction every 6 hours
    }

    // Get an item from the cache 
    public MenuItem get(String key) {
        if (map.containsKey(key)) {
            // If the item exists, update its access time and return it
            MenuItem val = map.remove(key);
            map.put(key, val); // Move the item to the end to indicate recent access
            accessTimeMap.put(key, System.currentTimeMillis()); // Update access time
            return val;
        } else {
            // If the item doesn't exist, return null
            return null;
        }
    }

    // Put an item into the cache
    public void put(String key, MenuItem value) {
        if (map.containsKey(key)) {
            // If the item already exists, remove it first
            map.remove(key);
        } else if (map.size() >= capacity) {
            // If the cache is full, remove the least recently used item
            Iterator<Map.Entry<String, MenuItem>> iterator = map.entrySet().iterator();
            iterator.next(); // Get the first entry, which is the least recently used
            iterator.remove(); // Remove it
        }
        // Put the new item into the cache and update its access time
        map.put(key, value);
        accessTimeMap.put(key, System.currentTimeMillis()); // Update access time
    }

    // Remove an item from the cache by key
    public void remove(String key) {
        map.remove(key);
        accessTimeMap.remove(key);
    }

    // Evict expired entries from the cache
    private void evictExpiredEntries() {
        long currentTime = System.currentTimeMillis();
        Iterator<Map.Entry<String, Long>> iterator = accessTimeMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Long> entry = iterator.next();
            long accessTime = entry.getValue();
            if (currentTime - accessTime > 6 * 360000) { // 6 hours in milliseconds
                // If an entry is expired, remove it from both maps
                map.remove(entry.getKey());
                iterator.remove();
            } else {
                // Stop evicting once we reach a non-expired entry
                break;
            }
        }
    }

    // Get all items from the cache
    public Map<String, MenuItem> getItems() {
        return map;
    }
}
