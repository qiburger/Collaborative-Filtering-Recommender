import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Help parse and load data into Database
 * Created by Qi_He on Mar/23/16.
 */
public class DataLoader {

    /**
     * This class helps load the file based on given string address
     * @param fileAddr string address to data file
     * @throws FileNotFoundException
     */
    public DataLoader(String fileAddr, String delim, String remove, int header) throws FileNotFoundException{

        //Use this to do faster lookup and add ratings to Item.
        HashMap<String, Item> idToItem = new HashMap<>();
        HashMap<String, User> idToUser = new HashMap<>();

        Double sum = 0.0;
        int counter = 0;
        int line = 0;

        Scanner fileReader = new Scanner(new File(fileAddr));

        // Parse every line to get userID, movieID and ratings.
        while(fileReader.hasNextLine()){
            String rawLine = fileReader.nextLine();
            String[] tokens = rawLine.split(delim);

            if (header==1 && line == 0){
                line++;
                continue;
            }

            String userID = tokens[0].replace(remove, "");
            String movieID = tokens[1].replace(remove, "");
            Double ratings = Double.parseDouble(tokens[2].replace(remove, ""));

            sum += ratings;
            counter++;

            Item movie;
            User user;

            if (idToItem.containsKey(movieID)){
                // movie item already in set -> just need to update the movie's HashMap
                movie = idToItem.get(movieID);
            }else{
                // initiate a new movie object
                movie = new Item(movieID, new HashMap<>());
            }

            // Do the same to construct an user object
            if (idToUser.containsKey(userID)){
                user = idToUser.get(userID);
            }else{
                user = new User(userID, new HashMap<>());
            }

            // Now link two pointers to each other's collection.
            movie.addVector(user, ratings);
            user.addVector(movie, ratings);

            idToItem.put(movieID, movie);
            idToUser.put(userID, user);
        }

        // Set up the Database
        Database db = Database.getInstance();
        db.setItems(idToItem);
        db.setUsers(idToUser);
        db.setAverage(sum / counter);

//        for (User user : users){
//            System.out.print("User: ");
//            System.out.println(user.getID());
//            for (Vector movie : user.getKeySet()){
//                System.out.print("Movie: ");
//                System.out.println(movie.getID());
//                System.out.print("Rating: ");
//                System.out.println(user.getDataPoint(movie));
//            }
//        }

    }
}
