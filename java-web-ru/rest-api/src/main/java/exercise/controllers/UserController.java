package exercise.controllers;

import io.javalin.http.Context;
import io.javalin.apibuilder.CrudHandler;
import io.ebean.DB;
import java.util.List;

import exercise.domain.User;
import exercise.domain.query.QUser;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.lang3.StringUtils;

public class UserController implements CrudHandler {

    public void getAll(Context ctx) {
        // BEGIN
        List<User> users = new QUser().findList();
// Преобразование списка сущностей в JSON представление
        String json = DB.json().toJson(users);
        ctx.json(json);
        // END
    };

    public void getOne(Context ctx, String id) {

        // BEGIN
        User user = new QUser()
                .id.equalTo(Integer.parseInt(id))
                .findOne();
// Аналогично можно представить в виде JSON отдельную сущность
        String json = DB.json().toJson(user);
        ctx.json(json);
        // END
    };

    public void create(Context ctx) {

        // BEGIN
        // Получение тела запроса

        String body = ctx.body();

// Из JSON представления, полученного из тела запроса, можно получить экземпляр модели

        User user = DB.json().toBean(User.class, body);
        user.save();

        // END
    };

    public void update(Context ctx, String id) {
        // BEGIN
        String body = ctx.body();
        User user = DB.json().toBean(User.class, body);
        new QUser()
                .id.equalTo(Integer.parseInt(id))
                .asUpdate()
                    .set("firstName", user.getFirstName())
                    .set("lastName", user.getLastName())
                    .set("email", user.getEmail())
                    .set("password", user.getPassword())
                .update();
        // END
    };

    public void delete(Context ctx, String id) {
        // BEGIN
        new QUser()
                .id.equalTo(Integer.parseInt(id))
                .delete();
        // END
    };
}
