package exercise;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import io.javalin.Javalin;
import io.ebean.DB;

import exercise.domain.User;
import exercise.domain.query.QUser;
import io.ebean.Database;

class AppTest {

    private static Javalin app;
    private static String baseUrl;

    // BEGIN
    @BeforeAll
    public static void beforeAll() {
        // Получаем инстанс приложения
        app = App.getApp();
        // Запускаем приложение на рандомном порту
        app.start(0);
        // Получаем порт, на которм запустилось приложение
        int port = app.port();
        // Формируем базовый URL
        baseUrl = "http://localhost:" + port;
    }

    // Метод выполнится после окончания всех тестов в классе
    @AfterAll
    public static void afterAll() {
        // Останавливаем приложение
        app.stop();
    }
    // END

    // Между тестами база данных очищается
    // Благодаря этому тесты не влияют друг на друга
    @BeforeEach
    void beforeEach() {
        Database db = DB.getDefault();
        db.truncate("users");
        User existingUser = new User("Wendell", "Legros", "a@a.com", "123456");
        existingUser.save();
    }

    @Test
    void testUsers() {

        // Выполняем GET запрос на адрес http://localhost:port/users
        HttpResponse<String> response = Unirest
            .get(baseUrl + "/users")
            .asString();
        // Получаем тело ответа
        String content = response.getBody();

        // Проверяем код ответа
        assertThat(response.getStatus()).isEqualTo(200);
        // Проверяем, что страница содержит определенный текст
        assertThat(response.getBody()).contains("Wendell Legros");
    }

    @Test
    void testNewUser() {

        HttpResponse<String> response = Unirest
            .get(baseUrl + "/users/new")
            .asString();

        assertThat(response.getStatus()).isEqualTo(200);
    }

    // BEGIN
    @Test
    void testCreateUserPositive() {
        String firstName = "Ivan";
        String lastName = "Ivanov";
        String email = "ivanovi@mail.ru";
        String password = "12345";

        HttpResponse<String> responsePost = Unirest
                // POST запрос на URL
                .post(baseUrl + "/users")
                // Устанавливаем значения полей
                .field("firstName", firstName)
                .field("lastName", lastName)
                .field("email", email)
                .field("password", password)
        // Выполняем запрос и получаем тело ответ с телом в виде строки
                .asString();

        // Проверяем статус ответа
        assertThat(responsePost.getStatus()).isEqualTo(302);

        // Проверяем, что компания добавлена в БД
        User expectedUser = new QUser()
                .lastName.equalTo("Ivanov")
                .firstName.equalTo("Ivan")
                .findOne();

        assertThat(expectedUser).isNotNull();
        assertThat(expectedUser.getFirstName()).isEqualTo(firstName);
        assertThat(expectedUser.getLastName()).isEqualTo(lastName);
        assertThat(expectedUser.getEmail()).isEqualTo(email);
        assertThat(expectedUser.getPassword()).isEqualTo(password);

    }

    @Test
    void testCreateUserNegotive() {
        String firstName = "Ivan";
        String lastName = "Ivanov";
        String email = "ivanovi@mail.ru";
        String password = "12345";

        String incorrectEmail1 = "ivanovi";
        String incorrectEmail2 = "ivanovi@asdas";
        String incorrectPassword = "123";

        String empty = "";

        HttpResponse<String> responsePostEmptyFirstName = Unirest
                // POST запрос на URL
                .post(baseUrl + "/users")
                // Устанавливаем значения полей
                .field("firstName", empty)
                .field("lastName", lastName)
                .field("email", email)
                .field("password", password)
                // Выполняем запрос и получаем тело ответ с телом в виде строки
                .asString();

        // Проверяем статус ответа
        assertThat(responsePostEmptyFirstName.getStatus()).isEqualTo(422);

        // Проверяем, что компания добавлена в БД
        User expectedUserCheckFirstName = new QUser()
                .lastName.equalTo(lastName)
                .email.equalTo(email)
                .findOne();

        // Так можно получить содержимое тела ответа
        String content1 = responsePostEmptyFirstName.getBody();
        // И проверить, что оно содержит определённую строку
        assertThat(content1).contains(lastName);
        assertThat(content1).contains(email);
        assertThat(content1).contains(password);


        assertThat(expectedUserCheckFirstName).isNull();


        HttpResponse<String> responsePostEmptyLastName = Unirest
                // POST запрос на URL
                .post(baseUrl + "/users")
                // Устанавливаем значения полей
                .field("firstName", firstName)
                .field("lastName", empty)
                .field("email", email)
                .field("password", password)
                // Выполняем запрос и получаем тело ответ с телом в виде строки
                .asString();

        // Проверяем статус ответа
        assertThat(responsePostEmptyLastName.getStatus()).isEqualTo(422);

        // Проверяем, что компания добавлена в БД
        User expectedUserCheckLastName = new QUser()
                .firstName.equalTo(firstName)
                .email.equalTo(email)
                .findOne();

        assertThat(expectedUserCheckLastName).isNull();
        String content2 = responsePostEmptyLastName.getBody();
        assertThat(content2).contains(firstName);
        assertThat(content2).contains(email);
        assertThat(content2).contains(password);

        HttpResponse<String> responsePostEmptyEmail = Unirest
            // POST запрос на URL
            .post(baseUrl + "/users")
            // Устанавливаем значения полей
            .field("firstName", firstName)
            .field("lastName", lastName)
            .field("email", empty)
            .field("password", password)
            // Выполняем запрос и получаем тело ответ с телом в виде строки
            .asString();

        // Проверяем статус ответа
        assertThat(responsePostEmptyEmail.getStatus()).isEqualTo(422);

        // Проверяем, что компания добавлена в БД
        User expectedUserCheckEmail1 = new QUser()
                .firstName.equalTo(firstName)
                .lastName.equalTo(lastName)
                .findOne();

        assertThat(expectedUserCheckEmail1).isNull();
        String content3 = responsePostEmptyEmail.getBody();
        assertThat(content3).contains(firstName);
        assertThat(content3).contains(lastName);
        assertThat(content3).contains(password);

        HttpResponse<String> responsePostIncorrectEmail1 = Unirest
                // POST запрос на URL
                .post(baseUrl + "/users")
                // Устанавливаем значения полей
                .field("firstName", firstName)
                .field("lastName", lastName)
                .field("email", incorrectEmail1)
                .field("password", password)
                // Выполняем запрос и получаем тело ответ с телом в виде строки
                .asString();

        // Проверяем статус ответа
        assertThat(responsePostIncorrectEmail1.getStatus()).isEqualTo(422);

        // Проверяем, что компания добавлена в БД
        User expectedUserCheckEmail2 = new QUser()
                .firstName.equalTo(firstName)
                .lastName.equalTo(lastName)
                .findOne();

        assertThat(expectedUserCheckEmail2).isNull();
        String content4 = responsePostIncorrectEmail1.getBody();
        assertThat(content4).contains(firstName);
        assertThat(content4).contains(lastName);
        assertThat(content4).contains(incorrectEmail1);
        assertThat(content4).contains(password);

        HttpResponse<String> responsePostIncorrectEmail2 = Unirest
                // POST запрос на URL
                .post(baseUrl + "/users")
                // Устанавливаем значения полей
                .field("firstName", firstName)
                .field("lastName", lastName)
                .field("email", incorrectEmail2)
                .field("password", password)
                // Выполняем запрос и получаем тело ответ с телом в виде строки
                .asString();

        // Проверяем статус ответа
        assertThat(responsePostIncorrectEmail2.getStatus()).isEqualTo(422);

        // Проверяем, что компания добавлена в БД
        User expectedUserCheckEmail3 = new QUser()
                .firstName.equalTo(firstName)
                .lastName.equalTo(lastName)
                .findOne();

        assertThat(expectedUserCheckEmail3).isNull();
        String content5 = responsePostIncorrectEmail2.getBody();
        assertThat(content5).contains(firstName);
        assertThat(content5).contains(lastName);
        assertThat(content5).contains(incorrectEmail2);
        assertThat(content5).contains(password);

        HttpResponse<String> responsePostIncorrectPassword = Unirest
                // POST запрос на URL
                .post(baseUrl + "/users")
                // Устанавливаем значения полей
                .field("firstName", firstName)
                .field("lastName", lastName)
                .field("email", email)
                .field("password", incorrectPassword)
                // Выполняем запрос и получаем тело ответ с телом в виде строки
                .asString();

        // Проверяем статус ответа
        assertThat(responsePostIncorrectPassword.getStatus()).isEqualTo(422);

        // Проверяем, что компания добавлена в БД
        User expectedUserCheckPassword = new QUser()
                .firstName.equalTo(firstName)
                .lastName.equalTo(lastName)
                .findOne();

        assertThat(expectedUserCheckPassword).isNull();
        String content6 = responsePostIncorrectPassword.getBody();
        assertThat(content6).contains(firstName);
        assertThat(content6).contains(lastName);
        assertThat(content6).contains(email);
        assertThat(content6).contains(incorrectPassword);
}
    // END
}
