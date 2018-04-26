public class MSECalc {

    static double calculate(Matrix trening, Matrix test, NeuralNetwork network){
        Matrix vectorMSE = trening.getRow(0).retMultiply(0);
        for(int i = 0; i < 4; i++){
            Matrix t = trening.getRow(i);
            Matrix r = network.feedforward(test.getRow(i));

            Matrix error = t.retSubtract(r);
            error.multiply(t.retSubtract(r));

            vectorMSE.add(error);
        }

        double totalMSE = 0.0;
        for(int i = 0; i < 4; i++){
            totalMSE += vectorMSE.getValues().get(i).get(0);
        }
        return(totalMSE/4.0);
    }
}
