package com.rehan.qdtest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.rehan.qdtest.databinding.ActivityDashboardBinding;

public class DashboardActivity extends AppCompatActivity {
    private ActivityDashboardBinding binding;
    private DeviceInfoFragment deviceInfoFragment;

    private SharedPreferences sharedPreferences;
    private String  name,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        sharedPreferences=getSharedPreferences("AppData",MODE_PRIVATE);
        name=sharedPreferences.getString("name","user");
        email=sharedPreferences.getString("email","user");
        binding.name.setText(name);
        binding.email.setText(email);
    }

    public void openExitAppDialogue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(Html.fromHtml(getString(R.string.exit_confirm_msg)))
                .setCancelable(false)
                .setPositiveButton(Html.fromHtml(getString(R.string.exit_yes)), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DashboardActivity.this.finish();
                        System.exit(0);
                    }
                })
                .setNegativeButton(Html.fromHtml(getString(R.string.exit_no)), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
        Button btnPositive = alert.getButton(AlertDialog.BUTTON_POSITIVE);
        Button btnNegative = alert.getButton(AlertDialog.BUTTON_NEGATIVE);
        btnNegative.setTextColor(getColor(R.color.orange));
        btnNegative.setTextColor(getColor(R.color.blue));
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) btnPositive.getLayoutParams();
        LinearLayout.LayoutParams layoutParamsN = (LinearLayout.LayoutParams) btnNegative.getLayoutParams();
        layoutParams.weight = 100;
        layoutParamsN.weight = 100;
        btnPositive.setLayoutParams(layoutParams);
        btnNegative.setLayoutParams(layoutParamsN);
    }

    public void exitApp(View view) {
        openExitAppDialogue(); // it will display dialog box before exit from app
    }

    public void deviceInfo(View view) {
      DeviceInfoFragment deviceInfoFragment =new DeviceInfoFragment();
        deviceInfoFragment.show(getSupportFragmentManager(),
                "device_info");
    }

    public void openCamera(View view) {
        startActivity(new Intent(DashboardActivity.this,CameraActivity.class));
    }
}
