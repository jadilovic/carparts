package com.avlija.parts.service;

import java.util.Comparator;

import com.avlija.parts.model.CarModel;
 
public class CarModelNameSorter implements Comparator<CarModel> 
{
    @Override
    public int compare(CarModel o1, CarModel o2) {
        return o2.getName().compareToIgnoreCase(o1.getName());
    }
}