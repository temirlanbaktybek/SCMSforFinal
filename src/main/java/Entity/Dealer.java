package Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Dealer {

    @NotNull
    @Id
    private int dealerId;

    @Max(25)
    private String productName;

    @NotNull
    @Positive
    private int quantity;


    private double price;

}
