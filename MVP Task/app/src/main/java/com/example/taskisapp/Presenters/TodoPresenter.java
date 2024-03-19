package com.example.taskisapp.Presenters;

import com.example.taskisapp.Models.TodoItem;
import com.example.taskisapp.Views.TodoView;

import java.util.ArrayList;
import java.util.List;

public class TodoPresenter {
    private TodoView view;
    public List<TodoItem> todos;

    public TodoPresenter(TodoView view) {
        this.view = view;
        this.todos = new ArrayList<>();
    }

    public void loadTodos() {
        view.showTodos(todos);
    }

    public void addTodo(String title) {
        TodoItem todo = new TodoItem(title);
        todos.add(todo);
        view.showTodos(todos);
    }

    public void updateTodo(TodoItem todo, String newTitle) {
        todo.setTitle(newTitle);
        view.showTodos(todos);
    }

    public void deleteTodo(TodoItem todo) {
        todos.remove(todo);
        view.showTodos(todos);
    }
}