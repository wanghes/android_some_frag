package com.werot.helloworld;

import static com.werot.helloworld.ThreeActivity.TAG;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.werot.helloworld.adapter.JokeAdapter;
import com.werot.helloworld.databinding.FragmentThreeBinding;
import com.werot.helloworld.domain.JokeResult;
import com.werot.helloworld.utils.AsynNetUtils;

import java.util.List;

public class ThreeFragment extends Fragment {
    public final static String ParamData = "ThreeFragmentParam";
    private FragmentThreeBinding binding;
    private JokeAdapter jokeAdapter;
    private Context context;

    private int ThreeFragmentParam;


    public Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100 && msg.obj != null) {
                Bundle receivedData = msg.getData();
                Boolean isRefresh = receivedData.getBoolean("isRefresh");
                Gson gson = new Gson();
                String data = (String) msg.obj;
                JokeResult js = gson.fromJson(data, JokeResult.class);
                List<JokeResult.Joke> list = js.getResult();

                jokeAdapter.setData(list, isRefresh);
            }
        }
    };

    public static ThreeFragment newInstance(int data) {
        if (data == 0) {
            data = 1;
        }
        ThreeFragment threeFragment = new ThreeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ParamData, data);
        threeFragment.setArguments(bundle);
        return threeFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentThreeBinding.inflate(inflater, container, false);
        Bundle bundle = getArguments();
        context = getContext();


        if (bundle != null) {
            ThreeFragmentParam = bundle.getInt(ParamData);
        }

        jokeAdapter = new JokeAdapter(context);
        // binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext())); // 已经在布局文件中设置了
        binding.recyclerView.setAdapter(jokeAdapter);

        jokeAdapter.setOnLoadMoreListener(new JokeAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore(int currentPage) {
                final int page = currentPage;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 加载完成后停止刷新动画
                        getData(page, false);
                    }
                },1000);

            }
        });

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                final int page = ThreeFragmentParam;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 加载完成后停止刷新动画
                        binding.swipeRefreshLayout.setRefreshing(false);
                        getData(page, true);
                    }
                },1000);

            }
        });


        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        getData(page);

        //                jokeAdapter.setmOnItemCLickListener(new NewsListAdapter.onItemCLickListener() {
//                    @Override
//                    public void onItemClick(NewsInfo.ResultDTO.DataDTO dataDTO, int position) {
//                        Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
//                        intent.putExtra("dataDTO", dataDTO);
//                        startActivity(intent);
//                    }

//                });
    }

    public void getData(int page, boolean isRefresh) {
//        Log.d(TAG, Integer.toString(page));
        AsynNetUtils.get("http://192.168.110.61:3000/api/jokes/" + page, new AsynNetUtils.Callback() {
            @Override
            public void onResponse(String response) {
//                System.out.println(TAG+"=>"+response);
                if (null != response) {
                    Message message = new Message();
                    message.what = 100;
                    message.obj = response;
                    Bundle data = new Bundle();
                    data.putBoolean("isRefresh", isRefresh);
                    message.setData(data);

                    ThreeFragment.this.handler.sendMessage(message);
                    // Log.d(TAG, response);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}

