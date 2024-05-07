package com.thuanht.eatez.view.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import com.thuanht.eatez.databinding.FragmentSignupTabBinding;
import com.thuanht.eatez.interfaceEvent.LoginCallback;
import com.thuanht.eatez.interfaceEvent.RegisterCallback;
import com.thuanht.eatez.jsonResponse.LoginResponse;
import com.thuanht.eatez.utils.KeyboardUtils;
import com.thuanht.eatez.view.Activity.HomeActivity;
import com.thuanht.eatez.view.Activity.LoginActivity;
import com.thuanht.eatez.viewModel.LoginViewModel;
import com.thuanht.eatez.R;
import com.thuanht.eatez.viewModel.RegisterViewModel;


public class SignUpTabFragment extends Fragment implements RegisterCallback {
    private FragmentSignupTabBinding binding;
    private RegisterViewModel viewModel;
    private FirebaseAuth mAuth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSignupTabBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(RegisterViewModel.class);

        // Quan sát LiveData để nhận thông báo lỗi email & password
        viewModel.getFullnameError().observe(requireActivity(), errorString -> {
            binding.txtFullName.setError(errorString);
            binding.textInputNameLayout.setEndIconDrawable(null);
            binding.textInputNameLayout.setBoxStrokeColor(getResources().getColor(R.color.red_hat_red));
            binding.textInputNameLayout.setEndIconMode(TextInputLayout.END_ICON_NONE);
            binding.txtFullName.requestFocus();
        });
        viewModel.getEmailError().observe(requireActivity(), errorString -> {
            binding.txtEmail.setError(errorString);
            binding.textInputEmailLayout.setEndIconDrawable(null);
            binding.textInputEmailLayout.setBoxStrokeColor(getResources().getColor(R.color.red_hat_red));
            binding.textInputEmailLayout.setEndIconMode(TextInputLayout.END_ICON_NONE);
            binding.txtEmail.requestFocus();
        });
        viewModel.getPasswordError().observe(requireActivity(), errorString -> {
            binding.txtPassword.setError(errorString);
            binding.textInputPasswordLayout.setEndIconDrawable(null);
            binding.textInputPasswordLayout.setBoxStrokeColor(getResources().getColor(R.color.red_hat_red));
            binding.textInputPasswordLayout.setEndIconMode(TextInputLayout.END_ICON_NONE);
            binding.txtPassword.requestFocus();
        });

        return binding.getRoot();
    }

    public void eventHandler() {
        // Click button login
        binding.btnSignUp.setOnClickListener(v -> {
            String email = binding.txtEmail.getText().toString();
            String password1 = binding.txtPassword.getText().toString();
            String password2 = binding.txtRePassword.getText().toString(); // Sửa thành txtRePassword
            String fullname = binding.txtFullName.getText().toString(); // Sửa thành txtFullName
            Log.e("pass1",password1);
            Log.e("pass2",password2);
            if (viewModel.validateData(fullname,email, password1, password2)) {
                viewModel.register(fullname, email, password1, "");
            }
        });
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.setRegisterCallback(this);// Thiết lập loginCallback trong ViewModel
        eventHandler(); // Gọi eventHandler() để thiết lập sự kiện cho nút đăng nhập
    }

    @Override
    public void onRegisterSuccess() {
        binding.progressSignUp.setVisibility(View.VISIBLE);
        Intent intent = new Intent(requireContext(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRegisterFailure(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }
}
