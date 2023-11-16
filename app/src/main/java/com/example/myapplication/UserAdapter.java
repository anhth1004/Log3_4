package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private ArrayList<User> userList;
    private Context context;

    public UserAdapter(ArrayList<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = userList.get(position);
        holder.textViewName.setText(user.getName());
        holder.textViewDOB.setText(user.getDob());
        holder.textViewEmail.setText(user.getEmail());
        Glide.with(context)
                .load(user.getImagePath()) // Thay thế 'user.getImagePath()' với URL hoặc đường dẫn hình ảnh
// R.drawable.placeholder là hình ảnh placeholder
                .into(holder.imageViewProfile);

        // Cài đặt hình ảnh người dùng (nếu cần)
        // Ví dụ: holder.imageViewProfile.setImageResource(...);

        holder.buttonView.setOnClickListener(v -> {
            // Xử lý sự kiện khi nhấn nút View
        });

        holder.buttonEdit.setOnClickListener(v -> {
            // Chuyển đến UpdateActivity để cập nhật thông tin người dùng
            Intent intent = new Intent(context, UpdateActivity.class);
            intent.putExtra("USER_ID", user.getId());
            context.startActivity(intent);
        });

        holder.buttonDelete.setOnClickListener(v -> {
            // Xóa người dùng và cập nhật danh sách
            DatabaseHelper databaseHelper = new DatabaseHelper(context);
            databaseHelper.deleteUser(user.getId());
            userList.remove(position);
            notifyItemRemoved(position);
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewProfile;
        public TextView textViewName, textViewDOB, textViewEmail;
        public Button buttonView, buttonEdit, buttonDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            imageViewProfile = itemView.findViewById(R.id.imageViewProfile);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewDOB = itemView.findViewById(R.id.textViewDOB);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            buttonView = itemView.findViewById(R.id.buttonView);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}
