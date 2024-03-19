package com.example.taskisapp.UI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskisapp.Models.TodoItem;
import com.example.taskisapp.R;

import java.util.ArrayList;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {
    private List<TodoItem> todos = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        return new TodoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        TodoItem todo = todos.get(position);
        holder.bind(todo);
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public void setTodos(List<TodoItem> todos) {
        this.todos = todos;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(TodoItem todo);
    }

    class TodoViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        CheckBox checkBox;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_todo_title);
            checkBox = itemView.findViewById(R.id.checkbox_completed);

            // Set click listener for the entire item view
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(todos.get(position));
                    }
                }
            });
        }

        public void bind(TodoItem todo) {
            tvTitle.setText(todo.getTitle());
            checkBox.setChecked(todo.isCompleted());
        }
    }
}