import java.util.HashSet;
import java.util.Set;

/**
 * Created by Qi_He on Mar/24/16.
 */
public interface Vector {

    /**
     * @return id of the object. I assume all id can be represented by id.
     */
    public String getID();

    /**
     * @param v Use Vector as key to look up the rating data
     * @return T rating data, type of data undetermined
     */
    public Double getDataPoint(Vector v);

    /**
     * @return return the average data (ratings) of all the fields in the vector
     */
    public Double getAverage();

    /**
     * Forgot to include this is my UML. Just a helper to fill the HashMap
     * @param item item to insert into map as key
     * @param data data as value
     */
    public void addVector(Vector item, Double data);

    /**
     * Forgot to include this is my UML. Just a helper to traverse the HashMap
     * @return keySet
     */
    public Set<Vector> getKeySet();

}
