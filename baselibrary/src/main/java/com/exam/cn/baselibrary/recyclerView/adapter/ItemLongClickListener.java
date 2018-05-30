package com.exam.cn.baselibrary.recyclerView.adapter;

/**
 * 长按事件 return true 就不会响应点击事件
 */

public interface ItemLongClickListener {
    public boolean onItemLongClick(int position);
}
