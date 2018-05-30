package com.exam.cn.baselibrary.recyclerView.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * recyclerView 通用 adapter
 * Created by 杰 on 2017/10/13.
 */

public abstract class RecyclerCommonAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

    protected Context mContext;

    private int mLayoutId;
    // 通用数据源
    protected List<T> mDatas;
    // 避免重复的去拿 inflater
    private LayoutInflater mInflater;
    //点击事件监听器
    private ItemClickListener mItemClickListener;
    private ItemLongClickListener mItemLongClickListener;
    //多种类型的布局
    private MultipleTypeSupport mMultipleTypeSupport;

    public RecyclerCommonAdapter(Context context, List<T> data, int layoutId) {

        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mLayoutId = layoutId;
        this.mDatas = data;
    }

    public RecyclerCommonAdapter(Context context,List<T> data,MultipleTypeSupport typeSupport){
        this(context,data,-1);
        this.mMultipleTypeSupport = typeSupport;
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public void setOnItemLongClickListener(ItemLongClickListener itemLongClickListener) {
        this.mItemLongClickListener = itemLongClickListener;
    }

    //这个方法会在onCreateViewHolder之前调用
    @Override
    public int getItemViewType(int position) {
        if (mMultipleTypeSupport!=null){
            return mMultipleTypeSupport.getLayoutId(mDatas.get(position));
        }
        return super.getItemViewType(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (mMultipleTypeSupport !=null){
            mLayoutId = viewType;
        }

        View itemView = mInflater.inflate(mLayoutId, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        if (mDatas ==null){
            return 0;
        }
        return mDatas.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //绑定数据
        convert(holder, mDatas.get(position), position);
        //点击事件
        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(position);
                }
            });
        }
        if (mItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return mItemLongClickListener.onItemLongClick(position);
                }
            });
        }
    }

    /**
     * 把必要的参数传出去
     *
     * @param holder
     * @param t
     * @param position
     */
    protected abstract void convert(ViewHolder holder, T t, int position);

}
