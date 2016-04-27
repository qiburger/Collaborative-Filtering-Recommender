import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 *  Implementation of Vector, representing movies in this assignment
 *  It also keeps track of all users who have rated this movie
 *  See readme for why I implemented this along with User.
 * Created by Qi_He on Mar/24/16.
 */
public class Item implements Vector {

    private String id;
    private HashMap<Vector, Double> userAndRating;
    private int count;
    private Double total;
    private Double averageRating;

    /**
     * Constructor creates an item and immediately calculates the average
     * Since we are not allowing users to change ratings, we just construct here.
     * @param id id of item
     * @param userAndRating mapping of users to their ratings
     */
    public Item(String id, HashMap<Vector, Double> userAndRating){
        this.id = id;
        this.userAndRating = userAndRating;

        total = 0.0;
        count = 0;

        for (HashMap.Entry<Vector, Double> entry : this.userAndRating.entrySet()) {
            count++;
            total += entry.getValue();
        }

        if (count == 0) averageRating = 0.0;
        else averageRating = total / count;
    }

    /**
     * @return id of the item.
     */
    @Override
    public String getID() {
        return id;
    }

    /**
     * @param v Use Vector as key to look up the given user's rating data
     * @return Double rating data
     */
    @Override
    public Double getDataPoint(Vector v) {
        return userAndRating.get(v);
    }

    /**
     * Return the average rating of item's history
     * @return average rating of this item
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
    @Override
    public void addVector(Vector item, Double data){
        userAndRating.put(item, data);
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
        return userAndRating.keySet();
    }



}
