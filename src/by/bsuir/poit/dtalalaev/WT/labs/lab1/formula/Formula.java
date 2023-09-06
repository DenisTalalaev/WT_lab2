package by.bsuir.poit.dtalalaev.WT.labs.lab1.formula;

public class Formula {
    private double x;
    private double y;
    private double result;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Formula(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getResult() {
        return result;
    }

    public void calculate() throws Exception {
        double term1 = 1 + Math.pow(Math.sin(x + y), 2);
        double term2 = 2 + Math.abs(x - 2 * x / (1 + Math.pow(x, 2) * Math.pow(y, 2)));

        result = term1 / term2 + x;

    }

    public static void showFormulaTemplate() {
        System.out.println("1 + sin^2(x + y)");
        System.out.println("----------------------------- + x");
        System.out.println("2 + | x - (2x / [1 + x^2 y^2])");
    }


    public void showFormula() {
        System.out.printf("1 + sin^2(%f + y%f)       \n", x, y);
        System.out.printf("------------------------------------------- + %f\n", x);
        System.out.printf("2 + | %f - (2*%f / [1 + %f^2 * %f^2])\n", x, x, x, y);
    }
}
