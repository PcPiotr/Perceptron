public class NeuralNetwork {
	
    private double learningRate;
    private double momentum;

    private boolean useBias;

    private Matrix weightsInput;
    private Matrix weightsOutput;
    private Matrix biasH;
    private Matrix biasO;

    private Matrix deltaWeightsInput;
    private Matrix deltaWeightsOutput;
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

    //Constructor
    public NeuralNetwork(int inputCount, int hiddenCount, int outputCount) {
        int vectorColumnCount = 1;

        weightsOutput = new Matrix(outputCount, hiddenCount);
        weightsInput =  new Matrix(hiddenCount, outputCount);

        deltaWeightsOutput = new Matrix(outputCount, hiddenCount);
        deltaWeightsInput =  new Matrix(hiddenCount, inputCount);

        biasO = new Matrix(outputCount, vectorColumnCount);
        biasH = new Matrix(hiddenCount, vectorColumnCount);

        deltaBiasO = new Matrix(outputCount, vectorColumnCount);
        deltaBiasH = new Matrix(hiddenCount, vectorColumnCount);
    }

    Matrix feedforward(Matrix input) {
        Matrix hidden = weightsInput.retMultiply(input);
        if (useBias) { hidden.add(biasH); }
        hidden.map(new ActivationFunction());

        Matrix output = weightsOutput.retMultiply(hidden);
        if (useBias) { output.add(biasO); }
        output.map(new ActivationFunction());
        return output;
    }

    void backpropagation(Matrix input, Matrix answer){
        Matrix hidden = weightsInput.retMultiply(input);
        if (useBias) { hidden.add(biasH); }
        hidden.map(new ActivationFunction());

        Matrix output = weightsOutput.retMultiply(hidden);
        if (useBias) { output.add(biasO); }
        output.map(new ActivationFunction());

        //Output Error
        Matrix outputError = answer.retSubtract(output);

        //Hidden Layer Errors
        Matrix hiddenError = (weightsOutput.retTranspose()).retMultiply(outputError);

        //Gradient
        Matrix gradient;
        gradient = output.retMap(new DerivativeFunction());
        gradient = ( gradient.retMultiply(outputError) ).retMultiply(learningRate);

        //Delta Weights on Output and Adjust Weights on Output
        deltaWeightsOutput = ( gradient.retMultiply(hidden.retTranspose()) ).retAdd( deltaWeightsOutput.retMultiply(momentum) );
        weightsOutput.add(deltaWeightsOutput);

        //Adjust Bias On Output Layer
        if (useBias){
            deltaBiasO = gradient.retAdd( deltaBiasO.retMultiply(momentum) );
            biasO.add(deltaBiasO);
        }

        //Hidden Layer Gradient
        Matrix gradientHidden = hidden;
        gradientHidden.map(new DerivativeFunction());
        gradientHidden = ( gradientHidden.retMultiply(hiddenError) ).retMultiply(learningRate);

        //Delta on Input to Hidden Layer, Adjust Weights
        deltaWeightsInput = ( gradientHidden.retMultiply(input.retTranspose()) ).retAdd( deltaWeightsInput.retMultiply(momentum) );
        weightsInput.add(deltaWeightsInput);

        //Adjust Hidden Layer Bias
        if (useBias){
            deltaBiasH = gradientHidden.retAdd( deltaBiasH.retMultiply(momentum) );
            biasH.add(deltaBiasH);
        }
    }
}