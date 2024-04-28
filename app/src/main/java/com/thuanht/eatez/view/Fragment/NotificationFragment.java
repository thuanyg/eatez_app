package com.thuanht.eatez.view.Fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.thuanht.eatez.Adapter.NotificationAdapter;
import com.thuanht.eatez.R;
import com.thuanht.eatez.database.database.AppDatabase;
import com.thuanht.eatez.database.entity.Notification;
import com.thuanht.eatez.databinding.FragmentNotificationBinding;
import com.thuanht.eatez.utils.DateUtils;
import com.thuanht.eatez.view.Dialog.DialogUtil;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment {
    private FragmentNotificationBinding binding;
    private List<Notification> notifications;
    private NotificationAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNotificationBinding.inflate(inflater, container, false);
        initUI();
        initData();
        eventHandler();
        return binding.getRoot();
    }

    private void eventHandler() {
        binding.btnRemoveAllNotification.setOnClickListener(v -> {
            DialogUtil.showStandardDialog(requireActivity(), "Delete all of notifications", "Do you want to remove these notifications?",
                    "Yes", "No", new DialogUtil.DialogClickListener() {
                        @Override
                        public void onPositiveButtonClicked(Dialog dialog) {
                            // Remove
                            notifications.clear();
                            AppDatabase.getInstance(requireContext()).notificationDAO().deleteAll();
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        }

                        @Override
                        public void onNegativeButtonClicked() {

                        }
                    });
        });
    }

    private void initData() {
        try {
            List<Notification> listFromDB = AppDatabase.getInstance(requireContext()).notificationDAO().selectAll();
            if (listFromDB != null){
                notifications.clear();
                notifications.addAll(listFromDB);
            }
            adapter.notifyDataSetChanged();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initUI() {
        if(AppDatabase.getInstance(requireContext()).notificationDAO().selectAll().size() == 0){
            binding.btnRemoveAllNotification.setVisibility(View.GONE);
        }

        notifications = new ArrayList<>();
        adapter = new NotificationAdapter(notifications, requireContext(), new NotificationAdapter.OnClickItemNotification() {
            @Override
            public void OnClick(Notification notification) {
                
            }

            @Override
            public void longClick(Notification notification) {
                DialogUtil.showStandardDialog(requireActivity(), "Delete notification", "Do you want to remove this notification?",
                        "Yes", "No", new DialogUtil.DialogClickListener() {
                            @Override
                            public void onPositiveButtonClicked(Dialog dialog) {
                                // Remove
                                notifications.remove(notification);
                                AppDatabase.getInstance(requireContext()).notificationDAO().delete(notification);
                                adapter.notifyDataSetChanged();
                                dialog.dismiss();
                            }

                            @Override
                            public void onNegativeButtonClicked() {

                            }
                        });
            }
        });
        binding.rcvNotification.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        binding.rcvNotification.setAdapter(adapter);
    }
}