package com.example.towingpro.welcome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.towingpro.R;
import com.example.towingpro.databinding.FragmentWelcomeBinding;
import com.google.firebase.auth.FirebaseAuth;

public class WelcomeFragment extends Fragment implements View.OnClickListener {
    private FragmentWelcomeBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentWelcomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnLogin.setOnClickListener(this);
        binding.btnSignin.setOnClickListener(this);
    }

    private void showWelcomeMessage() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {
        if (view == binding.btnSignin) {
            NavHostFragment.findNavController(this).navigate(R.id.action_welcomeFragment_to_registrationFragment);
        }
        if (view == binding.btnLogin) {
            NavHostFragment.findNavController(this).navigate(R.id.action_welcomeFragment_to_loginFragment);

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null
                && !FirebaseAuth.getInstance().getCurrentUser().getUid().equals("")) {
            NavHostFragment.findNavController(this).navigate(R.id.action_welcomeFragment_to_serviceFragment);
        }
    }
}
