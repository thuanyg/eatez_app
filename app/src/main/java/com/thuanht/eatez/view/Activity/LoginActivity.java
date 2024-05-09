package com.thuanht.eatez.view.Activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookActivity;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.annotation.NonNullApi;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.play.integrity.internal.b;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.messaging.FirebaseMessaging;
import com.thuanht.eatez.Adapter.LoginAdapter;
import com.thuanht.eatez.LocalData.LocalDataManager;
import com.thuanht.eatez.firebase.FirebaseAuth.GoogleSignInManager;
import com.thuanht.eatez.interfaceEvent.RegisterCallback;
import com.thuanht.eatez.jsonResponse.SignupResponse;
import com.thuanht.eatez.jsonResponse.UserResponse;
import com.thuanht.eatez.model.User;
import com.thuanht.eatez.retrofit.ApiService;
import com.thuanht.eatez.view.Dialog.DialogUtil;
import com.thuanht.eatez.viewModel.LoginViewModel;
import com.thuanht.eatez.R;
import com.thuanht.eatez.databinding.ActivityLoginBinding;
import com.thuanht.eatez.viewModel.RegisterViewModel;
import com.thuanht.eatez.viewModel.UserViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity{
    public ActivityLoginBinding binding;
    private Disposable disposable;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        initUI();
        // Customize Google + Facebook
        GoogleSignInManager.getInstance().init(this);
        binding.btnGoogle.setOnClickListener(view -> {
            // Initialize sign in intent
            Intent intent = GoogleSignInManager.getInstance().getGoogleSignInClient().getSignInIntent();
            // Start activity for result
            startActivityForResult(intent, 100);
        });
        // Facebook
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                getFbInfo();
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(@androidx.annotation.NonNull FacebookException e) {
                Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnFacebook.setOnClickListener(v -> {
            LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("email", "public_profile"));
        });
    }

    private void getFbInfo() {
        if (AccessToken.getCurrentAccessToken() != null) {
            GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(final JSONObject me, GraphResponse response) {
                            if (me != null) {
                                String userId = me.optString("id");
                                String userName = me.optString("name");
                                String userEmail = me.optString("email") != "" ? me.optString("email") : userId + "@eatez.vn";
                                String userPictureUrl = "https://graph.facebook.com/" + userId + "/picture?type=large";
//                                Toast.makeText(LoginActivity.this, "User ID: " + userId, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(LoginActivity.this, "User Name: " + userName, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(LoginActivity.this, "User Email: " + userEmail, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(LoginActivity.this, "User Picture URL: " + userPictureUrl, Toast.LENGTH_SHORT).show();
                                User user = new User(-1, userName, userEmail, userPictureUrl);
                                SignInAction(user);
                            }
                        }
                    });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,link");
            request.setParameters(parameters);
            request.executeAsync();
        }
    }

    private void initUI(){
        binding.tabLayout.addTab(binding.tabLayout.newTab());
        binding.tabLayout.addTab(binding.tabLayout.newTab());
        binding.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        binding.tabLayout.setTabIndicatorAnimationMode(TabLayout.INDICATOR_ANIMATION_MODE_LINEAR);
        binding.tabLayout.setTabMode(TabLayout.MODE_FIXED);
        LoginAdapter adapter = new LoginAdapter(getSupportFragmentManager(), this, binding.tabLayout.getTabCount());
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout));

        binding.tabLayout.setupWithViewPager(binding.viewPager);
        binding.tabLayout.getTabAt(0).setText("Sign In");
        binding.tabLayout.getTabAt(1).setText("Sign Up");
    }

    private void SignInAction(User user){
        binding.constraintLayout.setVisibility(View.GONE);
        binding.progressLogin.setVisibility(View.VISIBLE);
        MutableLiveData<Boolean> isSuccess = new MutableLiveData<>();
        isSuccess.observe(this, aBoolean -> {
            if(!aBoolean){
                loginWithEmailAlready(user.getEmail());
            } else {
                // Navigate to Home
                LocalDataManager.getInstance().setUserLogin(user);
                finish();
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
            }
        });
        ApiService.ApiService.Register(user.getFullName(), user.getEmail(), "123@eatez", user.getAvatar_image())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SignupResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NonNull SignupResponse signupResponse) {
                        if(signupResponse != null){
                            if(signupResponse.isStatus()) isSuccess.setValue(true);
                            else isSuccess.setValue(false);
                        } else isSuccess.setValue(false);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void loginWithEmailAlready(String email) {
        MutableLiveData<User> user = new MutableLiveData<>();
        user.observe(this, userFetched -> {
            if(userFetched != null){
                LocalDataManager.getInstance().clearUserLogin();
                LocalDataManager.getInstance().setUserLogin(userFetched);
                // Update token to push notification
                setTokenUserLoginGoogle();
                // Navigate to Home
                finish();
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
            } else {
                binding.progressLogin.setVisibility(View.GONE);
            }
        });
        ApiService.ApiService.getUserByEmail(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NonNull UserResponse userResponse) {
                        if(userResponse != null){
                            if(userResponse.isStatus()) {
                                user.setValue(userResponse.getData());
                            } else {
                                user.setValue(null);
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        // Check condition
        if(requestCode == 100){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
//                Toast.makeText(getApplicationContext(), "Successfully", Toast.LENGTH_SHORT).show();
                navigateToHomeActivity();
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void navigateToHomeActivity() {
        GoogleSignInAccount acc = GoogleSignInManager.getInstance().getLastSignedInAccount(this);
        if(acc != null){
            String personName = acc.getDisplayName();
            String personEmail = acc.getEmail();
            String avatar = acc.getPhotoUrl() == null ? "" : acc.getPhotoUrl().toString();
            User user = new User(-1, personName, personEmail, avatar);
            SignInAction(user);
        }
    }

    public void setTokenUserLoginGoogle(){
        // Get current FCM token
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                return;
            }
            String token = task.getResult();
            updateUserTokenToServer(token);
        });
    }
    private void updateUserTokenToServer(String token) {
        UserViewModel viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        viewModel.getStatus().observeForever(aBoolean -> {
        });
        int userid = LocalDataManager.getInstance().getUserLogin().getUserid();
        viewModel.setToken(userid, token);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(disposable != null) disposable.dispose();
    }
}