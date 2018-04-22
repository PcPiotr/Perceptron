import static java.lang.Math.exp;

public class ActivationFunction implements IFunAct {

    public double usefn(double a){ return 1 / (1 + exp(-a)); }
}
