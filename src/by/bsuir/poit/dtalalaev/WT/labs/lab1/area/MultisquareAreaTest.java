package by.bsuir.poit.dtalalaev.WT.labs.lab1.area;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MultisquareAreaTest {

    Area testArea = new MultisquareArea(
            new SquareArea(new Dot(-4, 5), new Dot(4, 0)),
            new SquareArea(new Dot(-6, 0), new Dot(6, -3))
    );

    @Test
    void isDotInAreaTest_1() {
        double x = 1;
        double y = 1;
        boolean answer = true;
        assertEquals(testArea.isDotInArea(new Dot(x, y)), answer);
    }

    @Test
    void isDotInAreaTest_2() {
        double x = 1.5;
        double y = 1.5;
        boolean answer = true;
        assertEquals(testArea.isDotInArea(new Dot(x, y)), answer);
    }

    @Test
    void isDotInAreaTest_3() {
        double x = 7;
        double y = 1.5;
        boolean answer = false;
        assertEquals(testArea.isDotInArea(new Dot(x, y)), answer);
    }

    @Test
    void isDotInAreaTest_4() {
        double x = 0;
        double y = 0;
        boolean answer = true;
        assertEquals(testArea.isDotInArea(new Dot(x, y)), answer);
    }

    @Test
    void isDotInAreaTest_5() {
        double x = 6;
        double y = 0;
        boolean answer = true;
        assertEquals(testArea.isDotInArea(new Dot(x, y)), answer);
    }

    @Test
    void isDotInAreaTest_6() {
        double x = 0;
        double y = 5.001;
        boolean answer = false;
        assertEquals(testArea.isDotInArea(new Dot(x, y)), answer);
    }
    @Test
    void isDotInAreaTest_7() {
        double x = -3.5;
        double y = 4.5;
        boolean answer = true;
        assertEquals(testArea.isDotInArea(new Dot(x, y)), answer);
    }

    @Test
    void isDotInAreaTest_8() {
        double x = -7;
        double y = 0;
        boolean answer = false;
        assertEquals(testArea.isDotInArea(new Dot(x, y)), answer);
    }

    @Test
    void isDotInAreaTest_9() {
        double x = 5;
        double y = -1.5;
        boolean answer = true;
        assertEquals(testArea.isDotInArea(new Dot(x, y)), answer);
    }

    @Test
    void isDotInAreaTest_10() {
        double x = 3.999;
        double y = 0;
        boolean answer = true;
        assertEquals(testArea.isDotInArea(new Dot(x, y)), answer);
    }

    @Test
    void isDotInAreaTest_11() {
        double x = -1;
        double y = -4;
        boolean answer = false;
        assertEquals(testArea.isDotInArea(new Dot(x, y)), answer);
    }

    @Test
    void isDotInAreaTest_12() {
        double x = -6;
        double y = -3.001;
        boolean answer = false;
        assertEquals(testArea.isDotInArea(new Dot(x, y)), answer);
    }

    @Test
    void isDotInAreaTest_13() {
        double x = 6.001;
        double y = -3;
        boolean answer = false;
        assertEquals(testArea.isDotInArea(new Dot(x, y)), answer);
    }

    @Test
    void isDotInAreaTest_14() {
        double x = 0;
        double y = -4;
        boolean answer = false;
        assertEquals(testArea.isDotInArea(new Dot(x, y)), answer);
    }

    @Test
    void isDotInAreaTest_15() {
        double x = 4;
        double y = 0.5;
        boolean answer = true;
        assertEquals(testArea.isDotInArea(new Dot(x, y)), answer);
    }

    @Test
    void isDotInAreaTest_16() {
        double x = -4.001;
        double y = 0.001;
        boolean answer = false;
        assertEquals(testArea.isDotInArea(new Dot(x, y)), answer);
    }
    @Test
    void isDotInAreaTest_17() {
        double x = -4;
        double y = 5;
        boolean answer = true;
        assertEquals(testArea.isDotInArea(new Dot(x, y)), answer);
    }

    @Test
    void isDotInAreaTest_18() {
        double x = 4;
        double y = 0;
        boolean answer = true;
        assertEquals(testArea.isDotInArea(new Dot(x, y)), answer);
    }

    @Test
    void isDotInAreaTest_19() {
        double x = 0;
        double y = -3;
        boolean answer = true;
        assertEquals(testArea.isDotInArea(new Dot(x, y)), answer);
    }

    @Test
    void isDotInAreaTest_20() {
        double x = 6;
        double y = -3;
        boolean answer = true;
        assertEquals(testArea.isDotInArea(new Dot(x, y)), answer);
    }


}