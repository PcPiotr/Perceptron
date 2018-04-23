public class NeuralNetwork {

    private int input;
    private int hidden;
    private int output;

    private double learningRate = 0.5;
    private double momentum = 0.1;

    private boolean useBias = false;
    private boolean lineal = false;

    private Matrix weightsHI;
    private Matrix weightsOH;
    private Matrix biasH;
    private Matrix biasO;

    private Matrix deltaWeightsHI;
    private Matrix deltaWeightsOH;
    private Matrix deltaBiasO;
    private Matrix deltaBiasH;

    //Setters
    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    public void setMomentum(double momentum) {
        this.momentum = momentum;
    }

    public void setUseBias(boolean useBias) {
        this.useBias = useBias;
    }

    public void setLineal(boolean lineal) {
        this.lineal = lineal;
    }

    //Constructor
    public NeuralNetwork(int inputCount, int hiddenCount, int outputCount) {
        int vectorColumnCount = 1;

        weightsOH = new Matrix(outputCount, hiddenCount);
        weightsHI = new Matrix(hiddenCount, outputCount);

        deltaWeightsOH = new Matrix(outputCount, hiddenCount);
        deltaWeightsHI = new Matrix(hiddenCount,inputCount);

        biasO = new Matrix(outputCount, vectorColumnCount);
        biasH = new Matrix(hiddenCount, vectorColumnCount);

        deltaBiasO = new Matrix(outputCount, vectorColumnCount);
        deltaBiasH = new Matrix(hiddenCount, vectorColumnCount);
    }

    //Feedforward function
    Matrix Feedforward(Matrix input) {
        Matrix hidden = weightsHI.retMultiply(input);
        if (useBias) { hidden.add(biasH); }
        hidden.map(new ActivationFunction());

        Matrix output = weightsOH.retMultiply(hidden);
        if (useBias) { output.add(biasO); }
        output.map(new LinearFunction());
        return output;
    }

    void Backforward(Matrix input, Matrix answer){
        Matrix hidden = weightsHI.retMultiply(input);
        if (useBias) { hidden.add(biasH); }
        hidden.map(new ActivationFunction());

        Matrix output = weightsOH.retMultiply(hidden);
        if (useBias) { output.add(biasO); }
        output.map(new LinearFunction());

        //Output Error
        Matrix outputError = answer.retSubtract(output);

        //Hidden Layer Errors
        Matrix hiddenError = (weightsOH.retTranspose()).retMultiply(outputError);

        //Gradient
        Matrix gradient;
        gradient = output.retMap(new DerivativeLinealFunction());
        gradient = ( gradient.retMultiply(outputError) ).retMultiply(learningRate);

        //Delta Weights Output Hidden
        //
        deltaWeightsOH = ( gradient.retMultiply(hidden.retTranspose()) ).retAdd( deltaWeightsOH.retMultiply(momentum) );
        //
        //Adjust weightsOH
        weightsOH.add(deltaWeightsOH);

        //Adjust the output layer bias
        if (useBias){
            deltaBiasO = gradient.retAdd( deltaBiasO.retMultiply(momentum) );
            biasO.add(deltaBiasO);
        }

        //Hidden Gradient
        Matrix gradientHidden = hidden;
        gradientHidden.map(new DerivativeFunction());
        gradientHidden = ( gradientHidden.retMultiply(hiddenError) ).retMultiply(learningRate);



        //Inputs->hidden deltas
        deltaWeightsHI = ( gradientHidden.retMultiply(input.retTranspose()) ).retAdd( deltaWeightsHI.retMultiply(momentum) );

        //Adjust weightsHI by delta
        weightsHI.add(deltaWeightsHI);

        //Adjust hidden layer bias
        if (useBias){
            deltaBiasH = gradientHidden.retAdd( deltaBiasH.retMultiply(momentum) );
            biasH.add(deltaBiasH);
        }
    }
}