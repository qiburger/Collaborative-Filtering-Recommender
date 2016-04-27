import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Database that stores collections of vectors
 * Created by Qi_He on Mar/25/16.
 */
public class Database {
    private static Database db = new Database();
    private HashMap<String, Item> items;
    private HashMap<String, User> users;
    private Double average;


    /**
     * Singleton Database design that loads data and stores them.
     */
    private Database(){
    }

    /**
     * @return singleton database object
     */
    public static Database getInstance(){
        return db;
    }

    /**
     * Setter for users
     * @param users HashSet of users object of class User
     */
    public void setUsers(HashMap<String, User> users){
        this.users = users;
    }

    /**
     * Setter fo items
     * @param items HashSet of users object of class Item
     */
    public void setItems(HashMap<String, Item> items){
        this.items = items;
    }

    /**
     * Getter for keySet of users HashMap
     * @return users
     */
    public Set<String> getUserIDSet(){
        return users.keySet();
    }

    /**
     * Getter for keySet of items HashMap
     * @return items
     */
    public Set<String> getItemIDSet(){
        return items.keySet();
    }

    /**
     * Get the user by its id
     * @param id String id
     * @return User
     */
    public User getUser(String id){
        return users.get(id);
    }

    /**
     * Get the item by its id
     * @param id String id
     * @return Item
     */
    public Item getItem(String id){
        return items.get(id);
    }

    /**
     * Set the overall average for baseline predictor
     * @param avg set the average rating
     */
    public void setAverage(Double avg){ this.average = avg;}

    /**
     * Getter for overall average - for baseline predictor
     * @return overall average
     */
    public Double getAverage() {
        return average;
    }
}
