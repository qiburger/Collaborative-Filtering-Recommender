import java.util.HashSet;
import java.util.Set;

/**
 * Created by Qi_He on Mar/25/16.
 */
public class PearsonSimilarity implements SimilarityCalculator {

    /**
     * Constructor
     */
    public PearsonSimilarity(){

    }

    /**
     * Implements Pearson similarity of two vectors
     * @param v1 vector 1
     * @param v2 vector 2
     * @return cos similarity
     */
    public Double getSimilarity(Vector v1, Vector v2){
//        Double v1Average = v1.getAverage();
//        Double v2Average = v2.getAverage();

        Set<Vector> intersection = new HashSet<>(v1.getKeySet());
        intersection.retainAll(v2.getKeySet());

        Double nominator = 0.0;
        Double sumDeltaOneSqr = 0.0;
        Double sumDeltaTwoSqr = 0.0;

        for (Vector vector : intersection){
            Double deltaOne = v1.getDataPoint(vector) - v1.getAverage();
            Double deltaTwo = v2.getDataPoint(vector) - v2.getAverage();

            nominator += deltaOne * deltaTwo;

            sumDeltaOneSqr += deltaOne * deltaOne;
            sumDeltaTwoSqr += deltaTwo * deltaTwo;

        }

        if (sumDeltaOneSqr == 0.0 || sumDeltaTwoSqr == 0.0) return 0.0;

        return (nominator / (Math.sqrt(sumDeltaOneSqr) * Math.sqrt(sumDeltaTwoSqr)));
    }

}
