package com.exam.cn.baselibrary.recyclerView.wrap;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 杰 on 2017/10/14.
 */

public class WrapRecyclerView extends RecyclerView {

    private WrapRecyclerAdapter mAdapter;

    private OnLoadListener mOnLoadListener;

    private LoadMoreView mLoadMoreView;
    private boolean loadMoreEnabled = false;
    /**
     * 是否正在执行网络请求，切换标记位保证滚动到底部时不会频繁触发网络请求
     */
    private boolean isLoading = false;
    private boolean noMore = false;
//    /**
//     * 分页加载时，总数据不满一页，则不需要分页
//     */
//    private boolean noNeedToLoadMore = true;

    public WrapRecyclerView(Context context) {
        this(context, null);
    }

    public WrapRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WrapRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
    }

    @Override
    public void setAdapter(Adapter adapter) {

        //拦截 adapter
        if (adapter instanceof WrapRecyclerAdapter) {
            mAdapter = (WrapRecyclerAdapter) adapter;
        } else {
            mAdapter = new WrapRecyclerAdapter(adapter);
        }

        super.setAdapter(mAdapter);
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);

        /*  state：
            SCROLL_STATE_IDLE     = 0 ：静止,没有滚动
            SCROLL_STATE_DRAGGING = 1 ：正在被外部拖拽,一般为用户正在用手指滚动
            SCROLL_STATE_SETTLING = 2 ：自动滚动开始
         */

        /*
            RecyclerView.canScrollVertically(1)的值表示是否能向上滚动，false表示已经滚动到底部
            RecyclerView.canScrollVertically(-1)的值表示是否能向下滚动，false表示已经滚动到顶部
         */

//        Log.e(TAG, state + ", " + this.canScrollVertically(1));
        // 判断RecyclerView滚动到底部，参考：http://www.jianshu.com/p/c138055af5d2
        if (state == RecyclerView.SCROLL_STATE_IDLE &&
                !this.canScrollVertically(1) &&
                loadMoreEnabled && !noMore && !isLoading) {
            doLoadMore();
        }
    }

    private void doLoadMore() {
        isLoading = true;
        noMore = false;
//        noNeedToLoadMore = false;
        if (mLoadMoreView != null) {
            mLoadMoreView.setState(LoadMoreView.STATE_LOADING);
        }
        if (mOnLoadListener != null) {
            mOnLoadListener.onLoadMore();
        }
    }
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 判断是不是头部或者底部
     *
     * @param position
     */
    public boolean isHeader(int position) {
        return mAdapter.isHeader(position);
    }

    public boolean isFooter(int position) {
        return mAdapter.isFooter(position);
    }

    // 添加头部和底部及移除的方法

    public void addHeaderView(View view) {
        if (mAdapter != null) {
            mAdapter.addHeaderView(view);
        }
    }

    public void addFooterView(View view) {
        if (mAdapter != null) {
            mAdapter.addFooterView(view);
        }
    }

    public void removeHeaderView(View view) {
        if (mAdapter != null) {
            mAdapter.removeHeaderView(view);
        }
    }

    public void removeFooterView(View view) {
        if (mAdapter != null) {
            mAdapter.removeFooterView(view);
        }
    }

    /**
     *
     * @param loadMoreEnabled 是否加载更多
     */
    public void setLoadMoreEnabled(boolean loadMoreEnabled,int pagesize,OnLoadListener onLoadListener) {
        this.loadMoreEnabled = loadMoreEnabled;
        this.mOnLoadListener = onLoadListener;
        mAdapter.isLoadMore(loadMoreEnabled);
        if (this.loadMoreEnabled) {
            mLoadMoreView = new LoadMoreView(getContext(), new LoadMoreView.OnClickListener() {
                @Override
                public void onLoadMoreViewClick() {
                    doLoadMore();
                }
            });

            mAdapter.setPageSize(pagesize);
            mAdapter.setLoadMoreView(mLoadMoreView);
        }
    }

    public void loadMoreComplete() {
        isLoading = false;
        noMore = false;
//        noNeedToLoadMore = false;
        if (mLoadMoreView != null) {
            mLoadMoreView.setState(LoadMoreView.STATE_COMPLETE);
        }
    }

    public void loadMoreError() {
        isLoading = false;
        noMore = false;
//        noNeedToLoadMore = false;
        if (mLoadMoreView != null) {
            mLoadMoreView.setState(LoadMoreView.STATE_ERROR);
        }
    }

    /**
     * 没有更多了
     */
    public void noMore() {
        isLoading = false;
        noMore = true;
//        noNeedToLoadMore = false;
        if (mLoadMoreView != null) {
            mLoadMoreView.setState(LoadMoreView.STATE_NOMORE);
        }
    }

//    /**
//     * 全部数据不足一页(分页时)，不需要显示加载更多
//     */
//    public void noNeedToLoadMore() {
//        isLoading = false;
//        noMore = true;
//        noNeedToLoadMore = true;
//    }


//------------------------------------------------------------------------------------------------------------------------------------------------------------

    public interface OnLoadListener {
        void onLoadMore();
    }

}
