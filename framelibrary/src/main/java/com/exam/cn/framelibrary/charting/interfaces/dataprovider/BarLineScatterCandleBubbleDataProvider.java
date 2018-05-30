package com.exam.cn.framelibrary.charting.interfaces.dataprovider;

import com.exam.cn.framelibrary.charting.components.YAxis;
import com.exam.cn.framelibrary.charting.data.BarLineScatterCandleBubbleData;
import com.exam.cn.framelibrary.charting.utils.Transformer;

public interface BarLineScatterCandleBubbleDataProvider extends ChartInterface {

    Transformer getTransformer(YAxis.AxisDependency axis);
    boolean isInverted(YAxis.AxisDependency axis);
    
    float getLowestVisibleX();
    float getHighestVisibleX();

    BarLineScatterCandleBubbleData getData();
}
