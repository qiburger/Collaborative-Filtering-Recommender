import java.util.HashSet;

/**
 * Class to implement the preference function. Here it's pearson function.
 * Created by Qi_He on Mar/25/16.
 */
public class PreferenceCalculator {

    /**
     * Construction. Left empty for future changes.
     */
    public PreferenceCalculator(){

    }

    /**
     * Get a user's predicted preference of an item
     * @param user User in question
     * @param item Item in question
     * @param neighborSet neighbors of this user for Pearson function
     * @param sc Similarity function
     * @return Double predicted preference
     */
    public Double getPreference(Vector user, Vector item, HashSet<Vector> neighborSet, SimilarityCalculator sc){
        Double pearson = user.getAverage();
        Double nominator = 0.0;
        Double denominator = 0.0;

        for (Vector neighbor : neighborSet){
            Double sim = sc.getSimilarity(neighbor, user);
            Double delta = neighbor.getDataPoint(item) - neighbor.getAverage();
            nominator += sim * delta;

            denominator += Math.abs(sim);
        }

        if (denominator == 0.0) return pearson;

        pearson += nominator / denominator;

        return pearson;
    }

    /**
     * Implementation of baseline algo
     * @param user user vector
     * @param item item vector
     * @param overallAvg overall average - can access from the database
     * @return prediction
     */
    public Double baseLine(Vector user, Vector item, Double overallAvg){

        double baselineUser = user.getAverage() - overallAvg;
        double baselineItem = 0.0;

        for (Vector reviewer : item.getKeySet()){

            baselineItem += item.getDataPoint(reviewer) - (reviewer.getAverage() - overallAvg) - overallAvg;
        }

        baselineItem /= item.getKeySet().size();

        return (overallAvg + baselineUser + baselineItem);

    }
}
