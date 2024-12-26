package com.werot.helloworld;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.werot.helloworld.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    public final static String ParamInfo = "SecondFragmentParamInfo";

    private FragmentSecondBinding binding;

    public static SecondFragment newInstance(String title){
        SecondFragment secondFragment = new SecondFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ParamInfo, title);
        secondFragment.setArguments(bundle);
        return secondFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        Bundle bundle = getArguments();

        if (bundle != null) {
            String str = bundle.getString(ParamInfo);
            binding.textviewSecond.setText(str);
        }

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}