package hr.algebra.bird_shop.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
  @NotBlank(message = "Bird name must not be empty!")
  private String name;
    @NotBlank(message = "Bird description must not be empty!")
    private String description;
    private Boolean canFly;
    @NotNull(message = "Price must not be left empty")
    @Min(value = 0,message = "Bird prices should be higher than 0")
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
