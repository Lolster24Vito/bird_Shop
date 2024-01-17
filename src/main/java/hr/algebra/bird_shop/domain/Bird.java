package hr.algebra.bird_shop.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Bird {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date createdAt=new Date();
  //  @NotNull validation will be done after database stuff
    private String name;
    private String description;
    private Boolean canFly;
    private BigDecimal price;
    @ManyToMany
    private List<BirdTag> birdTags;
    public Bird(String name, String description, Boolean canFly,BigDecimal price) {
        this.name = name;
        this.description = description;
        this.canFly = canFly;
        this.price=price;
    }

    public Bird(String name, String description, Boolean canFly, BigDecimal price, List<BirdTag> birdTags) {
        this.name = name;
        this.description = description;
        this.canFly = canFly;
        this.price = price;
        this.birdTags = birdTags;
    }
}
