import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Testing class for testing 3 different techniques
 * Created by Qi_He on Apr/4/16.
 */
public class Test {

    public static void main(String[] args) {

        String holdOut = "hold-out.csv";
        String training = "training-set.csv";
        Scanner fileReader;
        Prediction p = Prediction.getInstance();

        int baseline = 0;
        int pearson = 0;
        int cosine = 0;

        try {
            DataLoader dl = new DataLoader(training, ",", "", 1);
            fileReader = new Scanner(new File(holdOut));

            while(fileReader.hasNextLine()){
                String rawLine = fileReader.nextLine();
                String[] tokens = rawLine.split(",");

                String userID = tokens[0];
                String itemID = tokens[1];
                Double ratings = Double.parseDouble(tokens[2]);

                double predictBaseline = Math.abs(p.getPrediction(userID, itemID, 0) - ratings);
                double predictPearson = Math.abs(p.getPrediction(userID, itemID, 1) - ratings);
                double predictCosine = Math.abs(p.getPrediction(userID, itemID, 2) - ratings);

                double best = Math.min(predictBaseline, Math.min(predictCosine, predictPearson));

                if (best == predictBaseline){
                    baseline++;
                }else if (best == predictPearson){
                    pearson++;
                }else{
                    cosine++;
                }

            }

        }catch(FileNotFoundException e){
            e.printStackTrace();
        }

        System.out.println("Baseline: ");
        System.out.println(baseline);
        System.out.println("Pearson: ");
        System.out.println(pearson);
        System.out.println("Cosine");
        System.out.println(cosine);

    }
}
