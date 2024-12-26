package com.werot.helloworld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.werot.helloworld.domain.JokeResult;
import com.werot.helloworld.utils.AsynNetUtils;

public class ThreeActivity extends AppCompatActivity {
    public final static String TAG = "ThreeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);

        Intent intent = getIntent();
        int data = intent.getIntExtra(ThreeFragment.ParamData, 1);

        ThreeFragment threeFragment = ThreeFragment.newInstance(data);

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frameThree, threeFragment);
        fragmentTransaction.commit();
    }



}