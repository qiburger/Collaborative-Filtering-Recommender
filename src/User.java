import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * User class also implements Vector
 *  It also keeps track of all movies who the user have rated
 * Created by Qi_He on Mar/24/16.
 */
public class User implements Vector{

    private String id;
    private HashMap<Vector, Double> itemAndRating;
    private int count;
    private Double total;
    private Double averageRating;

    /**
     * Constructor creates an user and immediately calculates the average
     * Since we are not allowing users to change ratings, we just construct here.
     * @param id id of user
     * @param itemAndRating mapping of items and ratings
     */
    public User(String id, HashMap<Vector, Double> itemAndRating){
        this.id = id;
        this.itemAndRating = itemAndRating;

        total = 0.0;
        count = 0;

        for (HashMap.Entry<Vector, Double> entry : this.itemAndRating.entrySet()) {
            count++;
            total += entry.getValue();
        }

        if (count == 0) averageRating = 0.0;
        else averageRating = total / count;
    }

    /**
     * @return id of the user.
     */
    @Override
    public String getID() {
        return id;
    }

    /**
     * @param v Use Vector as key to look up the rating data
     * @return T rating data
     */
    @Override
    public Double getDataPoint(Vector v) {
        return itemAndRating.get(v);
    }

    /**
     * Return the average rating of this item
     * @return average rating
     */
    @Override
    public Double getAverage() {
        return averageRating;
    }

//    /**
//     * Helper method to abstract conversion from generic input to Double
//     * @param data generic input
//     * @return converted Double value
//     */
//    private Double dataHelper(T data){
//        return (Double) data;
//    }

    /**
     * Forgot to include this is my UML. Just a helper to fill the HashMap
     * @param item Key in HashMap
     * @param data Rating/Value in HashMap
     */
    public void addVector(Vector item, Double data){
        itemAndRating.put(item, data);
        count++;
        total += data;
        averageRating = total/count;
    }

    /**
     * Also forgot this. Just a helper to traverse the HashMap
     * @return keySet
     */
    @Override
    public Set<Vector> getKeySet() {
        return itemAndRating.keySet();
    }


}
