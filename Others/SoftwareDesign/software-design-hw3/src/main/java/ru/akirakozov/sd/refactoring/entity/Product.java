package ru.akirakozov.sd.refactoring.entity;

public class Product {
    private String name;
    private long price;

    public Product(String name, long price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public boolean isSame(Product that) {
        return this.name.equals(that.name) && this.price == that.price;
    }
}
