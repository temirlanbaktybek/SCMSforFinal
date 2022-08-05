package Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Container {

    @NotNull
    @Id
    private String product;

    @NotNull
    @Positive
    private int quantityProduct;

}
