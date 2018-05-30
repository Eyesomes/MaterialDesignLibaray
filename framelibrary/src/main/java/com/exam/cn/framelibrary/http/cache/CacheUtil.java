package com.exam.cn.framelibrary.http.cache;

import com.exam.cn.baselibrary.db.DaoSupportFactory;
import com.exam.cn.baselibrary.db.IDaoSupport;
import com.exam.cn.baselibrary.util.MD5Util;

import java.util.List;

/**
 * Created by 杰 on 2017/10/28.
 */

public class CacheUtil {
    
    public static String getCacheResultJson(String finalUrl){

        IDaoSupport daoSupport = DaoSupportFactory.getFactory().getDao(CacheData.class);
        List<CacheData> cacheDatas = daoSupport.querySupport().selection("mUrlKey = ?").selectionArgs(MD5Util.string2MD5(finalUrl)).query();

        if (cacheDatas.size()!=0){
            String resultJson = cacheDatas.get(0).getmResultJson();
            return resultJson;
        }
        return null;
    }

    public static long cacheData(String finalUrl , String resultJson){

        IDaoSupport daoSupport = DaoSupportFactory.getFactory().getDao(CacheData.class);
        daoSupport.delete("mUrlKey = ?", MD5Util.string2MD5(finalUrl));
        long result = daoSupport.insert(new CacheData(MD5Util.string2MD5(finalUrl),resultJson));
        return result;
    }
}
