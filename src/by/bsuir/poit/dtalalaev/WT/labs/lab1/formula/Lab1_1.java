package by.bsuir.poit.dtalalaev.WT.labs.lab1.formula;


import java.util.Scanner;

public class Lab1_1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter x value");
        double x = scanner.nextDouble();
        System.out.println("Enter y value");
        double y = scanner.nextDouble();
        Formula formula = new Formula(x, y);
        try{
            formula.calculate();
            System.out.println("result of calculating");
            formula.showFormula();
            System.out.println(formula.getResult());
        } catch (Exception e){
            System.out.println("Error with calculating");
            e.printStackTrace();
        }
    }

}
