package com.exam.cn.framelibrary.charting.interfaces.dataprovider;

import com.exam.cn.framelibrary.charting.data.BubbleData;

public interface BubbleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    BubbleData getBubbleData();
}
