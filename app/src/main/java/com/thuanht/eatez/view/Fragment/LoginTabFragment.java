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
    private FirebaseAuth mAuth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login_tab, container, false);
        binding.setLifecycleOwner(this);

        // Lấy viewModel từ LoginActivity
        LoginViewModel loginViewModel = ((LoginActivity) requireActivity()).binding.getLoginViewModel();
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
            if (loginViewModel.validateData()) {
                Intent intent = new Intent(getContext(), HomeActivity.class);
                // Hiển thị ProgressBar
                ProgressBar progressBar = getActivity().findViewById(R.id.progress_login);
                progressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Ẩn ProgressBar sau 2 giây
                        progressBar.setVisibility(View.GONE);
                    }
                }, 2000); // Đặt độ trễ là 2000 milliseconds (2 giây)
                KeyboardUtils.hideKeyboard(requireContext(), binding.btnLogin);
                startActivity(intent);
                getActivity().finish();
            }

//            mAuth = FirebaseAuth.getInstance();
//            mAuth.signInWithEmailAndPassword(loginViewModel.getEmail(), loginViewModel.getPassword())
//                    .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//                                Intent intent = new Intent(getContext(), HomeActivity.class);
//                                startActivity(intent);
//                                getActivity().finish();
//                            } else {
//                                Toast.makeText(getContext(), "Loi", Toast.LENGTH_SHORT)
//                                        .show();                        }
//                        }
//                    });
        });


        return binding.getRoot();
    }
}
