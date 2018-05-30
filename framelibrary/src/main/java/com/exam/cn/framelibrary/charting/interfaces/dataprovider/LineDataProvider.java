package com.exam.cn.framelibrary.charting.interfaces.dataprovider;

import com.exam.cn.framelibrary.charting.components.YAxis;
import com.exam.cn.framelibrary.charting.data.LineData;

public interface LineDataProvider extends BarLineScatterCandleBubbleDataProvider {

    LineData getLineData();

    YAxis getAxis(YAxis.AxisDependency dependency);
}
