import java.util.*;

/**
 * Prediction class. Singleton class that handles all user calls
 * Created by Qi_He on Mar/25/16.
 */
public class Prediction {

    private static Prediction p = new Prediction();
    private PearsonSimilarity ps;
    private CosineSimilarity cs;
    private SelectNeighbor sn;
    private PreferenceCalculator pc;
    private Database db;


    /**
     * Singleton class Prediction
     */
    private Prediction() {
        ps = new PearsonSimilarity();
        cs = new CosineSimilarity();
        sn = new SelectNeighbor();
        pc = new PreferenceCalculator();
        db = Database.getInstance();
    }

    /**
     * @return singleton prediction object
     */
    public static Prediction getInstance(){
        return p;
    }

    /**
     * Important method to get the prediction
     * Throws illegal arguments exception if the user/movie doesn't exist
     * @param userID given user ID
     * @param itemID given item ID
     * @param method method selection (1 for Baseline, 2 for kNN with Pearson Sim, 3 for kNN with Cosine)
     * @return predict how many the user likes the item
     */
    public double getPrediction(String userID, String itemID, Integer method) throws IllegalArgumentException{

        User user = db.getUser(userID);
        Item item = db.getItem(itemID);
        Double overallAvg = db.getAverage();

        if (user == null || item == null || overallAvg == null) throw new IllegalArgumentException();

        if (method == 1){
            return pc.baseLine(user, item ,overallAvg);
        }else if (method == 2){
            return pc.getPreference(user, item, sn.getNeighbors(item, ps, user), ps);
        }else{
            return pc.getPreference(user, item, sn.getNeighbors(item, cs, user), cs);
        }

    }

    /**
     * Get the N highest recommendations
     * Throws illegal arguments exception if the user doesn't exist
     * @param userID given an String user ID
     * @param n given # of recs
     * @param method method selection
     * @return ArrayList of recommendations
     */
    public ArrayList<String> getNHighest(String userID, int n, Integer method) throws IllegalArgumentException{

        // Get all movies out there
        Set<String> movieIDSet = db.getItemIDSet();

        // Map of movie ID and prediction
        HashMap<String, Double> predictionMap = new HashMap<>();

        ArrayList<String> output = new ArrayList<>();

        User user = db.getUser(userID);
        if (user == null ) throw new IllegalArgumentException();

        // Check all movies user has seen
        // Assuming users only exist when there is at least one movie seen
        Set<Vector> viewedMovie = user.getKeySet();

        for (String movieID : movieIDSet){
            Item movie = db.getItem(movieID);

            // If user has not seen the movie
            if (!viewedMovie.contains(movie)){
                double prediction = getPrediction(userID, movieID, method);
                predictionMap.put(movieID, prediction);
            }
        }

        // Min size of available movie to recommend vs given size n
        int tempSize = Math.min(n, predictionMap.keySet().size());

        // Sorting top movies
        List<HashMap.Entry<String, Double>> list = new LinkedList<>(predictionMap.entrySet());
        Collections.sort(list, (o1, o2) -> (o2.getValue()).compareTo( o1.getValue() ));

        for (int i = 0; i < tempSize; i++){
            String id = list.get(i).getKey();
            output.add(id);
        }

        return output;

    }


    /**
     * Test the best recommended system
     * @return (1 for Baseline, 2 for kNN with Pearson Sim, 3 for kNN with Cosine)
     */
    public int test(){

        int baseline = 0;
        int pearson = 0;
        int cosine = 0;

        for (int i=0; i < 100; i++) {
            int item = new Random().nextInt(db.getUserIDSet().size());
            int counter = 0;

            for (String user : db.getUserIDSet()) {
                counter++;

                if (counter == item) {

                    Vector itemObj = db.getUser(user).getKeySet().iterator().next();
                    String itemID = itemObj.getID();
                    double ratings = db.getUser(user).getDataPoint(itemObj);


                    double predictBaseline = Math.abs(getPrediction(user, itemID, 0) - ratings);
                    double predictPearson = Math.abs(getPrediction(user, itemID, 1) - ratings);
                    double predictCosine = Math.abs(getPrediction(user, itemID, 2) - ratings);


                    double best = Math.min(predictBaseline, Math.min(predictCosine, predictPearson));

                    if (best != predictBaseline) {
                        if (best == predictPearson){
                            pearson++;
                        }else{
                            cosine++;
                        }
                    } else {
                        baseline++;
                    }

                    break;
                }
            }
        }

        double most = Math.max(baseline, Math.max(cosine, pearson));

        if (most == baseline){
            return 1;
        }else if (most == pearson){
            return 2;
        }else{
            return 3;
        }

    }

}
