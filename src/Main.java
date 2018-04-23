import java.io.*;
import java.util.*;

public class Main {

    public static void importData(String filename, Matrix one, Matrix two) throws FileNotFoundException {
        Scanner input = new Scanner(new File(filename)).useLocale(Locale.US) ;
        Vector<Vector<Double>> oneVal = one.getValues();
        Vector<Vector<Double>> twoVal = two.getValues();
        for (int i = 0; i < one.getRows(); i++) {
            oneVal.get(i).set(0, input.nextDouble());
            twoVal.get(i).set(0, input.nextDouble());
            }
            one.setValues(oneVal);
        two.setValues(twoVal);
        }

    public static void main(String[] args) throws IOException {

        boolean wariant = false;

        Matrix inputData;  Matrix outputData; int numberOfTests; int numberOfInputs;
        if(wariant){
            numberOfTests = 81; numberOfInputs = 1;
            inputData = new Matrix (81, 1);
            outputData = new Matrix (81, 1);
            importData("apro1.txt", inputData, outputData);
        }
        else{
            numberOfTests = 15; numberOfInputs = 1;
            inputData = new Matrix (15, 1);
            outputData = new Matrix (15, 1);
            importData("apro2.txt", inputData, outputData);
        }

        NeuralNetwork network;
        //Options
        network = new NeuralNetwork(1,2,1);
        network.setUseBias(true);
        network.setLineal(false);
        network.setLearningRate(0.1);
        network.setMomentum(0.5);
        int generations = 1000;

        ArrayList<Integer> numbers = new ArrayList<Integer>();
        for(int i = 0; i < numberOfTests; i++ ){
            numbers.add(i);
        }

        BufferedWriter pisarz = new BufferedWriter(new FileWriter("export.txt"));

        for (int i = 0; i < generations; i++){
            Collections.shuffle(numbers);
            for (int j = 0; j < numberOfTests; j++){
                Integer index = numbers.get(j);
                network.Backforward(outputData.getRow(index), inputData.getRow(index));

            }
            pisarz.write(Double.toString(MSECalc.calculate(outputData, inputData, network, numberOfTests)) + "\n");
            System.out.println(MSECalc.calculate(outputData, inputData, network, numberOfTests));
        }

        Matrix testInData = new Matrix (1000, 1);
        Matrix testOutData = new Matrix (1000, 1);
        importData("aprotest.txt", testInData, testOutData);

        for (int i = 0; i < 1000; i++){
            Matrix output = network.Feedforward( testInData.getRow(i));
            testOutData.getRow(i).print();
            output.print();
        }
    }
}
