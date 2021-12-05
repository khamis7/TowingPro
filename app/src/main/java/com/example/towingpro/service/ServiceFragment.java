package com.example.towingpro.service;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.towingpro.R;
import com.example.towingpro.databinding.FragmentServiceBinding;

public class ServiceFragment extends Fragment implements View.OnClickListener {
    private FragmentServiceBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentServiceBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = NavHostFragment.findNavController(this);
        NavBackStackEntry navBackStackEntry = navController.getCurrentBackStackEntry();
        assert navBackStackEntry != null;
        SavedStateHandle savedStateHandle = navBackStackEntry.getSavedStateHandle();
//        savedStateHandle.getLiveData()
//                .observe(navBackStackEntry, (Observer<Boolean>) success -> {
//                    if (!success) {
//                        int startDestination = navController.getGraph().getStartDestination();
//                        NavOptions navOptions = new NavOptions.Builder()
//                                .setPopUpTo(startDestination, true)
//                                .build();
//                        navController.navigate(startDestination, null, navOptions);
//                    }
//                });
        binding.buttonMechanic.setOnClickListener(this);
        binding.buttonTowing.setOnClickListener(this);
        binding.cancel.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {
        if (view == binding.buttonTowing) {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_serviceFragment_to_towingRequest);
        }
        if (view == binding.buttonMechanic) {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_serviceFragment_to_mechanicRequest);
        }
        if (view == binding.cancel) {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_serviceFragment_to_welcomeFragment);
        }
    }
}
