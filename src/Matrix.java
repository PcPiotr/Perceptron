import java.io.*;
import java.net.URL;
import java.util.Scanner;
import java.util.Vector;

public class Matrix {

    //Values
    private Vector<Vector<Double>> values = new Vector<Vector<Double>>();

    public void setValues(Vector<Vector<Double>> values) {
        this.values = values;
    }

    //Getters
    public Vector<Vector<Double>> getValues() {
        return values;
    }

    public int getRows() {
        return values.size();
    }

    public int getCols() {
        return values.get(0).size();
    }

    public Matrix getRow(int index) {
        Vector<Vector<Double>> mat = new Vector<Vector<Double>>();
        mat.add(values.get(index));
        return(new Matrix(mat));
    }

    public Matrix getCol(int index) {
        Vector<Vector<Double>> mat = new Vector<Vector<Double>>();
        for(int i = 0; i < 4; i++){
            Vector<Double> temp = new Vector<Double>();
            temp.add(values.get(index).get(i));
            mat.add(temp);
        }
        return(new Matrix(mat));
    }

    // Default constructor
    Matrix() {

    }

    //Matrix constructor for number of rows and cols, random values
    Matrix(int nrRows, int nrCols) {
        for (int i = 0; i < nrRows; i++) {
            Vector<Double> row = new Vector<Double>();
            for (int j = 0; j < nrCols; j++) {
                row.add((-1) + Math.random() * (1 - (-1)));
            }
            values.add(row);
        }
    }

    // Matrix constructor for making matrix from other matrix
    Matrix(Matrix m) {
        for (int i = 0; i < m.values.size(); i++) {
            Vector<Double> row = new Vector<Double>();
            for (int j = 0; j < m.values.get(0).size(); j++) {
                row.add(m.values.get(i).get(j));
            }
            values.add(row);
        }
    }

    // Matrix constructor, reads values from file
    Matrix(int rows, int cols, String filename, int skipInputs) throws FileNotFoundException {
        Scanner input = new Scanner(new File(filename));
        for (int i = 0; i < skipInputs; i++) {
            int garbage = input.nextInt();
        }
        for (int i = 0; i < rows; i++) {
            Vector<Double> row = new Vector<Double>();
            for (int j = 0; j < cols; j++) {
                double temp = input.nextDouble();
                row.add(temp);
            }
            values.add(row);
        }
    }

    Matrix(Vector<Vector<Double>> vec) {
        values = vec;
    }

    public void print() {
        for (int i = 0; i < values.size(); i++) {
            for (int j = 0; j < values.get(0).size(); j++) {
                System.out.print(values.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }

    public void toFile(FileWriter file) {
        try {
            BufferedWriter pisarz = new BufferedWriter(file);

            for (int i = 0; i < values.size(); i++) {
                for (int j = 0; j < values.get(i).size(); j++) {
                    if (j == values.get(i).size() - 1) {
                        pisarz.write(Double.toString(values.get(i).get(j)));
                    } else {
                        pisarz.write(Double.toString(values.get(i).get(j)) + ",");
                    }
                }
                pisarz.newLine();
            }
            pisarz.flush();
        } catch (IOException e) {
            System.out.println("Well, IO Exception");
        }
    }

    public void map( IFunAct fn) {
        for (int i = 0; i < values.size(); i++) {
            for (int j = 0; j < values.get(i).size(); j++) {
                Double temp = fn.usefn(values.get(i).get(j));
                values.get(i).set(j, temp);
            }
        }
    }

    public Matrix retMap( IFunAct fn) {
        Vector<Vector<Double>> temp = new Vector<Vector<Double>>();
        for (int i = 0; i < values.size(); i++) {
            Vector<Double> row = new Vector<Double>();
            for (int j = 0; j < values.get(i).size(); j++) {
                row.add(fn.usefn(values.get(i).get(j)));
            }
            temp.add(row);
        }

        return (new Matrix(temp));
    }

    public void add(double s){
        for (int i = 0; i < values.size(); i++) {
            for (int j = 0; j < values.get(i).size(); j++) {
                values.get(i).set(j, (values.get(i).get(j) + s));
            }
        }
    }

    public Matrix retAdd(double s){
        Vector<Vector<Double>> temp = new Vector<Vector<Double>>();
        for (int i = 0; i < values.size(); i++) {
            Vector<Double> row = new Vector<Double>();
            for (int j = 0; j < values.get(i).size(); j++) {
                row.add(values.get(i).get(j) + s);
            }
            temp.add(row);
        }
        return (new Matrix(temp));
    }

    public void add(Matrix m){
        for (int i = 0; i < values.size(); i++) {
            for (int j = 0; j < values.get(i).size(); j++) {
                values.get(i).set(j, (values.get(i).get(j) + m.values.get(i).get(j)));
            }
        }
    }

    public Matrix retAdd(Matrix m){
        Vector<Vector<Double>> temp = new Vector<Vector<Double>>();
        for (int i = 0; i < values.size(); i++) {
            Vector<Double> row = new Vector<Double>();
            for (int j = 0; j < values.get(i).size(); j++) {
                row.add((values.get(i).get(j) + m.values.get(i).get(j)));
            }
            temp.add(row);
        }
        return (new Matrix(temp));
    }

    public void subtract(double s){
        for (int i = 0; i < values.size(); i++) {
            for (int j = 0; j < values.get(i).size(); j++) {
                values.get(i).set(j, (values.get(i).get(j) - s));
            }
        }
    }

    public Matrix retSubtract(double s){
        Vector<Vector<Double>> temp = new Vector<Vector<Double>>();
        for (int i = 0; i < values.size(); i++) {
            Vector<Double> row = new Vector<Double>();
            for (int j = 0; j < values.get(i).size(); j++) {
                row.add(values.get(i).get(j) - s);
            }
            temp.add(row);
        }
        return (new Matrix(temp));
    }

    public void subtract(Matrix m){
        for (int i = 0; i < values.size(); i++) {
            for (int j = 0; j < values.get(i).size(); j++) {
                values.get(i).set(j, (values.get(i).get(j) - m.values.get(i).get(j)));
            }
        }
    }

    public Matrix retSubtract(Matrix m){
        Vector<Vector<Double>> temp = new Vector<Vector<Double>>();
        for (int i = 0; i < values.size(); i++) {
            Vector<Double> row = new Vector<Double>();
            for (int j = 0; j < values.get(i).size(); j++) {
                row.add((values.get(i).get(j) - m.values.get(i).get(j)));
            }
            temp.add(row);
        }
        return (new Matrix(temp));
    }

    public void multiply(double s){
        for (int i = 0; i < values.size(); i++)
        {
            for (int j = 0; j < values.get(i).size(); j++)
            {
                values.get(i).set(j, values.get(i).get(j)*s);
            }
        }
    }

    public Matrix retMultiply(double s){
        Vector<Vector<Double>> temp = new Vector<Vector<Double>>();
        for (int i = 0; i < values.size(); i++) {
            Vector<Double> row = new Vector<Double>();
            for (int j = 0; j < values.get(i).size(); j++) {
                row.add(values.get(i).get(j) * s);
            }
            temp.add(row);
        }
        return (new Matrix(temp));
    }

    public Matrix retMultiply(Matrix m) {
            if (values.get(0).size() == m.values.size()) {
                Matrix result = new Matrix(values.size(), m.values.get(0).size());
                for (int i = 0; i < result.values.size(); i++) {
                    for (int j = 0; j < result.values.get(i).size(); j++) {
                        double sum = 0;
                        for (int k = 0; k < values.get(i).size(); k++) {
                            sum += values.get(i).get(k)* m.values.get(k).get(j);
                        }
                        result.values.get(i).set(j, sum);
                    }
                }
                return result;
            }
            else {
                Matrix result = new Matrix(values.size(), values.get(0).size());
                for (int i = 0; i < result.values.size(); i++) {
                    for (int j = 0; j < result.values.get(i).size(); j++) {
                        double sum = 0;
                        {
                            sum += values.get(i).get(j) * m.values.get(i).get(j);
                        }
                        result.values.get(i).set(j, sum);
                    }
                }
                return result;
            }
        }


    public void multiply(Matrix m) {
        Vector<Vector<Double>> temp = new Vector<Vector<Double>>();
        if (values.get(0).size() == m.values.size()) {
            for (int i = 0; i < values.size(); i++) {
                Vector<Double> row = new Vector<Double>();
                for (int j = 0; j < values.get(i).size(); j++) {
                    double sum = 0;
                    for (int k = 0; k < values.get(0).size(); k++) {
                        sum += values.get(i).get(k) * m.values.get(k).get(j);
                    }
                    row.add(sum);
                }
                temp.add(row);
            }
        }
        else if (values.size() == m.values.size() && values.get(0).size() == m.values.get(0).size()) {
            for (int i = 0; i < values.size(); i++) {
                Vector<Double> row = new Vector<Double>();
                for (int j = 0; j < values.get(i).size(); j++) {
                    row.add(values.get(i).get(j) * m.values.get(i).get(j));
                }
                temp.add(row);
            }
        }
        values = temp;
    }

    public void transpose() {
        Vector<Vector<Double>> temp = new Vector<Vector<Double>>();
        for (int i = 0; i < values.get(0).size(); ++i) {
            Vector<Double> row = new Vector<Double>();
            for (int j = 0; j < values.size(); j++) {
                row.add(values.get(j).get(i));
            }
            temp.add(row);
        }
        values = temp;
    }

    public Matrix retTranspose() {
        Vector<Vector<Double>> temp = new Vector<Vector<Double>>();
        for (int i = 0; i < values.get(0).size(); ++i) {
            Vector<Double> row = new Vector<Double>();
            for (int j = 0; j < values.size(); j++) {
                row.add(values.get(j).get(i));
            }
            temp.add(row);
        }
        Matrix tempp = new Matrix(temp);
        return(tempp);
    }




}
