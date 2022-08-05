package Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.lang.reflect.Array;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Order {

    @NotNull
    @Id
    private int orderId;
    @Max(20)
    private String orderName;
    @Max(20)
    private double orderPrice;

}
