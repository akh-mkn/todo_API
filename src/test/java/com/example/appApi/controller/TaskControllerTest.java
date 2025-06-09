package com.example.appApi.controller;

import com.example.appApi.model.Task;
import com.example.appApi.repository.TaskRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskRepository taskRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldReturnAllTasks() throws Exception {
        Task task1 = new Task();
        task1.setTitle("Task 1");
        task1.setDescription("Description 1");
        task1.setDueDate(LocalDate.now());

        Mockito.when(taskRepository.findAll()).thenReturn(Arrays.asList(task1));

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Task 1"));
    }

    @Test
    public void shouldCreateTask() throws Exception {
        Task newTask = new Task();
        newTask.setTitle("New Task");
        newTask.setDescription("From test");
        newTask.setDueDate(LocalDate.now());

        Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenReturn(newTask);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTask)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("New Task"));
    }
}



// we have utlilised mockito, a mocking 
// framework for java to avoid real database or API calls. 

// A dummy task object was created above. Machine stores for testing. 
// Mockito is informed to return the dummy task. 

// We make fake HTTP requests which simulates a real one such as the POST request above to /tasks.
// The machine inspects the response. Was the status code created and was JSON returned correctly. 

// Both tests to [return and create a task] were successful. 