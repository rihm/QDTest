package com.rehan.qdtest;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.rehan.qdtest.databinding.FragmentLoginBinding;

public class LoginFragment extends BottomSheetDialogFragment {
    private FragmentLoginBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentLoginBinding.inflate(inflater,container,false);
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(view);// after validate it will login to app and navigates to Dashboard
            }
        });
        return binding.getRoot();
    }
//
    public void login(View view) {
        if(isValidData()){
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("AppData",MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putBoolean("isLogin",true);
            editor.putString("name","test user");
            editor.putString("email",binding.editEmail.getText().toString());
            editor.apply();
            editor.commit();

            Intent intent= new Intent(getActivity(), DashboardActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }

    public boolean isValidData() {
        boolean isValid = true;

        if (binding.editEmail.getText().toString()== null || binding.editEmail.getText().toString().equals("")) {

            binding.usernameError.setText("Enter email address");
            isValid = false;
            binding.usernameError.setVisibility(View.VISIBLE);

        } else {
            binding.usernameError.setText(null);
            binding.usernameError.setVisibility(View.GONE);
        }

        if (binding.editPassword.getText().toString() == null || binding.editPassword.getText().toString().equals("")) {
            binding.passwordError.setText("Enter password");
            binding.usernameError.setVisibility(View.VISIBLE);
            isValid = false;

        } else {
            binding.passwordError.setText(null);
            binding.passwordError.setVisibility(View.GONE);
        }
        if(binding.editEmail.getText().toString()!=null){
            if(!isEmailValid()){
                binding.usernameError.setText("Enter valid email address");
                isValid = false;
                binding.usernameError.setVisibility(View.VISIBLE);
            }else {
                binding.usernameError.setText(null);
                binding.usernameError.setVisibility(View.GONE);
            }
        }
        if(binding.editPassword.getText().toString()!=null){
            if(!isPasswordLengthGreaterThan5()){
                binding.passwordError.setText("Minimum 6 letter required");
                isValid = false;
                binding.passwordError.setVisibility(View.VISIBLE);
            }else {
                binding.passwordError.setText(null);
                binding.passwordError.setVisibility(View.GONE);
            }
        }

        return isValid;
    }
    public boolean isEmailValid() {
        return Patterns.EMAIL_ADDRESS.matcher(binding.editEmail.getText().toString()).matches();
    }
    public boolean isPasswordLengthGreaterThan5() {
        return binding.editPassword.getText().toString().length()>5;
    }
}