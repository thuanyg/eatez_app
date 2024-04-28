package com.thuanht.eatez.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thuanht.eatez.R;
import com.thuanht.eatez.database.entity.Notification;
import com.thuanht.eatez.databinding.ItemNotificationBinding;
import com.thuanht.eatez.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {
    private List<Notification> notifications;
    private Context context;
    private OnClickItemNotification listener;

    public NotificationAdapter(List<Notification> notifications, Context context, OnClickItemNotification listener) {
        this.notifications = notifications;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNotificationBinding binding = ItemNotificationBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Notification notification = notifications.get(position);
        holder.binding.tvTitleNotification.setText(notification.getTitle());
        holder.binding.tvNotification.setText(notification.getMessage());
        holder.itemView.setOnClickListener(v -> listener.OnClick(notification));
        holder.itemView.setOnLongClickListener(v -> {
            listener.longClick(notification);
            return false;
        });
        Glide.with(context).load(notification.getImageLink())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.binding.imageNotification);
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                holder.binding.tvDateNotitfication.setText(DateUtils.convertToRelativeTime(notification.getDate()));
            }
        } catch (Exception e) {

        }
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ItemNotificationBinding binding;

        public MyViewHolder(@NonNull ItemNotificationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnClickItemNotification {
        void OnClick(Notification notification);

        void longClick(Notification notification);
    }
}
