package by.bsuir.poit.dtalalaev.WT.labs.lab1.area;

import java.util.Collection;
import java.util.Iterator;

public class SquareArea implements Area, Collection<Area> {
    private Dot leftUpAngleDot;
    private Dot rightDownAngleDot;

    public SquareArea(Dot leftUpAngleDot, Dot rightDownAngleDot) {
        this.leftUpAngleDot = leftUpAngleDot;
        this.rightDownAngleDot = rightDownAngleDot;
    }

    @Override
    public boolean isDotInArea(Dot dot) {
        return dot.getX() >= leftUpAngleDot.getX() && dot.getX() <= rightDownAngleDot.getX() &&
                dot.getY() <= leftUpAngleDot.getY() && dot.getY() >= rightDownAngleDot.getY();
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<Area> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(Area area) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends Area> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }
}
