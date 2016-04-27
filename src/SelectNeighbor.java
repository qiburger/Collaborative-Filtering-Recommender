import java.util.*;

/**
 * Helps handle the neighbor selection process,
 * when given an item, an user and similarity function
 * Created by Qi_He on Mar/25/16.
 */
public class SelectNeighbor {

    private int neighborSize = 20;

    /**
     * Just default constructor. Left empty for future changes
     */
    public SelectNeighbor(){
    }

    /**
     * Key function - returns a set of Vectors as neighbors, when given these below:
     * @param item Here it's a movie, which contains all users who rated it
     * @param sc Similarity function
     * @param user User in question
     * @return a set of Vectors as neighbors
     */
    public HashSet<Vector> getNeighbors(Vector item, SimilarityCalculator sc, Vector user){
        HashSet<Vector> neighbors = new HashSet<>();
        HashMap<Vector, Double> similarityMap = new HashMap<>();

        int tempSize = Math.min(item.getKeySet().size(), neighborSize);

        for (Vector viewer : item.getKeySet()){

            // Make sure to remove user itself from the neighbor, if it has already rated it
            if (!Objects.equals(viewer.getID(), user.getID())) {
                Double similarity = sc.getSimilarity(user, viewer);
                similarityMap.put(viewer, similarity);
            }else{
                tempSize--;
            }
        }

        // Sorting top neighbors
        List<HashMap.Entry<Vector, Double>> list = new LinkedList<>(similarityMap.entrySet());
        Collections.sort(list, (o1, o2) -> (o2.getValue()).compareTo( o1.getValue() ));


        for (int i = 0; i < tempSize; i++){
            Vector temp = list.get(i).getKey();
            neighbors.add(temp);
        }

        return neighbors;
    }

    /**
     * In case the prediction class needs to change the neighbor size in the future.
     * Here default is 20;
     * @param k new size
     */
    public void setK(int k){
        neighborSize = k;
    }




}
