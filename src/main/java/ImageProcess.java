import java.io.File;

public class ImageProcess {
    public static String API_ENDPOINT = "https://app.seker.live/fm1/upload-file";
    public static String STUDENT_ID = "0"; //Set your ID here!
    public static String INPUT_FILE_PATH = ""; //Set the path to the input file
    public static String OUTPUT_FILE_PATH = ""; //Set the path to the output file

    public static void main(String[] args) {
        cleanTheNoisyImage(INPUT_FILE_PATH);

//        Utils.uploadFile(API_ENDPOINT, new File(OUTPUT_FILE_PATH), STUDENT_ID);
    }

    public static void cleanTheNoisyImage (String path) {
        //write your code here!

    }



}
