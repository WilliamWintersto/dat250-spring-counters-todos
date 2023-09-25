package no.hvl.dat250.rest.todos;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Rest-Endpoint for todos.
 */
@RestController
public class TodoController {

  public static final String TODO_WITH_THE_ID_X_NOT_FOUND = "Todo with the id %s not found!";
  public ArrayList<Todo> todos = new ArrayList<Todo>();
  public long id_counter = 0;

  public Long getNewID() {
    Long id = id_counter;
    id_counter++;
    return id;
  }

  @PostMapping("/todos")
  public Todo post(@RequestBody Todo todo) {
    Long id = getNewID();
    todo.setId(id);
    todos.add(todo);
    return todo;
  }
  @GetMapping("/todos")
  public ArrayList<Todo> getAll() {
    return todos;
  }
  @GetMapping("/todos/{todoId}")
  public Todo getOne(@PathVariable Long todoId) {
    for (Todo todo : todos) {
      if (Objects.equals(todo.getId(), todoId)) {
        return todo;
      }
    }
    return todoNotFound(todoId);
  }
  @PutMapping("/todos/{id}")
  public Todo put(@PathVariable Long id, @RequestBody Todo newTodo) {
    Long newId = newTodo.getId();
    String newDescription = newTodo.getDescription();
    String newSummary = newTodo.getSummary();
    for (Todo todo : todos) {
      if (Objects.equals(todo.getId(), newId)) {
        todo.setId(newId);
        todo.setDescription(newDescription);
        todo.setSummary(newSummary);
        return newTodo;
      }
    }
    return todoNotFound(newId);
  }
  @DeleteMapping("/todos/{todoId}")
  public Todo delete(@PathVariable Long todoId) {
    for (Todo todo : todos) {
      if (Objects.equals(todo.getId(), todoId)) {
        todos.remove(todo);
        return todo;
      }
    }
    return todoNotFound(todoId);
  }

  public Todo todoNotFound(Long id) {
    Todo error = new Todo();
    error.setDescription(String.format(TODO_WITH_THE_ID_X_NOT_FOUND, id));
    return error;
  }
}
