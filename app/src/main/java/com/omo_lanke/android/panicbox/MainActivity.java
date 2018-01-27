package com.omo_lanke.android.panicbox;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;

import com.omo_lanke.android.panicbox.api.PanicApi;
import com.omo_lanke.android.panicbox.api.PanicApiFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.buttonForRobbery)
    Button buttonForRobbery;

    @BindView(R.id.buttonForAccident)
    Button buttonForAccident;

    @BindView(R.id.buttonForFire)
    Button buttonForFire;

    @BindView(R.id.floatingHelpButton)
    FloatingActionButton floatingHelpButton;

    @BindView(R.id.regErrorLayout)
    LinearLayout regErrorLayout;

    private static final int REQUEST_CALL_PERMISSION = 1;
    private static final String FRAGMENT_DIALOG = "dialog";

    PanicApi serviceAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        serviceAPI =  PanicApiFactory.createService(PanicApi.class);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            requestPhonePermission();
            return;
        }
    }

    @OnClick(R.id.buttonForRobbery)
    public void buttonForRobbery() {
        announceEmergency(getString(R.string.robbery));
    }

    @OnClick(R.id.buttonForAccident)
    public void buttonForAccident(){
        announceEmergency(getString(R.string.accident));
    }

    @OnClick(R.id.buttonForFire)
    public void buttonForFire(){
        announceEmergency(getString(R.string.fire));
    }

    @OnClick(R.id.floatingHelpButton)
    public void buttonForHelp(){
        new HelpDialog().show(getSupportFragmentManager(), FRAGMENT_DIALOG);
    }

    @OnClick(R.id.regErrorLayout)
    public void regErrorLayout(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void announceEmergency(String emergencyType){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:767"));//change the number
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPhonePermission();
            return;
        }
        startActivity(callIntent);
        //TODO: Call API with emrgency type, location and voice
        //TODO: Api should inform LRU
        //TODO: Api should send message to Emergency contact
        //TODO: Phone should call the Response unit and reduce phone volume
    }

    public static class HelpDialog extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new AlertDialog.Builder(getActivity())
                    .setMessage(R.string.info)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dismiss();
                        }
                    })
                    .create();
        }
    }


    private void requestPhonePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                new ConfirmationDialog().show(getSupportFragmentManager(), FRAGMENT_DIALOG);
            } else {
                requestPermissions(new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.CALL_PHONE, Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                }, REQUEST_CALL_PERMISSION);
            }
        }
    }

    public static class ConfirmationDialog extends DialogFragment {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
//            final android.support.v4.app.Fragment parent = getParentFragment();
            return new AlertDialog.Builder(getActivity())
                    .setMessage(R.string.perm)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(new String[]{Manifest.permission.CAMERA,
                                            Manifest.permission.ACCESS_FINE_LOCATION,
                                            Manifest.permission.CALL_PHONE,
                                            Manifest.permission.ACCESS_COARSE_LOCATION},
                                    REQUEST_CALL_PERMISSION);
                        }
                    })
                    .setNegativeButton(android.R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Activity activity = getActivity();
                                    if (activity != null) {
                                        activity.finish();
                                    }
                                }
                            })
                    .create();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_register) {
//            Intent intent = new Intent(this, RegisterActivity.class);
//            startActivity(intent);
//            return true;
//        }else if (id == R.id.action_help){
//
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
