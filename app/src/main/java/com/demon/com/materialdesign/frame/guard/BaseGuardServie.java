package com.demon.com.materialdesign.frame.guard;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.demon.com.materialdesign.ProcessGuard;

public abstract class BaseGuardServie extends Service {

    private MessageServiceConnection mServiceConnection;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ProcessGuard.Stub() {
        };
    }

    @Override
    public void onCreate() {
        if (mServiceConnection == null) {
            mServiceConnection = new MessageServiceConnection();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        BaseGuardServie.this.bindService(new Intent(
                        BaseGuardServie.this, guardService()),
                mServiceConnection, Context.BIND_IMPORTANT);

        return START_STICKY;
    }

    protected abstract Class guardService();

    private class MessageServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 建立连接
            Toast.makeText(BaseGuardServie.this, "建立连接", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // 断开连接
            Toast.makeText(BaseGuardServie.this, "断开连接", Toast.LENGTH_LONG).show();
            Intent guardIntent = new Intent(BaseGuardServie.this, guardService());
            // 发现断开我就从新启动和绑定
            startService(guardIntent);
            BaseGuardServie.this.bindService(guardIntent,
                    mServiceConnection, Context.BIND_IMPORTANT);
        }
    }
}
