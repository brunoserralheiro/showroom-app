package com.bs.showroom_app;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentHostCallback;
import androidx.navigation.fragment.NavHostFragment;

import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class FirstFragment extends Fragment {
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    // Host this fragment is attached to.


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        view.findViewById(R.id.button_upload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("UPLOAD button");
                //check runtime permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if ((ContextCompat.checkSelfPermission(requireActivity().getBaseContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_DENIED)) {
                        System.out.println("permission not granted");
                        //permission not granted, request it
                        String[] permissions = {android.Manifest.permission.READ_EXTERNAL_STORAGE};
                        //show pop up for runtime permission
                        requestPermissions(permissions, PERMISSION_CODE);
                        System.out.println(this.getClass().getName()+"   permission requested >>>" +
                                ContextCompat.checkSelfPermission(requireActivity().getBaseContext(),
                                android.Manifest.permission.READ_EXTERNAL_STORAGE));

                    } else {
                        //permission already granted
                        pickImageFromGallery();
                    }
                } else {
                    //system OS is less than Marshmallow
                    pickImageFromGallery();
                }

            }
        });

        view.findViewById(R.id.button_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("UPLOAD button");
                //check runtime permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if ((ContextCompat.checkSelfPermission(requireActivity().getBaseContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_DENIED)) {
                        System.out.println("permission not granted");
                        //permission not granted, request it
                        String[] permissions = {android.Manifest.permission.READ_EXTERNAL_STORAGE};
                        //show pop up for runtime permission
                        requestPermissions(permissions, PERMISSION_CODE);
                        System.out.println(this.getClass().getName()+"   permission requested >>>" +
                                ContextCompat.checkSelfPermission(requireActivity().getBaseContext(),
                                        android.Manifest.permission.READ_EXTERNAL_STORAGE));

                    } else {
                        //permission already granted
                        pickImageFromGallery();
                    }
                } else {
                    //system OS is less than Marshmallow
                    pickImageFromGallery();
                }

            }
        });
    }

    @SuppressLint("IntentReset")
    private void pickImageFromGallery() {
        System.out.println("pickImageFromGallery");
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
        Activity activity = this.getActivity();
        String[] permissions = {android.Manifest.permission.READ_EXTERNAL_STORAGE};
        int[] granted = {PackageManager.PERMISSION_GRANTED};
        assert activity != null;

    }

    // handle result of runtime permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //handle result of pickImageFromGallery

        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission was granted
                    Toast.makeText(getContext(), "Permission GRANTED...!", Toast.LENGTH_LONG).show();
                    pickImageFromGallery();
                } else {
                    //permission denied
                    Toast.makeText(getContext(), "Permission denied...!", Toast.LENGTH_LONG).show();
                }

            }
            default: {

            }
        }


    }


}