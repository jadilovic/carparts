package com.avlija.parts.service;

import java.util.Comparator;

import com.avlija.parts.model.ProductMaker;
 
public class ProductMakerNameSorter implements Comparator<ProductMaker> 
{
    @Override
    public int compare(ProductMaker o1, ProductMaker o2) {
        return o2.getName().compareToIgnoreCase(o1.getName());
    }
}