package cz.intercity.smellsphishy.ai.utils;

public class Matrix {
    private Matrix(){}; // NO DEFAULT CONSTRUCTOR FOR YOU

    public Matrix(int height, int width) {
        this.width = width;
        this.height = height;
        this.array = new double[height][width];
    }

    public Matrix(double[][] contents){
        this.array = contents;
        this.width = contents[0].length; //should work, since length should be the same for each row
        this.height = contents.length;
    }

    public Matrix(double[] contents){
        this.width = contents.length;
        this.height = 1;
        this.array = new double[height][width];

        this.array[0] = contents;
    }

    //Scalar multiplication
    public Matrix multiply(double scalar){
        Matrix result = new Matrix(this.height, this.width);

        for(int i = 0; i<height; i++){
            for(int j = 0; j<width; j++){
                result.array[i][j] = this.array[i][j] * scalar;
            }
        }

        return result;

    }

    //Multiplication of individual elements using a multiplication matrix, NOT A DOT PRODUCT
    public Matrix multiply(Matrix matrix){
        assert(matrix.width == this.width && matrix.height == this.height) : "Multiplication not possible: Incompatible matrix sizes";

        Matrix result = new Matrix(this.height, this.width);

        for(int i = 0; i<height; i++){
            for(int j = 0; j<width; j++){
                result.array[i][j] = this.array[i][j] * matrix.array[i][j];
            }
        }

        return result;
    }

    public Matrix add(Matrix matrix){
        assert(matrix.width == this.width && matrix.height == this.height) : "Multiplication not possible: Incompatible matrix sizes";

        Matrix result = new Matrix(this.height, this.width);

        for(int i = 0; i<height; i++){
            for(int j = 0; j<width; j++){
                result.array[i][j] = this.array[i][j] + matrix.array[i][j];
            }
        }

        return result;
    }

    public Matrix subtract(Matrix matrix){
        assert(matrix.width == this.width && matrix.height == this.height) : "Multiplication not possible: Incompatible matrix sizes";

        Matrix result = new Matrix(this.height, this.width);

        for(int i = 0; i<height; i++){
            for(int j = 0; j<width; j++){
                result.array[i][j] = this.array[i][j] - matrix.array[i][j];
            }
        }

        return result;
    }

    //Dot product
    public Matrix dot(Matrix matrix){
        assert(this.width == matrix.height);

        int i, j, h;
        double prod = 0; //Dot product partial result

        Matrix result = new Matrix(this.height, matrix.width); //This is correct, I swear :)

        for(i = 0; i<height; i++){
            for(j = 0; j<matrix.width; j++){
                for(h = 0; h<width; h++){
                    prod += array[i][h]*matrix.array[h][j];
                }

                result.array[i][j] = prod;
                prod = 0;
            }
        }

        return result;
    }

    public Matrix transpose(){
        Matrix result = new Matrix(this.height, this.width);
        for(int i = 0; i<this.width; i++){
            for(int j = 0; j<this.height; j++){
                result.array[i][j] = this.array[j][i];
            }
        }

        return result;
    }

    public Matrix applyFunction(MathFunction f){
        Matrix result = new Matrix(this.height, this.width);
        for(int i = 0; i<this.height; i++){
            for(int j = 0; j<this.width; j++){
                result.array[i][j] = f.getNumResult(result.array[i][j]);
            }
        }

        return result;
    }

    public String print(){
        StringBuilder sb = new StringBuilder();
        sb.append("Matrix (Width: ").append(this.width).append("; Height: ").append(this.height).append(")\n");
        sb.append("{\n");

        for(int i = 0; i<this.height; i++) {
            sb.append("\t");
            for(int j = 0; j<this.width; j++){
                sb.append(this.array[i][j]).append(" ");
            }
            sb.append("\n");
        }

        sb.append("}\n");
        return sb.toString();
    }

    /*
      Member vars
     */
    private double[][] array;
    private int height;
    private int width;

}
