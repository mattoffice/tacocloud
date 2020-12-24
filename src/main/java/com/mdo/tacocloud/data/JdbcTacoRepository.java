package com.mdo.tacocloud.data;

import com.mdo.tacocloud.Ingredient;
import com.mdo.tacocloud.Taco;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

@Repository
public class JdbcTacoRepository implements TacoRepository {

    private JdbcTemplate jdbc;

    public JdbcTacoRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Taco save(Taco taco) {
        /* when you insert into the Taco table, and id is generated for the row - you can then
         use that id when you write the taco's ingredients to the Taco_Ingredients table (this table
         uses the taco id as a foreign key to link an ingredient to a particular taco) */
        long id = saveTacoInfo(taco);
        taco.setId(id);

        for (String ingredient : taco.getIngredients()) {
            saveIngredientToTaco(ingredient, id);
        }

        return taco;
    }

    private long saveTacoInfo(Taco taco) {
        /* insert a new taco object into the Taco table, get back the auto-generated primary key
        id and return it.  The update() method doesn't help you access the generated id, so another
        approach is required here - this is to use the update overload which accepts a PreparedStatementCreator
        and a KeyHolder (which allows you to get at the generated id).  Also, Before persisting the taco object,
        you will have to create the createdAt field which shows when exactly the taco was created,
        and saved in the db */
        taco.setCreatedAt(new Date());
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
                "insert into Taco (name, createdAt) values (?, ?)",
                Types.VARCHAR, Types.BIGINT);

        pscf.setReturnGeneratedKeys(true); // otherwise, the getKey() method below returns null

        PreparedStatementCreator psc =  pscf.newPreparedStatementCreator(
                        Arrays.asList(
                                taco.getName(),
                                taco.getCreatedAt().getTime()
                        ));

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(psc, keyHolder);

        return keyHolder.getKey().longValue();
    }

    private void saveIngredientToTaco(String ingredient, long tacoId) {
        jdbc.update("insert into Taco_Ingredients (taco, ingredient) values (?, ?)",
                tacoId, ingredient);
    }
}