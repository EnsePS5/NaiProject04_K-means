import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PreparedData {

    private final String filePath;

    private ArrayList<ArrayList<Double>> listOfCores = new ArrayList<>();

    public ArrayList<String> listOfPoints = new ArrayList<>();
    public Map<Integer, ArrayList<Integer>> mapOfIndexesFromListOfPoints = new HashMap<>();

    public PreparedData(String filePath){
        this.filePath = filePath;
    }

    //
    //public methods
    //

    //prepares data (reads from file and saves to 'listOfPoints')
    public void dataPreparation() throws IOException {

        BufferedReader bufferedReader = fileOpener(filePath);

        String line;
        while ((line = bufferedReader.readLine()) != null){
            listOfPoints.add(line);
        }
        bufferedReader.close();
    }

    //the equation for distance calculator
    public void groupAssigner(ArrayList<Integer> indexCoreVales){

        if (listOfCores.isEmpty()) {
            for (Integer indexCoreVale : indexCoreVales) {
                listOfCores.add(valuesExtraction(listOfPoints.get(indexCoreVale)));
            }
        }

        for (int i = 0; i < listOfPoints.size(); i++) {

            double minDistance = -1.0;
            int coreMinIndex = 0;

            double sum;

            ArrayList<Double> tempArr = valuesExtraction(listOfPoints.get(i));

            for (int ij = 0; ij < indexCoreVales.size(); ij++) {

                sum = 0.0;

                for (int j = 0; j < tempArr.size(); j++) {
                    sum += Math.pow(listOfCores.get(ij).get(j) - tempArr.get(j), 2);
                }

                if (sum < minDistance || minDistance == -1) {
                    minDistance = sum;
                    coreMinIndex = ij;
                }
                if (ij == indexCoreVales.size()-1)
                    System.out.println(i + ". " + tempArr + " assigned to cluster with core " + listOfCores.get(coreMinIndex)
                            + ". Distance to core -> " + sum);
                }

            ArrayList<Integer> temp = new ArrayList<>();

            if (mapOfIndexesFromListOfPoints.get(coreMinIndex) != null)
                temp.addAll(mapOfIndexesFromListOfPoints.get(coreMinIndex));

            temp.add(i);

            mapOfIndexesFromListOfPoints.put(coreMinIndex, temp);

        }
        System.out.println(mapOfIndexesFromListOfPoints.toString());

        adaptCores();

        mapOfIndexesFromListOfPoints.clear();
    }

    //
    //private methods
    //

    //assigns to certain group
    private void adaptCores(){

        Map <Integer,Double> mapOfNewCoresVariables = new HashMap<>();
        ArrayList<ArrayList<Double>> newListOfCores = new ArrayList<>();

        for (int i = 0; i < listOfCores.size(); i++) {

            for (int j = 0; j < mapOfIndexesFromListOfPoints.get(i).size(); j++) {

                var extractedPoint = valuesExtraction(listOfPoints.get(mapOfIndexesFromListOfPoints.get(i).get(j)));

                for (int k = 0; k < extractedPoint.size(); k++) {

                    double temp = 0.0;

                    if (i != 0)
                        temp = mapOfNewCoresVariables.get(k);

                    temp += extractedPoint.get(k);
                    mapOfNewCoresVariables.put(k,temp);
                }
            }
            ArrayList<Double> coreValues = new ArrayList<>();

            for (int j = 0; j < mapOfNewCoresVariables.size(); j++) {
                double temp = mapOfNewCoresVariables.get(j);
                temp = temp/mapOfIndexesFromListOfPoints.get(i).size();
                coreValues.add(temp);
            }

            newListOfCores.add(coreValues);
        }

        System.out.println("new cores - > \n " + newListOfCores);
        if (listOfCores.equals(newListOfCores)) {
            System.out.println("Cores does not change no more. There is no point in further cycle. System shutdown... ");
            System.exit(0);
        }

        listOfCores.clear();
        listOfCores = newListOfCores;
    }

    //Opens file
    private static BufferedReader fileOpener(String filePath) throws FileNotFoundException {

        File file = new File(filePath);
        return new BufferedReader(new FileReader(file));
    }

    //Get vales from record
    private static ArrayList<Double> valuesExtraction(String core){

        ArrayList<Double> result = new ArrayList<>();
        List<String> temp = List.of(core.split(","));

        for (String val : temp){
            result.add(Double.parseDouble(val));
        }

        return result;
    }
}
