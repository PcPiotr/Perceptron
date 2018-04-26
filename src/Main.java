import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Main {

    public static void main(String[] args) throws IOException {

        int numberOfTests = 4; int numberOfInputs = 4;
        Matrix inputData = new Matrix (numberOfTests, numberOfInputs, "Input.txt");
        Matrix outputData = inputData;
        NeuralNetwork network;

        //Options
        network = new NeuralNetwork(4,1,4);
        network.setUseBias(true);
        network.setLearningRate(0.1);
        network.setMomentum(0.5);
        int generations = 1000;

        ArrayList<Integer> numbers = new ArrayList<Integer>();
        for(int i = 0; i < 4; i++ ){
            numbers.add(i);
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter("export.txt"));

        for (int i = 0; i < generations; i++){
            Collections.shuffle(numbers);
            for (int j = 0; j < 4; j++){
                Integer index = numbers.get(j);
                network.backpropagation(inputData.getRow(index), outputData.getRow(index));

            }
            //writer.write(Double.toString(MSECalc.calculate(outputData, inputData, network)) + "\n");
            //System.out.println(MSECalc.calculate(outputData, inputData, network));
        }

        for (int i = 0; i < 4; i++){
            Matrix output = network.feedforward( inputData.getRow(i));
            inputData.getRow(i).print();
            output.print();
        }
        writer.close();
    }
}
