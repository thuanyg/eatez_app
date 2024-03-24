package com.thuanht.eatez.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.thuanht.eatez.viewModel.LoginViewModel;
import com.thuanht.eatez.R;
import com.thuanht.eatez.databinding.LoginTabFragmentBinding;

public class LoginTabFragment extends Fragment {
    private LoginTabFragmentBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.login_tab_fragment, container, false);
        binding.setLifecycleOwner(this);

        // Lấy viewModel từ LoginActivity
        LoginViewModel loginViewModel = ((LoginActivity)requireActivity()).binding.getLoginViewModel();
        binding.setLoginViewModel(loginViewModel);

        // Quan sát LiveData để nhận thông báo lỗi email
        loginViewModel.getEmailError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                binding.textInputLayout.setEndIconDrawable(null);
                binding.textInputLayout.setBoxStrokeColor(getResources().getColor(R.color.red_hat_red));
                binding.textInputLayout.setEndIconMode(TextInputLayout.END_ICON_NONE);
                binding.txtEmail.setError(error);
                binding.txtEmail.requestFocus();
            } else {
                binding.textInputLayout.setBoxStrokeColor(getResources().getColor(R.color.success_dialog));
                binding.textInputLayout.setEndIconMode(TextInputLayout.END_ICON_CLEAR_TEXT);
                binding.txtEmail.setError(null);
            }
        });

        binding.btnLogin.setOnClickListener(v -> {
            if(loginViewModel.validateData()){
                Intent intent = new Intent(getContext(), HomeActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return binding.getRoot();
    }
}
