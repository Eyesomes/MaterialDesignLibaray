package com.exam.cn.framelibrary.charting.interfaces.dataprovider;

import com.exam.cn.framelibrary.charting.data.CandleData;

public interface CandleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    CandleData getCandleData();
}
