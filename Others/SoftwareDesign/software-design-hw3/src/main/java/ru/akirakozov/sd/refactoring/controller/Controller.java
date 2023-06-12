package ru.akirakozov.sd.refactoring.controller;

import java.util.List;

import ru.akirakozov.sd.refactoring.entity.Product;

public interface Controller {
    int init();
    int addProducts(List<Product> products);
    List<Product> getAllProducts();
    List<Product> getMaxPriceProduct();
    List<Product> getMinPriceProduct();
    Long getPriceSum();
    Long getProductsCount();
}
