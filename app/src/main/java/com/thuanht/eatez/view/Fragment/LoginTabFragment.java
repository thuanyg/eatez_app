package com.thuanht.eatez.view.Fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.thuanht.eatez.LocalData.LocalDataManager;
import com.thuanht.eatez.R;
import com.thuanht.eatez.databinding.FragmentLoginTabBinding;
import com.thuanht.eatez.interfaceEvent.LoginCallback;
import com.thuanht.eatez.jsonResponse.ForgetPasswordResponse;
import com.thuanht.eatez.jsonResponse.SignupResponse;
import com.thuanht.eatez.model.User;
import com.thuanht.eatez.retrofit.ApiService;
import com.thuanht.eatez.view.Activity.HomeActivity;
import com.thuanht.eatez.view.Dialog.DialogUtil;
import com.thuanht.eatez.viewModel.LoginViewModel;
import com.thuanht.eatez.viewModel.UserViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginTabFragment extends Fragment implements LoginCallback {
    private FragmentLoginTabBinding binding;
    private LoginViewModel viewModel;
    private FirebaseAuth mAuth;
    private boolean isLoginSuccess = false;
    private CircularProgressButton btnGetPassword;
    private Button btnCancel;
    private AlertDialog alert;

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

        viewModel.getIsLoginSuccess().observe(requireActivity(), isLoginSuccess -> {
            this.isLoginSuccess = isLoginSuccess;
        });
        return binding.getRoot();
    }

    public void eventHandler() {
        // Click button login
        binding.btnLogin.setOnClickListener(v -> {
            String email = binding.txtEmail.getText().toString();
            String password = binding.txtPassword.getText().toString();
            if (viewModel.validateData(email, password)) {
                binding.progressBarLogin.setVisibility(View.VISIBLE);
                viewModel.Login(email, password);
            }
        });

        binding.btnForgotPassword.setOnClickListener(v -> {
            showInputDialog();
        });


    }

    protected void showInputDialog() {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View promptView = layoutInflater.inflate(R.layout.dialog_input, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setOnCancelListener(dialog -> dialog.dismiss())
                .setView(promptView)
                .setCancelable(false);

        // setup a dialog window
        // create an alert dialog
        alert = alertDialogBuilder.create();
        alert.show();

        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        btnGetPassword = (CircularProgressButton) promptView.findViewById(R.id.btnGetPassword);
        btnCancel = (Button) promptView.findViewById(R.id.btnCancelForgotPass);
        btnGetPassword.setOnClickListener(v -> {
            btnGetPassword.startAnimation();
            btnCancel.setClickable(false);
            getPassword(editText.getText().toString().trim());
        });

        btnCancel.setOnClickListener(v -> alert.dismiss());
    }

    private void getPassword(String email) {
        Call<ForgetPasswordResponse> call = ApiService.ApiService.forgotPasswordAction(email);
        call.enqueue(new Callback<ForgetPasswordResponse>() {
            @Override
            public void onResponse(Call<ForgetPasswordResponse> call, Response<ForgetPasswordResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().getStatusCode() == 1){
                        DialogUtil.showSuccessDialog(requireContext(), "Successfully", "New password sent to your email: "
                                + email + ". Please check your email!", "Ok");
                        alert.dismiss();
                    }
                    if(response.body().getStatusCode() == 0){
                        DialogUtil.showErrorDalog(requireContext(), "Failed", "Something went wrong. Please try again!", "Ok");
                        btnCancel.setClickable(true);
                        btnGetPassword.stopAnimation();
                    }
                    if(response.body().getStatusCode() == -1){
                        DialogUtil.showErrorDalog(requireContext(), "Failed", "Email: "
                                + email + " don't match with any email in application. Please check and enter your email again!", "Ok");
                        btnCancel.setClickable(true);
                        btnGetPassword.revertAnimation();
                    }
                }
            }

            @Override
            public void onFailure(Call<ForgetPasswordResponse> call, Throwable throwable) {
                    DialogUtil.showErrorDalog(requireContext(), "Failed", "Something went wrong. Please try again!", "Ok");
                    btnCancel.setClickable(true);
                    btnGetPassword.stopAnimation();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Kiểm tra xem người dùng đã đăng nhập trước đó hay không
        User user = LocalDataManager.getInstance().getUserLogin();
        if (user != null) {
            // Chuyển hướng đến màn hình Home
            Intent intent = new Intent(requireContext(), HomeActivity.class);
            startActivity(intent);
            requireActivity().finish();
            return;
        }

        // Nếu chưa đăng nhập, tiếp tục hiển thị màn hình đăng nhập và xử lý logic đăng nhập
        viewModel.setLoginCallback(this); // Thiết lập loginCallback trong ViewModel
        eventHandler(); // Gọi eventHandler() để thiết lập sự kiện cho nút đăng nhập
    }

    @Override
    public void onLoginSuccess(User user) {
        LocalDataManager.getInstance().setUserLogin(user);
        // Get current FCM token
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                return;
            }
            String token = task.getResult();
            updateUserTokenToServer(token);
        });
        // Chuyển hướng đến màn hình Home
        Intent intent = new Intent(requireContext(), HomeActivity.class);
        startActivity(intent);
        requireActivity().finish();
    }

    @Override
    public void onLoginFailure(String errorMessage) {
        binding.progressBarLogin.setVisibility(View.GONE);
        Toast.makeText(getContext(), "Email or password not correct!", Toast.LENGTH_SHORT).show();
    }

    private void updateUserTokenToServer(String token) {
        UserViewModel viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        viewModel.getStatus().observeForever(aBoolean -> {
//            if(aBoolean){
//                Toast.makeText(requireContext(), "Luu token thanh cong", Toast.LENGTH_SHORT).show();
//            } else Toast.makeText(requireContext(), "Luu token khong thanh cong", Toast.LENGTH_SHORT).show();
        });
        int userid = LocalDataManager.getInstance().getUserLogin().getUserid();
        viewModel.setToken(userid, token);
    }
}