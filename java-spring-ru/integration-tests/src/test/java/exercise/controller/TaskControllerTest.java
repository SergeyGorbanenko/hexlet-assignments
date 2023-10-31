package exercise.controller;

import org.junit.jupiter.api.Test;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import org.instancio.Instancio;
import org.instancio.Select;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.util.HashMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.datafaker.Faker;
import exercise.repository.TaskRepository;
import exercise.model.Task;

// END
@SpringBootTest
@AutoConfigureMockMvc
// BEGIN
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Faker faker;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ObjectMapper om;

    public Task createTestTask() {
        return Instancio.of(Task.class)
            .ignore(Select.field(Task::getId))
            .supply(Select.field(Task::getTitle), () -> faker.lorem().word())
            .supply(Select.field(Task::getDescription), () -> faker.lorem().paragraph())
            .create();
    }

    @Test
    public void testShow() throws Exception {
        var task = createTestTask();

        taskRepository.save(task);

        var request = get("/tasks/" + task.getId());

        var result = mockMvc.perform(request)
            .andExpect(status().isOk())
            .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).and(
            a -> a.node("id").isEqualTo(task.getId()),
            a -> a.node("title").isEqualTo(task.getTitle()),
            a -> a.node("description").isEqualTo(task.getDescription())
        );
    }

    @Test
    public void testShowNotFound() throws Exception {
        mockMvc.perform(get("/tasks/999999"))
            .andExpect(status().isNotFound());
    }

    @Test
    public void testCreate() throws Exception {
        var task = createTestTask();

        var request = post("/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(om.writeValueAsString(task));

        var result = mockMvc.perform(request)
            .andExpect(status().isCreated())
            .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).and(
            //a -> a.node("id").isEqualTo(task.getId()),
            a -> a.node("title").isEqualTo(task.getTitle()),
            a -> a.node("description").isEqualTo(task.getDescription())
        );
    }

    @Test
    public void testUpdate() throws Exception {
        var task = createTestTask();

        taskRepository.save(task);

        var data = new HashMap<>();
        data.put("title", "title1");
        data.put("description", "description1");

        var request = put("/tasks/" + task.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(om.writeValueAsString(data));

        var result = mockMvc.perform(request)
            .andExpect(status().isOk())
            .andReturn();

        var body = result.getResponse().getContentAsString();

        var updatedTask = taskRepository.findById(task.getId()).get();

        assertThatJson(body).and(
            a -> a.node("id").isEqualTo(updatedTask.getId()),
            a -> a.node("title").isEqualTo(updatedTask.getTitle()),
            a -> a.node("description").isEqualTo(updatedTask.getDescription())
        );
    }

    @Test
    public void testDelete() throws Exception {
        var task = createTestTask();

        taskRepository.save(task);

        var request = delete("/tasks/" + task.getId());

        var result = mockMvc.perform(request)
            .andExpect(status().isOk());

        assertThat(taskRepository.findById(task.getId()).isEmpty()).isTrue();        
    }
// END
}
