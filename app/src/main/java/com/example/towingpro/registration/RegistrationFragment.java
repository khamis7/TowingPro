package com.example.towingpro.registration;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.towingpro.R;
import com.example.towingpro.databinding.FragmentRegistrationBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.Objects;

public class RegistrationFragment extends Fragment {
    private FragmentRegistrationBinding binding;
    DatabaseReference users;
    FirebaseAuth auth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        auth = FirebaseAuth.getInstance();

        binding.buttonRegistration.setOnClickListener(v -> {
            if (TextUtils.isEmpty(Objects.requireNonNull(binding.firstName.getText()).toString())
                    && TextUtils.isEmpty(Objects.requireNonNull(binding.lastName.getText()).toString())
                    && TextUtils.isEmpty(Objects.requireNonNull(binding.email.getText()).toString())
                    && TextUtils.isEmpty(Objects.requireNonNull(binding.date.getText()).toString())
                    && TextUtils.isEmpty(Objects.requireNonNull(binding.password.getText()).toString())) {
                showErrorMessage();
            } else {
                auth.createUserWithEmailAndPassword(Objects.requireNonNull(binding.email.getText()).toString(), Objects.requireNonNull(binding.password.getText()).toString())
                        .addOnSuccessListener(authResult -> {
                            User user = new User();
                            user.setFirstName(binding.firstName.getText().toString());
                            user.setLastName(Objects.requireNonNull(binding.lastName.getText()).toString());
                            user.setEmail(binding.email.getText().toString());
                            user.setDate(Objects.requireNonNull(binding.date.getText()).toString());
                            user.setPassword(binding.password.getText().toString());

                            users.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                    .setValue(user)
                                    .addOnSuccessListener(aVoid -> loginSuccessful())
                                    .addOnFailureListener(e ->
                                            Snackbar.make(RegistrationFragment.this.requireContext(), requireView(), "Failed" + e.getMessage(), Snackbar.LENGTH_LONG).show());

                        }).addOnFailureListener(e -> Snackbar.make(RegistrationFragment.this.requireContext(), requireView(), "Failed" + e.getMessage(), Snackbar.LENGTH_LONG).show());

            }
        });

    }

    public void loginFailed() {
        Snackbar.make(this.requireContext(), requireView(), getString(R.string.login_failed), Snackbar.LENGTH_LONG).show();

    }

    public void loginSuccessful() {
        Snackbar.make(RegistrationFragment.this.requireContext(), requireView(), getString(R.string.login_successful), Snackbar.LENGTH_LONG).show();
    }

    public void showErrorMessage() {
        Snackbar.make(this.requireContext(), requireView(), getString(R.string.fields_required), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
