package Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class OrderDetails {
    @NotNull
    private String order_name;
    private List<String> DetailsName;
    @NotNull
    private double price;
}
