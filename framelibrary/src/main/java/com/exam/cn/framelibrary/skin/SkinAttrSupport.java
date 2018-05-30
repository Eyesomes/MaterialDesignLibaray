package com.exam.cn.framelibrary.skin;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.exam.cn.framelibrary.skin.attr.SkinAttr;
import com.exam.cn.framelibrary.skin.attr.SkinType;

import java.util.ArrayList;
import java.util.List;

/**
 * 皮肤的属性解析支持类
 * Created by 杰 on 2017/11/3.
 */

public class SkinAttrSupport {

    /**
     *  获取 关于SkinType 的 SkinAttr
     * @param context
     * @param attrs
     * @return
     */
    public static List<SkinAttr> getSkinAttrs(Context context, AttributeSet attrs) {
        // background src textcolor
        List<SkinAttr> skinAttrs = new ArrayList<>();

        int count = attrs.getAttributeCount();
        for (int i = 0; i < count; i++) {
            String attributeName = attrs.getAttributeName(i);
            String attributeValue = attrs.getAttributeValue(i);
//            Log.i("SkinAttrSupport", attributeName+"----"+attributeValue);
            SkinType skinType = getSkinType(attributeName);

            if (skinType != null) {
                // 资源的名字attributeValue , 我们发现是一个以"@" 开头的 int 数字
                String resName = getResName(context, attributeValue);
                // 为空就跳过
                if (TextUtils.isEmpty(resName)){
                    continue;
                }
                SkinAttr skinAttr = new SkinAttr(resName, skinType);

                skinAttrs.add(skinAttr);
            }
        }

        return skinAttrs;
    }

    /**
     * 获取资源的name
     * @param context
     * @param attributeValue
     * @return
     */
    private static String getResName(Context context, String attributeValue) {
        if (attributeValue.startsWith("@")){
            attributeValue = attributeValue.substring(1);

            int resId = Integer.parseInt(attributeValue);
            return context.getResources().getResourceEntryName(resId);
        }
        return null;
    }

    /**
     * 通过名字来筛选
     *
     * @param name
     * @return
     */
    private static SkinType getSkinType(String name) {
        SkinType[] values = SkinType.values();
        for (SkinType skinType : values) {
            if (skinType.getName().equals(name)) {
                return skinType;
            }
        }
        return null;
    }
}
