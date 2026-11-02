package com.example.androidclass;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class BatteryDemoActivity extends AppCompatActivity {

    private Button btnStatus;
    private TextView tvBatteryInfo;
    private BroadcastReceiver batteryReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery_demo);

        btnStatus = findViewById(R.id.btnStatus);
        tvBatteryInfo = findViewById(R.id.tvBatteryInfo);

        btnStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBatteryStatus();
            }
        });

        // Register receiver for battery status changes
        registerBatteryReceiver();
    }

    private void showBatteryStatus() {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, ifilter);

        if (batteryStatus != null) {
            int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            
            if (level != -1 && scale != -1) {
                int batteryPercent = (level * 100) / scale;
                String message = "Current Battery: " + batteryPercent + "%";
                
                tvBatteryInfo.setText(message);
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void registerBatteryReceiver() {
        batteryReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
                    int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                    int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                    
                    if (level != -1 && scale != -1) {
                        int batteryPercent = (level * 100) / scale;
                        tvBatteryInfo.setText("Current Battery: " + batteryPercent + "%");
                    }
                }
            }
        };

        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (batteryReceiver != null) {
            try {
                unregisterReceiver(batteryReceiver);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
