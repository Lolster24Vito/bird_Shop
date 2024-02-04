package hr.algebra.bird_shop.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BirdOrder {
    //List shopitems
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany(cascade = CascadeType.ALL)
    private List<BirdShopItem> birdShopItems;
    //DateTime kada
    private LocalDateTime createdTime;
    @Enumerated(EnumType.ORDINAL)
    private OrderPayType orderPayType;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "BIRD_USER_ID")
    private BirdUser birdUser;
    //User
    private String birdUserShippingAddress;
    private BigDecimal total;

    public BirdOrder(List<BirdShopItem> birdShopItems,BigDecimal total,LocalDateTime createdTime, OrderPayType orderPayType, BirdUser birdUser, String birdUserShippingAddress) {
        this.birdShopItems = birdShopItems;
        this.total=total;
        this.createdTime = createdTime;
        this.orderPayType = orderPayType;
        this.birdUser = birdUser;
        this.birdUserShippingAddress = birdUserShippingAddress;
    }
}
