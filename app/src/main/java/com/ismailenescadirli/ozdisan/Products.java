package com.ismailenescadirli.ozdisan;

public class Products {

    private String link;
    private String product;
    private String stock;
    private String productDescription;

    public Products(String link, String product, String stock, String productDescription) {
        this.link = link;
        this.product = product;
        this.stock = stock;
        this.productDescription = productDescription;
    }

    public String getLink() {
        return link;
    }

    public String getProduct() {
        return product;
    }

    public String getStock() {
        return stock;
    }

    public String getProductDescription() {
        return productDescription;
    }

}


