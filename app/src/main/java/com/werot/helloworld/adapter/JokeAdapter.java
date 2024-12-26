package com.werot.helloworld.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.werot.helloworld.R;
import com.werot.helloworld.ThreeActivity;
import com.werot.helloworld.domain.JokeResult;

import java.util.ArrayList;
import java.util.List;

public class JokeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context mContext;
    private static final int PER_PAGE = 10;
    private static final int VIEW_TYPE_CONTENT = 0;
    private static final int VIEW_TYPE_FOOTER = 1;
    private boolean isCanLoadMore = true;
    private Animation rotateAnimation;
    private RecyclerOnScrollerListener mOnScrollListener;
    private List<JokeResult.Joke> mList = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<JokeResult.Joke> list, Boolean isRefresh) {
        if (isRefresh) {
            RecyclerOnScrollerListener.currentPage = 2;
            mList = list;
        } else {
            mList.addAll(list);
        }
        notifyDataSetChanged();

        setCanLoadMore(list.size() >= PER_PAGE);
    }

    public JokeAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_view, null);

        if (rotateAnimation == null) {
            rotateAnimation = AnimationUtils.loadAnimation(mContext, R.anim.loading);
            rotateAnimation.setInterpolator(new LinearInterpolator());
        }
        if (viewType == VIEW_TYPE_CONTENT) {
            return new MyViewHolder(view);
        } else {
            return new FooterViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_footer, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == VIEW_TYPE_CONTENT) {
            JokeResult.Joke joke = mList.get(position);
            ((MyViewHolder)holder).title.setText(joke.getContent());
            ((MyViewHolder)holder).date.setText(joke.getUpdatetime());
        } else {
            if (isCanLoadMore) {
                ((FooterViewHolder) holder).showLoading();
            } else {
                ((FooterViewHolder) holder).showTextOnly("无更多数据");
            }
        }

    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }
    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return VIEW_TYPE_FOOTER;
        }
        return VIEW_TYPE_CONTENT;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        mOnScrollListener = new RecyclerOnScrollerListener(recyclerView) {
            @Override
            public void onLoadMore(int currentPage) {
                mOnLoadMoreListener.onLoadMore(currentPage);
            }
        };

        recyclerView.addOnScrollListener(mOnScrollListener);

    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        if (mOnScrollListener != null) {
            recyclerView.removeOnScrollListener(mOnScrollListener);
        }
        mOnScrollListener = null;
    }

    /*
     * 数据加载完毕时执行setCanLoadMore()，此时isLoading都置为false
     * */
    public void setCanLoadMore(boolean isCanLoadMore) {
        this.isCanLoadMore = isCanLoadMore;
        mOnScrollListener.setCanLoadMore(isCanLoadMore);
        mOnScrollListener.setLoading(false);
    }
    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);
        }
    }
    class FooterViewHolder extends RecyclerView.ViewHolder {
        ImageView ivLoading = itemView.findViewById(R.id.iv_loading);
        TextView tvLoading = itemView.findViewById(R.id.tv_loading);

        public FooterViewHolder(View itemView) {
            super(itemView);
        }

        void showTextOnly(String s) {
            ivLoading.setVisibility(View.INVISIBLE);
            tvLoading.setText(s);
        }

        void showLoading() {
            ivLoading.setImageResource(R.drawable.loading);
            tvLoading.setText("正在加载...");
            if (ivLoading != null) {
                ivLoading.startAnimation(rotateAnimation);
            }
        }

    }

    public interface OnLoadMoreListener{
        void onLoadMore(int currentPage);
    }

    private OnLoadMoreListener mOnLoadMoreListener;

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.mOnLoadMoreListener = listener;
    }


    public abstract static class RecyclerOnScrollerListener extends RecyclerView.OnScrollListener{
        public static int currentPage = 1;
        private boolean isLoading = false;
        private boolean isCanLoadMore = true;
        private RecyclerView mRecyclerView;

        public RecyclerOnScrollerListener(RecyclerView recyclerView) {
            super();
            this.mRecyclerView = recyclerView;
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (isCanLoadMore) {
                if (isSlideDottom()) {
                    onLoadMore(currentPage);
                    currentPage++;
                    isLoading = true;
                }
            }
        }

        public boolean isSlideDottom() {
            if (mRecyclerView == null) {
                return false;
            }

            RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                return !isLoading && visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1;
            }

            return false;
        }

        //抽象方法，用来传递加载更多事件
        public abstract void onLoadMore(int currentPage);
        public void setCanLoadMore(boolean load) {
            isCanLoadMore = load;
        }
        public void setLoading(boolean load) {
            isLoading = load;
        }
    }

}



