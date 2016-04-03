import com.clarifai.api.ClarifaiClient;
import com.clarifai.api.RecognitionRequest;
import com.clarifai.api.RecognitionResult;
import com.clarifai.api.Tag;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Main {

    public static String selectedPath;
    public static String selectedDestination;
    public static File[] lists; //Saves the lists into an array so we can reaccess the names


    public static void main(String[] args) throws IOException {

        // Read in the API keys.
        FileReader reader = new FileReader("secrets.txt");
        BufferedReader br = new BufferedReader(reader);
        String[] secrets = new String[2];

        for (int i = 0; i < secrets.length; i++) {
            secrets[i] = br.readLine();
        }

        // Create the client.
        ClarifaiClient client = new ClarifaiClient(secrets[0], secrets[1]);
        List<RecognitionResult> results = client.recognize(new RecognitionRequest(new File("test.jpg")));

        GUI.launch(GUI.class);
        System.out.println(selectedPath);

        List<RecognitionResult> test = Container(client);
        for (int i = 0; i < test.size(); i++) {
            List<Tag> tag = test.get(i).getTags();
            System.out.println(lists[i].getName()); //Prints out name of image.
            for ( int j = 0; j < tag.size(); j++){
                System.out.println(tag.get(j).getName() + ": " + tag.get(j).getProbability());
            }
        }
    }

    public static List<RecognitionResult> Container(ClarifaiClient client) {
        File file = new File(selectedPath);
        File[] files = file.listFiles();
        lists = files;
        // These statements check to see if the files are readable since the OS denies access to its files.
//        if (file.canRead()) {
//            System.out.println("I made it here");
//        }
//        else {
//            file.setReadable(true);
//        }
        // End of checking.
        List<RecognitionResult> results = client.recognize(new RecognitionRequest(files));
        if (results != null) {
            //Checking Size of List to make sure every file was saved.
//            int z = results.size();
//            System.out.println("I made it?" + "  " + z);
            return results;
        }
        else {
            System.out.println("Folder is empty");
            return null;
        }

    }

    public static void setPath(String s) {
        selectedPath = s;
    }

    public static void setDestination(String s) {
        selectedDestination = s;
    }

}