package com.mdo.tacocloud;

import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.Date;
import java.util.List;

@Data
public class Taco {

    private Long id;

    private Date createdAt;

    @NotNull
    @Size(min=5, message="Name must be 5 letters or greater")
    private String name;

    @Size(min=1, message="You must choose at least one ingredient")
    private List<String> ingredients;

}
