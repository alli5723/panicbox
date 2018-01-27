package com.omo_lanke.android.panicbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.omo_lanke.android.panicbox.api.PanicApi;
import com.omo_lanke.android.panicbox.api.PanicApiFactory;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.action_register)
    Button action_register;

    @BindView(R.id.user_name)
    EditText user_name;

    @BindView(R.id.first_name)
    EditText first_name;

    @BindView(R.id.last_name)
    EditText last_name;

    @BindView(R.id.phone)
    EditText phone;

    @BindView(R.id.email)
    EditText email;

    @BindView(R.id.address)
    EditText address;

    @BindView(R.id.emergency_phone)
    EditText emergency_phone;

    @BindView(R.id.emergency_email)
    EditText emergency_email;

    @BindView(R.id.emergency_first)
    EditText emergency_first;

    @BindView(R.id.emergency_last)
    EditText emergency_last;

    PanicApi serviceAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        serviceAPI =  PanicApiFactory.createService(PanicApi.class);
    }

    @OnClick(R.id.action_register)
    public void action_register(){
        serviceAPI.register(user_name.getText().toString(), first_name.getText().toString(),
                last_name.getText().toString(), email.getText().toString(), phone.getText().toString(),
                address.getText().toString(), emergency_phone.getText().toString(), emergency_email.getText().toString(),
                emergency_first.getText().toString(), emergency_last.getText().toString(), new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        //TODO: Save credentials on phone
                        //TODO: Throw a snackbar to Alert of success
                        //TODO: Save token
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //TODO: Throw a snackbar to Alert of error
                    }
                });
    }
}
