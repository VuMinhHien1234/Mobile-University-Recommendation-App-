package com.example.myapplication.ui.home;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.adapter.NganhAdapter;
import com.example.myapplication.adapter.TruongAdapterTotal;
import com.example.myapplication.model.Nganh;
import com.example.myapplication.model.Truong;
import com.example.myapplication.ui.truong.AllTruongActivity;
import com.example.myapplication.ui.truong.TruongDetailActivity;

import java.util.List;

public class HomeFragment extends Fragment {
    private HomeViewModel viewModel;
    private TruongAdapterTotal truongAdapter;
    private RecyclerView rvNganh;
    private RecyclerView rvTruong;
    private EditText input_name;

    private Button viewAll,btn_search;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        rvNganh = view.findViewById(R.id.rvNganh);
        rvTruong = view.findViewById(R.id.rvTruong);
        input_name = view.findViewById(R.id.input_name);
        btn_search = view.findViewById(R.id.btn_search);

        btn_search.setOnClickListener(v->{
            String input = input_name.getText().toString().trim();
            viewModel.getTruongName(input);
        });

        rvNganh.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        );

        rvTruong.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        );

        viewAll = view.findViewById(R.id.btn_viewall);
        viewAll.setOnClickListener(v->{
            Intent intent = new Intent(requireContext(), AllTruongActivity.class);
            startActivity(intent);
        });
        truongAdapter = new TruongAdapterTotal(R.layout.item_truong);
        rvTruong.setAdapter(truongAdapter);
        truongAdapter.setOnItemClickListener(truong -> {
            Intent intent = new Intent(requireContext(), TruongDetailActivity.class);
            intent.putExtra("truong_code", truong.getCode());
            startActivity(intent);
        });
        viewModel.getNganhList().observe(getViewLifecycleOwner(), this::showNganhList);
        viewModel.getTruongList().observe(getViewLifecycleOwner(), this::showTruongList);
        viewModel.loadTatCaTruong();
        return view;
    }
    private void showNganhList(List<Nganh> list) {
        NganhAdapter adapter = new NganhAdapter(requireContext(), list, nganh -> {
            viewModel.loadTruongTheoNganh(nganh.getName());
        });
        rvNganh.setAdapter(adapter);
    }
    private void showTruongList(List<Truong> list) {
        truongAdapter = new TruongAdapterTotal(R.layout.item_truong);
        truongAdapter.setData(list);
        rvTruong.setAdapter(truongAdapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        rvNganh = null;
        rvTruong = null;
    }
}