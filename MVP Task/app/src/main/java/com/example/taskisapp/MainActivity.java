package com.example.taskisapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.taskisapp.Models.TodoItem;
import com.example.taskisapp.Presenters.TodoPresenter;
import com.example.taskisapp.R;
import com.example.taskisapp.UI.TodoAdapter;
import com.example.taskisapp.Views.TodoView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements TodoView {
    private TodoPresenter presenter;
    private TodoAdapter todoAdapter;
    private RecyclerView recyclerView;
    private EditText etTodoTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new TodoPresenter(this);
        etTodoTitle = findViewById(R.id.et_todo_title);
        recyclerView = findViewById(R.id.recycler_view);
        Button btnAddTodo = findViewById(R.id.btn_add_todo);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        todoAdapter = new TodoAdapter();
        recyclerView.setAdapter(todoAdapter);

        presenter.loadTodos();

        btnAddTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTodoTitle.getText().toString().trim();
                if (!title.isEmpty()) {
                    presenter.addTodo(title);
                    etTodoTitle.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a todo title", Toast.LENGTH_SHORT).show();
                }
            }
        });

        todoAdapter.setOnItemClickListener(new TodoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(TodoItem todo) {
                showUpdateTodoDialog(todo);
            }
        });
    }

    @Override
    public void showTodos(List<TodoItem> todos) {
        todoAdapter.setTodos(todos);
    }

    @Override
    public void showAddTodoDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_add_todo, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view)
                .setTitle("Add Todo")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText etTitle = view.findViewById(R.id.et_add_todo_title);
                        String title = etTitle.getText().toString().trim();
                        if (!title.isEmpty()) {
                            presenter.addTodo(title);
                        } else {
                            Toast.makeText(MainActivity.this, "Please enter a todo title", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", null);

        builder.create().show();
    }

    @Override
    public void showUpdateTodoDialog(final TodoItem todo) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_update_todo, null);

        final EditText etTitle = view.findViewById(R.id.et_update_todo_title);
        etTitle.setText(todo.getTitle());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view)
                .setTitle("Update Todo")
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newTitle = etTitle.getText().toString().trim();
                        if (!newTitle.isEmpty()) {
                            presenter.updateTodo(todo, newTitle);
                        } else {
                            Toast.makeText(MainActivity.this, "Please enter a todo title", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", null);

        builder.create().show();
    }

    @Override
    public void showTodoRemovedMessage() {
        Toast.makeText(this, "Todo removed", Toast.LENGTH_SHORT).show();
    }
}