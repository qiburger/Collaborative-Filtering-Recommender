/**
 * Created by Qi_He on Apr/4/16.
 */
public interface SimilarityCalculator {

    /**
     * Implements similarity of two vectors
     * @param v1 vector 1
     * @param v2 vector 2
     * @return Double similarity
     */
    public Double getSimilarity(Vector v1, Vector v2);

}
