//package com.example.foodfindr2.fragments;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.foodfindr2.R;
//import com.example.foodfindr2.adapter.NotificationAdapter;
//import com.example.foodfindr2.utils.CurrentUser;
//import com.example.foodfindr2.viewmodel.NotificationViewModel;
//
//public class NotificationsFragment extends Fragment {
//
//    private RecyclerView rvNotifications;
//    private NotificationAdapter adapter;
//    private NotificationViewModel notificationViewModel;
//
//    @Nullable
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        // Inflate the correct fragment layout
//        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
//
//        // Initialize RecyclerView
//        rvNotifications = view.findViewById(R.id.rvNotifications);
//        rvNotifications.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        // Initialize Adapter
//        adapter = new NotificationAdapter(requireContext());
//        rvNotifications.setAdapter(adapter);
//
//        // Initialize ViewModel
//        notificationViewModel = new ViewModelProvider(this).get(NotificationViewModel.class);
//
//        // Fetch and observe notifications for the current user
//        int currentUserId = CurrentUser.getInstance().getUserId();
//        notificationViewModel.getUserNotificationsWithUsername(currentUserId).observe(getViewLifecycleOwner(), notifications -> {
//            if (notifications != null) {
//                // Update the RecyclerView with the notifications
//                adapter.submitList(notifications);
//            }
//        });
//
//        return view;
//    }
//}
