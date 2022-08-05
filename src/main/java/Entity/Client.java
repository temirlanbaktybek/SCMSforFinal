package Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Client {

    @Id

    @NotNull
    private int clientId;

    @NotNull
    @Max(16)
    private String name;

    @NotNull
    private String surename;


    @NotNull
    @Positive
    private double money;

}
