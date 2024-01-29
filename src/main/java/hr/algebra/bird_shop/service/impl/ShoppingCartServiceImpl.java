package hr.algebra.bird_shop.service.impl;

import hr.algebra.bird_shop.domain.BirdShopItem;
import hr.algebra.bird_shop.domain.Cart;
import hr.algebra.bird_shop.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    Cart cart=new Cart();
    @Override
    public void addProduct(BirdShopItem shopItem) {
        cart.addItem(shopItem);
    }

    @Override
    public List<BirdShopItem> getProductsInCart() {
        return cart.getItems();
    }

    @Override
    public void checkout() {
    //buy?
    }

    @Override
    public BigDecimal getTotal() {
        return cart.getTotal();
    }
}
