package com.example.taskisapp.Views;

import com.example.taskisapp.Models.TodoItem;

import java.util.List;

public interface TodoView {
    void showTodos(List<TodoItem> todos);
    void showAddTodoDialog();
    void showUpdateTodoDialog(TodoItem todo);
    void showTodoRemovedMessage();

}

