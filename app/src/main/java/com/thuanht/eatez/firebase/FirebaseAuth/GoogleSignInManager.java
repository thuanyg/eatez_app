package com.thuanht.eatez.firebase.FirebaseAuth;

import android.app.Activity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.thuanht.eatez.R;

public class GoogleSignInManager {
    private static GoogleSignInManager instance;
    private GoogleSignInClient googleSignInClient;
    private GoogleSignInOptions googleSignInOptions;

    private GoogleSignInManager() {
        // Private constructor to prevent instantiation outside the class.
    }

    public static synchronized GoogleSignInManager getInstance() {
        if (instance == null) {
            instance = new GoogleSignInManager();
        }
        return instance;
    }

    public void init(Activity activity) {
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.google_signin_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(activity, googleSignInOptions);
    }

    public GoogleSignInAccount getLastSignedInAccount(Activity activity) {
        return GoogleSignIn.getLastSignedInAccount(activity);
    }

    public GoogleSignInClient getGoogleSignInClient() {
        return googleSignInClient;
    }

    public GoogleSignInOptions getGoogleSignInOptions() {
        return googleSignInOptions;
    }
}
