package by.bsuir.poit.dtalalaev.WT.labs.lab1.area;

public class Lab1_2 {

    public static void main(String[] args) {
        MultisquareArea multisquareArea = new MultisquareArea();
        multisquareArea.addArea(new SquareArea(new Dot(-4, 5), new Dot(4, 0)));
        multisquareArea.addArea(new SquareArea(new Dot(-6, 0), new Dot(6, -3)));
        Dot testDot = new Dot(5, 5);
        System.out.println(testDot.toString() + " is " + (multisquareArea.isDotInArea(testDot) ? "in the are" : "not in the area"));
    }

}
