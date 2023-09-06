package by.bsuir.poit.dtalalaev.WT.labs.lab1.area;

import java.util.ArrayList;

public class MultisquareArea implements Area {

    private ArrayList<Area> areas = new ArrayList<>();

    public MultisquareArea(Area... areas) {
        for (Area area: areas
             ) {
            this.areas.add(area);
        }
    }

    public void addArea(Area area) {
        areas.add(area);
    }

    public MultisquareArea(ArrayList<Area> areas) {
        this.areas = areas;
    }

    public MultisquareArea() {
    }

    @Override
    public boolean isDotInArea(Dot dot) {
        for (Area a: areas
             ) {
            if(a.isDotInArea(dot)) {
                return true;
            }
        }
        return false;
    }
}
