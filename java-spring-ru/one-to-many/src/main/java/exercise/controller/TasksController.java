package exercise.controller;

import java.util.List;

import exercise.dto.TaskCreateDTO;
import exercise.dto.TaskDTO;
import exercise.dto.TaskUpdateDTO;
import exercise.mapper.TaskMapper;
import exercise.mapper.UserMapper;
import exercise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import exercise.exception.ResourceNotFoundException;
import exercise.repository.TaskRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TasksController {
    // BEGIN
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private UserMapper userMapper;

    @GetMapping(path = "")
    public List<TaskDTO> index() {
        return taskRepository.findAll()
            .stream()
            .map(task -> taskMapper.map(task))
            .toList();
    }

    @GetMapping(path = "/{id}")
    public TaskDTO show(@PathVariable long id) {
        var task = taskRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Task with id = " + id + " not found"));
        return taskMapper.map(task);
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO create(@Valid @RequestBody TaskCreateDTO taskCreateDTO) {
        var assignee = userRepository.findById(taskCreateDTO.getAssigneeId())
            .orElseThrow(() -> new ResourceNotFoundException("User with id = " + taskCreateDTO.getAssigneeId() + " not found"));

        var newTask = taskMapper.map(taskCreateDTO);
        assignee.addTask(newTask);

        taskRepository.save(newTask);
        return taskMapper.map(newTask);
    }

    @PutMapping(path = "/{id}")
    public TaskDTO update(@Valid @RequestBody TaskUpdateDTO taskUpdateDTO, @PathVariable long id) {
        var assignee = userRepository.findById(taskUpdateDTO.getAssigneeId())
            .orElseThrow(() -> new ResourceNotFoundException("User with id = " + taskUpdateDTO.getAssigneeId() + " not found"));

        var task = taskRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Task with id = " + id + " not found"));

        //var assigneeOld = userRepository.findById(task.getAssignee().getId())
        //        .orElseThrow(() -> new ResourceNotFoundException("User (old) with id = " + task.getAssignee().getId() + " not found"));
        //assigneeOld.removeTask(task);

        assignee.addTask(task);
        taskMapper.update(taskUpdateDTO, task);
        taskRepository.save(task);

        return taskMapper.map(task);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable long id) {
        taskRepository.deleteById(id);
    }
    // END
}
