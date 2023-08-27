package exercise;

import com.fasterxml.jackson.databind.ObjectMapper;
import exercise.dto.PersonDto;
import exercise.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;
import com.github.database.rider.junit5.api.DBRider;
import com.github.database.rider.core.api.dataset.DataSet;

@SpringBootTest
// При тестировании можно вообще не запускать сервер
// Spring будет обрабатывать HTTP запрос и направлять его в контроллер
// Код вызывается точно так же, как если бы он обрабатывал настоящий запрос
// Такие тесты обходятся дешевле в плане ресурсов
// Для этого нужно внедрить MockMvc

// BEGIN
@AutoConfigureMockMvc
// END

// Чтобы исключить влияние тестов друг на друга,
// каждый тест будет выполняться в транзакции.
// После завершения теста транзакция автоматически откатывается
@Transactional
// Для наполнения БД данными перед началом тестирования
// воспользуемся возможностями библиотеки Database Rider
@DBRider
// Файл с данными для тестов (фикстуры)
@DataSet("people.yml")
public class AppTest {

    // Автоматическое создание экземпляра класса MockMvc
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PersonRepository repository;

    private static ObjectMapper mapper = new ObjectMapper();

    @Test
    void testRootPage() throws Exception {
        // Выполняем запрос и получаем ответ
        MockHttpServletResponse response = mockMvc
            // Выполняем GET запрос по указанному адресу
            .perform(get("/"))
            // Получаем результат MvcResult
            .andReturn()
            // Получаем ответ MockHttpServletResponse из класса MvcResult
            .getResponse();

        // Проверяем статус ответа
        assertThat(response.getStatus()).isEqualTo(200);
        // Проверяем, что ответ содержит определенный текст
        assertThat(response.getContentAsString()).contains("Welcome to Spring");
    }

    @Test
    void testCreatePerson() throws Exception {
        PersonDto dto = new PersonDto();
        dto.setFirstName("Jackson");
        dto.setLastName("Bind");
        dto.setEmail("a@a.com");
        mockMvc.perform(
                // Выполняем POST-запрос
                post("/people")
                    // Устанавливаем тип содержимого тела запроса
                    .contentType(MediaType.APPLICATION_JSON)
                    // Добавляем содержимое тела
                    .content(mapper.writeValueAsString(dto))
        )
                // Убеждаемся, что запрос выполнился успешно
                .andExpect(status().isOk());

        // Проверяем, что сущность добавилась в базу
        assertNotNull(repository.findByEmail("a@a.com"));
    }

    @Test
    void testGetPerson() throws Exception {
        var existingUserEmail = "jack@mail.com";
        // Используем утилиту для получения идентификатора пользователя по его email
        // Так как мы не знаем заранее, с каким идентификатором сущность создастся в базе данных
        var existingUserId = TestUtils.getUserIdByEmail(mockMvc, existingUserEmail);
        MockHttpServletResponse response = mockMvc
            .perform(get("/people/{id}", existingUserId))
            .andReturn()
            .getResponse();

        // Проверяем статус ответа
        assertThat(response.getStatus()).isEqualTo(200);
        // Проверяем, что тип значеня в ответе - json
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        // Проверяем, что тело ответа содержит пользователя
        assertThat(response.getContentAsString()).contains(existingUserEmail);
    }

    // BEGIN
    @Test
    void testGetAllPersons() throws Exception {
        var expectedUserFirstName = "John";
        var expecredUserLastName = "Doe";
        var expectedUserEmail = "jassika@yahoo.com";

        MockHttpServletResponse response = mockMvc
                .perform(get("/people"))
                .andReturn()
                .getResponse();


        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());

        assertThat(response.getContentAsString()).contains(expectedUserFirstName);
        assertThat(response.getContentAsString()).contains(expecredUserLastName);
        assertThat(response.getContentAsString()).contains(expectedUserEmail);
    }

    @Test
    void testUpdatePerson() throws Exception {
        var changedUserEmail = "jack@mail.com";
        var changedUserId = TestUtils.getUserIdByEmail(mockMvc, changedUserEmail);

        var existingUserFirstName = "Donald";
        var existingUserLastName = "Trump";
        var existingUserEmail = "DTrump@ya.com";

        PersonDto dto = new PersonDto();
        dto.setFirstName(existingUserFirstName);
        dto.setLastName(existingUserLastName);
        dto.setEmail(existingUserEmail);

        mockMvc.perform(
                patch("/people/{id}", changedUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        MockHttpServletResponse response = mockMvc
                .perform(get("/people/{id}", changedUserId))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString()).contains(existingUserFirstName);
        assertThat(response.getContentAsString()).contains(existingUserLastName);
        assertThat(response.getContentAsString()).contains(existingUserEmail);
    }

    @Test
    void TestDeletePeople() throws Exception {
        var deletedUserEmail = "jack@mail.com";
        var deletedUserId = TestUtils.getUserIdByEmail(mockMvc, deletedUserEmail);

        mockMvc.perform(
                delete("/people/{id}", deletedUserId))
                    .andExpect(status().isOk());

        MockHttpServletResponse response = mockMvc
                .perform(get("/people/{id}", deletedUserId))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertNull(repository.findById(deletedUserId));
        assertNull(repository.findByEmail(deletedUserEmail));
    }
    // END
}
