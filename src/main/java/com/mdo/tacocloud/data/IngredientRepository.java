package com.mdo.tacocloud.data;

import com.mdo.tacocloud.Ingredient;

import java.util.List;

public interface IngredientRepository {

    Iterable<Ingredient> findAll();

    Ingredient findOne(String id);

    Ingredient save(Ingredient ingredient);
}
