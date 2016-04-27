import java.util.HashSet;
import java.util.Set;

/**
 * Created by Qi_He on Apr/4/16.
 */
public class CosineSimilarity implements SimilarityCalculator {

    /**
     * Default Constructor, left empty here.
     */
    public CosineSimilarity(){

    }


    /**
     * Implement cosine similarity.
     * @param v1 vector 1
     * @param v2 vector 2
     * @return similarity
     */
    @Override
    public Double getSimilarity(Vector v1, Vector v2) {

        Set<Vector> union = new HashSet<>(v1.getKeySet());

        if (union.isEmpty()) return 0.0;

        union.addAll(v2.getKeySet());

        if (v2.getKeySet().isEmpty()) return 0.0;

        Double nominator = 0.0;
        Double sumDeltaOneSqr = 0.0;
        Double sumDeltaTwoSqr = 0.0;

        for (Vector vector : union) {
            Double deltaOne = v1.getDataPoint(vector);
            if (deltaOne == null){
                deltaOne = 0.0;
            }

            Double deltaTwo = v2.getDataPoint(vector);

            if (deltaTwo == null){
                deltaTwo = 0.0;
            }

            nominator += deltaOne * deltaTwo;

            sumDeltaOneSqr += deltaOne * deltaOne;
            sumDeltaTwoSqr += deltaTwo * deltaTwo;

        }

        if (sumDeltaOneSqr == 0.0 || sumDeltaTwoSqr == 0.0) return 0.0;

        return (nominator / (Math.sqrt(sumDeltaOneSqr) * Math.sqrt(sumDeltaTwoSqr)));
    }
}
