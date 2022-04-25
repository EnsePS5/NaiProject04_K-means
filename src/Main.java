import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        //Data preparation
        System.out.println("Please insert file path to read from: ");

        Scanner in = new Scanner(System.in);
        String filePath = in.nextLine();

        PreparedData data = new PreparedData(filePath);
        data.dataPreparation();

        //Parameters preparation
        System.out.println("Please insert k parameter: ");

        int kParam = in.nextInt();

        System.out.println("\nPlease insert " + kParam +
                " different indexes values to make them a cluster cores. Max index is " + (data.listOfPoints.size()-1));

        ArrayList<Integer> indexCoreValues = new ArrayList<>(kParam);

        for (int i = 0; i < kParam; i++) {
            System.out.print("The " + (i+1) + " value is -> ");
            int temp = in.nextInt();

            if (!indexCoreValues.contains(temp) && data.listOfPoints.size()-1 >= temp)
                indexCoreValues.add(temp);
            else {
                System.out.println("Current vale is being used or is to high. Please change it to other number");
                i--;
            }
        }
        int next;

        do {
            data.groupAssigner(indexCoreValues);

            System.out.println("type in 1 to go for another loop or type in -1 to STOP: ");
            next = in.nextInt();
        }while (next == 1);
    }

    //used to make randomize values
    private static void makeRandomValues(){

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 100; i++) {
            try (PrintWriter printWriter = new PrintWriter("C:\\Users\\citio\\OneDrive\\Pulpit\\Programowanie Java\\NaiProject04_K-means\\resources\\recordsToGroup.csv")) {

                for (int j = 0; j < 4; j++) {
                    sb.append((double)Math.round((Math.random() * 20)*10000d) / 10000d);
                    sb.append(',');
                }
                sb.append('\n');

                printWriter.write(sb.toString());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
