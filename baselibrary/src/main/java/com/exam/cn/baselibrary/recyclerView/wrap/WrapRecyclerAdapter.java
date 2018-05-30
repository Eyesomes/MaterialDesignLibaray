package com.exam.cn.baselibrary.recyclerView.wrap;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 杰 on 2017/10/14.
 */

class WrapRecyclerAdapter extends RecyclerView.Adapter {

    //不包含头部和底部的adapter
    private RecyclerView.Adapter mAdapter;
    //用SparseArray可以比arrayList多一个标识,比hashmap更高效
    private SparseArray<View> mHeaders, mFoots;

    private static int BASE_HEADER_KEY = 100000;
    private static int BASE_FOOTER_KEY = 200000;
    private static int BASE_LOAD_MORE = 300000;

    private boolean mIsLoadMore = false;
    private int pageSize;
    private View mLoadMoreView;

    private RecyclerView.AdapterDataObserver mObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            super.onItemRangeChanged(positionStart, itemCount, payload);
            notifyItemRangeChanged(positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            notifyItemMoved(fromPosition, toPosition);
        }
    };

    public WrapRecyclerAdapter(RecyclerView.Adapter adapter) {
        this.mAdapter = adapter;
        mAdapter.registerAdapterDataObserver(mObserver);
        mHeaders = new SparseArray();
        mFoots = new SparseArray();
    }

    @Override
    public int getItemViewType(int position) {
        //根据 position 去区分 viewType
        //头
        int numHeaders = mHeaders.size();

        if (isHeader(position)) {
            return mHeaders.keyAt(position);
        }

        int adjPosition = position - numHeaders;
        int adapterCount = mAdapter.getItemCount();

        if (isLoadMore(position)){
            return BASE_LOAD_MORE;
        }

        if (isFooter(position)){
            return mFoots.keyAt(adjPosition - adapterCount);
        }

        //内容
        return mAdapter.getItemViewType(adjPosition);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //怎么区分是头部底部还是内容区域 ---> 只能根据 viewType

        if (mHeaders.indexOfKey(viewType) >= 0) { //头
            return onCreateHeaderFooterViewHolder(mHeaders.get(viewType));
        } else if (mFoots.indexOfKey(viewType) >= 0) {//底部
            return onCreateHeaderFooterViewHolder(mFoots.get(viewType));
        } else if (viewType == BASE_LOAD_MORE){
            return onCreateHeaderFooterViewHolder(mLoadMoreView);
        }

        //内容
        return mAdapter.onCreateViewHolder(parent, viewType);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isFooter(position) || isHeader(position) || isLoadMore(position)||isLoadMore(position)) {
            return;
        }
        //内容
        if (mAdapter != null) {
            mAdapter.onBindViewHolder(holder, position - mHeaders.size());
        }
    }

    @Override
    public int getItemCount() {
        return mHeaders.size() + mFoots.size() + mAdapter.getItemCount() + (mAdapter.getItemCount()<pageSize||!mIsLoadMore?0:1);
    }

    /**
     * 创建头部和底部的ViewHolder
     *
     * @param view
     * @return
     */
    private RecyclerView.ViewHolder onCreateHeaderFooterViewHolder(View view) {
        return new RecyclerView.ViewHolder(view) {
        };
    }

    /**
     * 解决GridLayoutManager添加头部和底部不占用一行的问题 shipei
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
//        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
//        if (layoutManager instanceof GridLayoutManager) {
//            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
//            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//                @Override
//                public int getSpanSize(int position) {
//                    return isHeader(position) || isFooter(position) || isLoadMore(position) ? gridLayoutManager.getSpanCount() : 1;
//                }
//            });
//        }
        mAdapter.onAttachedToRecyclerView(recyclerView);
    }


    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        int position = holder.getLayoutPosition();
        if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams && (isHeader(position) || isFooter(position) || isLoadMore(position))) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(true);
        }
        //noinspection unchecked
        mAdapter.onViewAttachedToWindow(holder);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        mAdapter.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        //noinspection unchecked
        mAdapter.onViewDetachedFromWindow(holder);
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        //noinspection unchecked
        mAdapter.onViewRecycled(holder);
    }

    @Override
    public boolean onFailedToRecycleView(RecyclerView.ViewHolder holder) {
        //noinspection unchecked
        return mAdapter.onFailedToRecycleView(holder);
    }

    @Override
    public void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        mAdapter.unregisterAdapterDataObserver(observer);
    }

    @Override
    public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        mAdapter.registerAdapterDataObserver(observer);
    }


//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 判断是不是头部或者底部
     *
     * @param position
     */
    public boolean isHeader(int position) {

        int numHeaders = mHeaders.size();

        if (position < numHeaders) {
            return true;
        }
        return false;
    }

    public boolean isFooter(int position) {

        int numHeaders = mHeaders.size();

        if (position - numHeaders +1>  mAdapter.getItemCount()) {
            return true;
        }
        return false;
    }

    // 添加头部和底部及移除的方法

    public void addHeaderView(View view) {

        if (mHeaders.indexOfValue(view) == -1) { //避免重复添加
            mHeaders.put(BASE_HEADER_KEY++, view);

            notifyDataSetChanged();
        }
    }

    public void addFooterView(View view) {

        if (mFoots.indexOfValue(view) == -1) { //避免重复添加
            mFoots.put(BASE_FOOTER_KEY++, view);

            notifyDataSetChanged();
        }
    }

    public void removeHeaderView(View view) {
        if (mHeaders.indexOfValue(view) >= 0) { //避免重复删除
            mHeaders.removeAt(mHeaders.indexOfValue(view));

            notifyDataSetChanged();
        }
    }

    public void removeFooterView(View view) {
        if (mFoots.indexOfValue(view) >= 0) { //避免重复删除
            mFoots.removeAt(mFoots.indexOfValue(view));

            notifyDataSetChanged();
        }
    }

    /**
     * 加载更多
     */
    public void isLoadMore(boolean isloadmore){
        this.mIsLoadMore = isloadmore;
    }

    public void setPageSize(int size){
        this.pageSize = size;
    }

    public void setLoadMoreView(View view){
        this.mLoadMoreView = view;
    }

    /**
     * 判断是否加载更多
     *
     * @param position
     * @return
     */
    private boolean isLoadMore(int position) {
        return mIsLoadMore && position - mHeaders.size() - mFoots.size() - mAdapter.getItemCount() ==  0 && mAdapter.getItemCount()>=pageSize;
    }
}