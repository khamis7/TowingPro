package com.example.towingpro.login;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.towingpro.R;
import com.example.towingpro.databinding.FragmentLoginBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;
import java.util.regex.Pattern;

public class LoginFragment extends Fragment implements View.OnClickListener {
    //    public static String LOGIN_SUCCESSFUL = "LOGIN_SUCCESSFUL";
    private FragmentLoginBinding binding;
    private FirebaseAuth auth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        auth = FirebaseAuth.getInstance();
        binding.buttonRegistration.setOnClickListener(this);
        binding.buttonLogin.setOnClickListener(this);
        binding.passwordRecovery.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view == binding.buttonRegistration) {
            NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_registrationFragment);
        }
        if (view == binding.passwordRecovery) {
            NavHostFragment.findNavController(LoginFragment.this)
                    .navigate(R.id.action_loginFragment_to_forgetPassword);
        }
        if (view == binding.buttonLogin) {
            if (!isValidEmail(binding.emailEditText.getText())) {
                binding.emailEditText.setError("Email must contain @ your domain company");
            }
            if (!isPasswordValid(binding.passwordEditText.getText())) {
                binding.passwordEditText.setError(getString(R.string.login_error_password));
                binding.passwordRecovery.setVisibility(View.VISIBLE);


            } else {
                binding.progress.setVisibility(View.VISIBLE);


                auth.signInWithEmailAndPassword(Objects.requireNonNull(binding.emailEditText.getText()).toString(), binding.passwordEditText.toString()
                ).addOnSuccessListener(authResult -> NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.action_loginFragment_to_serviceFragment)).addOnFailureListener(e -> {
                    Snackbar.make(requireContext(), requireView(), "Authentication Failed", Snackbar.LENGTH_LONG).show();
                    hideDialog();
                });
            }

        } else {
            showErrorMessage();
        }

        binding.emailEditText.setOnKeyListener((view1, i, keyEvent) ->

        {
            if (isValidEmail(binding.emailEditText.getText())) {
                binding.emailEditText.setError(null);
            }
            return false;
        });
        binding.passwordEditText.setOnKeyListener((view1, i, keyEvent) ->

        {
            if (isPasswordValid(binding.passwordEditText.getText())) {
                binding.passwordEditText.setError(null);
            }
            return false;
        });
    }


    private void hideDialog() {
        if (binding.progress.getVisibility() == View.VISIBLE) {
            binding.progress.setVisibility(View.INVISIBLE);
        }
    }

    private void setupUserAuth() {
        auth.addAuthStateListener(firebaseAuth -> {
                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        if (user.isEmailVerified()) {
                            Snackbar.make(this.requireContext(), requireView(), "Authenticated with : " + user.getEmail(), Snackbar.LENGTH_LONG).show();
                        } else
                            Snackbar.make(this.requireContext(), requireView(), "Check your email Inbox for a verfication link", Snackbar.LENGTH_LONG).show();
                    }
                }
        );
    }

    private boolean isValidEmail(CharSequence email) {
        Pattern EMAIL_PATTERN;
        EMAIL_PATTERN = Pattern.compile("[a-zA-Z0-9+._%\\-]{1,256}" +
                "@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+");
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    private boolean isPasswordValid(Editable text) {

        return text != null && text.length() >= 8;
    }


    private void showErrorMessage() {
        Snackbar.make(this.requireContext(), requireView(), "All fields required", Snackbar.LENGTH_LONG).show();
    }
}
