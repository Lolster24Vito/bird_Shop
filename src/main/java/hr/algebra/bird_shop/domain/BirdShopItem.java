package hr.algebra.bird_shop.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
@JsonIgnoreProperties(ignoreUnknown = true)
public class BirdShopItem {
    private Bird birdProduct;

    private Integer quantity;


    public BigDecimal getSumTotal() {
        return birdProduct.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

    public Bird getBirdProduct() {
        return birdProduct;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setBirdProduct(Bird birdProduct) {
        this.birdProduct = birdProduct;
    }
}
