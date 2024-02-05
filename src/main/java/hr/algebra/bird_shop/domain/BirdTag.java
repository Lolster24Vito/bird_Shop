package hr.algebra.bird_shop.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BirdTag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Bird tag name cannot be empty")
    private String name;

    /*
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "birdUser")
    private List<BirdOrder> userBirdOrders;
 */
   /* @ManyToMany(mappedBy = "birdTags")
    //@JoinColumn(name = "BIRD_USER_ID")
    private List<Bird> birds;
*/
    public BirdTag(String name) {
        this.name = name;
    }
}
