import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles all user interactions in main
 * Created by Qi_He on Mar/23/16.
 */
public class UserInteraction {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("To get started, enter data file address.");
        String fileAddr = sc.nextLine();

        System.out.println("Then enter the delimiter to parse the file");
        String delim = sc.nextLine();

        System.out.println("Finally do you want to remove anything when tokenize the entries?");
        System.out.println("Enter no if none");
        String remove = sc.nextLine();

        System.out.println("Input 0 for no header, 1 for remove first line (header).");
        int header = Integer.parseInt(sc.nextLine());

        System.out.println("Loading data. Please wait");
        loadData(fileAddr, delim, remove, header);

        while (true){
            System.out.println("Continuously getting predictions.");
            System.out.println("First input user id. Enter q to quit.");
            String userID = sc.nextLine();

            if (userID.equals("q")) break;

            System.out.println("Then input movie id. Also enter q to quit.");
            String movieID = sc.nextLine();

            if (movieID.equals("q")) break;

            System.out.println("Input 1 for Baseline, 2 for kNN with Pearson Sim, 3 for kNN with Cosine");
            Integer method = Integer.parseInt(sc.nextLine());

            getPref(userID, movieID, method);
        }

        while (true){
            System.out.println("Continuously getting top n recommendations.");
            System.out.println("First input user id. Enter q to quit.");
            String userID = sc.nextLine();

            if (userID.equals("q")) break;

            System.out.println("Then input integer number of recs");
            Integer number = Integer.parseInt(sc.nextLine());

            System.out.println("Input 1 for Baseline, 2 for kNN with Pearson Sim, 3 for kNN with Cosine");
            Integer method = Integer.parseInt(sc.nextLine());

            getRec(userID, number, method);
        }

        System.out.println("Now testing: ");
        System.out.println("Showing 1 if Baseline is the best, 2 for kNN with Pearson Sim, 3 for kNN with Cosine");
        Prediction p = Prediction.getInstance();
        System.out.println(p.test());

        System.out.println("Done.");

        sc.close();

    }

    /**
     * Private helper to ask for file address and load data
     * @param fileAddr string file address
     */
    private static void loadData(String fileAddr, String delim, String remove, int header) throws IllegalArgumentException{
//        String defaultAddr = "ml-10M100K/test.dat";
//        String finalAddr = "ml-10M100K/ratings.dat";
        DataLoader dl;

        if (fileAddr.isEmpty() || delim.isEmpty() || header < 0 || header > 1) throw new IllegalArgumentException();

        if (remove.toLowerCase().equals("no")){
            remove = "";
        }

        try {
            dl = new DataLoader(fileAddr, delim, remove, header);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    /**
     * Runs the prediction algorithm
     * @param movieID movie String id
     * @param userID userID String
     * @param method method to use (1 for Baseline, 2 for kNN with Pearson Sim, 3 for kNN with Cosine)
     * @return prediction of given user's preference on given movie
     */
    private static Double getPref(String userID, String movieID, Integer method) throws IllegalArgumentException{

        if (userID.isEmpty() || movieID.isEmpty() || method == null) throw new IllegalArgumentException();

        Prediction p = Prediction.getInstance();
        double output = p.getPrediction(userID, movieID, method);

        System.out.println("Prediction is: ");
        System.out.println(output);

        return output;

    }

    /**
     * Run the recommender system
     * @param number int # of recs
     * @param userID String user ID
     * @param method method to use (1 for Baseline, 2 for kNN with Pearson Sim, 3 for kNN with Cosine)
     * @return List of recommendations for given user
     */
    private static ArrayList<String> getRec(String userID, Integer number, Integer method) throws IllegalArgumentException{

        if (userID.isEmpty() || number == null) throw new IllegalArgumentException();

        Prediction p = Prediction.getInstance();
        ArrayList<String> output = p.getNHighest(userID, number, method);

        System.out.println("Recommendation is: ");
        for (String i : output){
            System.out.print("Movie ID: ");
            System.out.println(i);
        }

        return output;

    }

}
