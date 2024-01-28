package hr.algebra.bird_shop.service;

import hr.algebra.bird_shop.domain.BirdShopItem;
import hr.algebra.bird_shop.domain.Cart;

import java.math.BigDecimal;
import java.util.List;

public interface ShoppingCartService {
    void addProduct(BirdShopItem shopItem);

    //void removeProduct(Product product);

   // Map<Product, Integer> getProductsInCart();
   List<BirdShopItem> getProductsInCart();

    void checkout();

    BigDecimal getTotal();
}
