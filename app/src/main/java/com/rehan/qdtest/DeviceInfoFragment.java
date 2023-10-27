package com.rehan.qdtest;

import android.app.ActivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.rehan.qdtest.databinding.FragmentDeviceInfoBinding;

import java.io.File;
import java.io.InputStream;

public class DeviceInfoFragment extends BottomSheetDialogFragment {
    private FragmentDeviceInfoBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentDeviceInfoBinding.inflate(inflater,container,false);


        binding.deviceModel.setText(Build.MODEL);
        binding.osVersion.setText(Build.VERSION.RELEASE);
        binding.ramCapacity.setText((Long.toString(getRaminfo()))+" MB"); // it will return total ram information
        binding.totalStorage.setText(Long.toString(getTotalStorageStize())+" GB");// it will return total available storage
        binding.cpu.setText(processorDetails());// it will return processor details
//        processorDetails();
        return binding.getRoot();

    }

    private Long getTotalStorageStize() {
        File path= Environment.getDataDirectory();
        StatFs stat=new StatFs(path.getPath());
        long blockSize=stat.getBlockSize();
        long availableBlocks=stat.getBlockCount();
        long totalInternalMemory=availableBlocks*blockSize;
        return totalInternalMemory/(1024 *1024 *1024);
    }

    private long getRaminfo() {
        ActivityManager actManager = (ActivityManager) getContext().getSystemService(getContext().ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        assert actManager != null;
        actManager.getMemoryInfo(memInfo);
        long totalMemory = memInfo.totalMem;
        return totalMemory/(1024 *1024);
    }

   private String  processorDetails(){
       String output = "";
       try {

           String[] DATA = {"/system/bin/cat", "/proc/cpuinfo"};
           ProcessBuilder processBuilder = new ProcessBuilder(DATA);
           Process process = processBuilder.start();
           InputStream inputStream = process.getInputStream();
           byte[] byteArry = new byte[1024];

           while (inputStream.read(byteArry) != -1) {
               output = output + new String(byteArry);
           }
           inputStream.close();

           Log.d("CPU_INFO", output);
       } catch (Exception ex) {
           ex.printStackTrace();
       }
       return  output;
   }
}