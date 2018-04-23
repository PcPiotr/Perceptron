import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Main {

    public static void main(String[] args) throws IOException {

        int numberOfTests = 4; int numberOfInputs = 4;
        Matrix inputData = new Matrix (numberOfTests, numberOfInputs,"Input.txt",0);
        Matrix outputData = inputData;
        NeuralNetwork network;

        //Options
        network = new NeuralNetwork(4,2,4);
        network.setUseBias(true);
        network.setLineal(false);
        network.setLearningRate(0.1);
        network.setMomentum(0.5);
        int generations = 1000;

        ArrayList<Integer> numbers = new ArrayList<Integer>();
        for(int i = 0; i < 4; i++ ){
            numbers.add(i);
        }

        for (int i = 0; i < generations; i++){
            Collections.shuffle(numbers);
            for (int j = 0; j < 4; j++){
                Integer index = numbers.get(j);
                network.Backforward(outputData.getRow(index), inputData.getRow(index));

            }
            System.out.println(MSECalc.calculate(outputData, inputData, network));
        }

        for (int i = 0; i < 4; i++){
            Matrix output = network.Feedforward( inputData.getRow(i));
            inputData.getRow(i).print();
            output.print();
        }
    }
}
