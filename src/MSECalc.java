final public class MSECalc {

    private MSECalc(){

    };

    static double calculate(Matrix trening, Matrix test, NeuralNetwork network, int testCount){
        Matrix vectorMSE = trening.getRow(0).retMultiply(0);
        for(int i = 0; i < testCount; i++){
            Matrix t = trening.getRow(i);
            Matrix r = network.Feedforward(test.getRow(i));

            Matrix error = t.retSubtract(r);
            error.multiply(t.retSubtract(r));

            vectorMSE.add(error);
        }

        double totalMSE = 0.0;
        for(int i = 0; i < testCount; i++){
            totalMSE += vectorMSE.getValues().get(i).get(0);
        }
        return(totalMSE/4.0);
    }

}
