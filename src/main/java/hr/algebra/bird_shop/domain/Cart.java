package hr.algebra.bird_shop.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<BirdShopItem> items;
    private BigDecimal total;
    public Cart(){
        items=new ArrayList<>();
        total=BigDecimal.valueOf(0);
    }

    public List<BirdShopItem> getItems() {
        return items;
    }

    public BirdShopItem getItem(Bird birdToGet){
        for (BirdShopItem item : items){
            if(item.getBirdProduct().getId() == birdToGet.getId()){
                return item;
            }
        }
        return null;
    }

    public void removeItemWithQuantity(BirdShopItem itemToFind){ // throws ProductNotFoundException
        BirdShopItem item = getItem(itemToFind.getBirdProduct());

        if (item != null){
            Integer originalQuantity=item.getQuantity();
            if(originalQuantity- itemToFind.getQuantity()<=0) {
                items.remove(item);
            }
            else{
                item.setQuantity(originalQuantity-itemToFind.getQuantity());
            }
        }
        getTotal();
    }
    public void addItem(BirdShopItem addItem){
        BirdShopItem item=getItem(addItem.getBirdProduct());
        if(item!=null){
            Integer originalQuantity=item.getQuantity();
            item.setQuantity(originalQuantity+ addItem.getQuantity());
        }
        else{
            items.add(addItem);
        }
        getTotal();
    }

    public void removeItem(Bird removeBird){ // throws ProductNotFoundException
        BirdShopItem item = getItem(removeBird);

        if (item != null){
            items.remove(item);
        }
        getTotal();
    }


    public BigDecimal getTotal() {
        total = BigDecimal.ZERO;
        for (BirdShopItem item : items) {
           total=total.add(item.getSumTotal());
        }
        return total;
    }
    public void clear(){
        items.clear();
        total = BigDecimal.valueOf(0);
    }


}
