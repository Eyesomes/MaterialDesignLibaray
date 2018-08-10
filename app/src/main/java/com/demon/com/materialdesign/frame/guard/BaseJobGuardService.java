package com.demon.com.materialdesign.frame.guard;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.util.List;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public abstract class BaseJobGuardService extends JobService {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        startSchedule();
        return START_STICKY;
    }

    protected void startSchedule() {
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        int id = 1;
        jobScheduler.cancel(id);
        JobInfo.Builder builder = new JobInfo.Builder(id, new ComponentName(this, getClass()));
        if (Build.VERSION.SDK_INT >= 24) {
            builder.setMinimumLatency(1000); //执行的最小延迟时间
            builder.setOverrideDeadline(1000);  //执行的最长延时时间
            builder.setMinimumLatency(1000);
            builder.setBackoffCriteria(1000, JobInfo.BACKOFF_POLICY_LINEAR);//线性重试方案
        } else {
            builder.setPeriodic(JobInfo.DEFAULT_INITIAL_BACKOFF_MILLIS);
        }
//        builder.setPersisted(true);  // 设置设备重启时，执行该任务
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
//        builder.setRequiresCharging(true); // 当插入充电器，执行该任务
        JobInfo info = builder.build();
        jobScheduler.schedule(info); //开始定时执行该系统任务
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.e("TAG", "onStartJob");

        boolean isMessageAlive = isServiceWork(this, guardService().getName());

        if (!isMessageAlive) {
            startService(new Intent(this, guardService()));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            jobFinished(params, true);
            startSchedule();
        } else {
            jobFinished(params, false);
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    protected abstract Class guardService();

    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param mContext
     * @param serviceName 是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    public boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(100);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }
}
