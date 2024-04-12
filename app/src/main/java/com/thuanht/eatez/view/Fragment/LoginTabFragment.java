package com.thuanht.eatez.view.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.thuanht.eatez.databinding.FragmentLoginTabBinding;
import com.thuanht.eatez.utils.KeyboardUtils;
import com.thuanht.eatez.view.Activity.HomeActivity;
import com.thuanht.eatez.view.Activity.LoginActivity;
import com.thuanht.eatez.viewModel.LoginViewModel;
import com.thuanht.eatez.R;

public class LoginTabFragment extends Fragment {
    private FragmentLoginTabBinding binding;
    private LoginViewModel viewModel;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginTabBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        // Quan sát LiveData để nhận thông báo lỗi email & password
        viewModel.getEmailError().observe(requireActivity(), errorString -> {
            binding.txtEmail.setError(errorString);
            binding.textInputLayout.setEndIconDrawable(null);
            binding.textInputLayout.setBoxStrokeColor(getResources().getColor(R.color.red_hat_red));
            binding.textInputLayout.setEndIconMode(TextInputLayout.END_ICON_NONE);
            binding.txtEmail.requestFocus();
        });
        return binding.getRoot();
    }

    public void eventHandler() {
        // Click button login
        String email = binding.txtEmail.getText().toString();
        String password = binding.txtPassword.getText().toString();
        binding.btnLogin.setOnClickListener(v -> {
            if (viewModel.validateData(email, password)) {
                viewModel.Login(email, password);
            }
        });
    }
}
