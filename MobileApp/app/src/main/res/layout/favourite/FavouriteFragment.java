package com.example.myapplication.ui.favourite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.myapplication.databinding.FragmentFavouriteBinding;

public class FavouriteFragment extends Fragment {

    private FragmentFavouriteBinding binding;
    private FavouriteAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFavouriteBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView();

        FavouriteViewModel favouriteViewModel = new ViewModelProvider(this).get(FavouriteViewModel.class);
        favouriteViewModel.getFavouriteItems().observe(getViewLifecycleOwner(), favouriteItems -> {
            adapter.setItems(favouriteItems);
        });
    }

    private void setupRecyclerView() {
        adapter = new FavouriteAdapter();
        binding.rvFavourite.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        binding.rvFavourite.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
