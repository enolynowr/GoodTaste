package com.goodtaste;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.goodtaste.api.ApiService;
import com.goodtaste.api.ApiServiceImpl;
import com.goodtaste.model.GoodTasteResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = "@@@@@_MainActivity_TAG";
    public static ProgressDialog mProgressDialog;

    TextView textViewId, textViewName, textViewFriends;

    EditText et_id;

    Button btn_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Good Taste");

        et_id = (EditText) findViewById(R.id.edittext_keyword);

        btn_search = (Button) findViewById(R.id.btn_search);

        mProgressDialog = new ProgressDialog(MainActivity.this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("Loading...");

        btn_search.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                String input_id = et_id.getText().toString();
                int input_id_number = Integer.parseInt(input_id);

                if (input_id.length() == 0 ) {
                    Toast.makeText(getApplicationContext(), "Please enter id", Toast.LENGTH_LONG).show();
                    return;
                } else if(input_id_number > 10 ){
                    Toast.makeText(getApplicationContext(), "検索のIDは１~10番まで入力をお願いいたします。", Toast.LENGTH_LONG).show();
                    return;
                }
                setSearch(input_id);
            }
        });
    }

    public void setSearch(String _id) {

        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        //@@@@@ Network Check(Wifiが繋がっているかを確認)
        if (mobile.isConnected() || wifi.isConnected()) {
            //mProgressDialog.show();
        } else {
            Toast.makeText(getApplicationContext(), "Please check your network", Toast.LENGTH_LONG).show();
            return;
        }

        //api通信
        ApiServiceImpl apiServiceImpl = new ApiServiceImpl();
        ApiService apiService = apiServiceImpl.getApiService();
        Call<GoodTasteResponseModel> call = apiService.getGoodTasteResponse(_id);

        call.enqueue(new Callback<GoodTasteResponseModel>() {
            /**
             * Invoked for a received HTTP response.
             * <p>
             * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
             * Call {@link Response#isSuccessful()} to determine if the response indicates success.
             *
             * @param call
             * @param response
             */
            @Override
            public void onResponse(Call<GoodTasteResponseModel> call, Response<GoodTasteResponseModel> response) {

                if (!response.isSuccessful()) {
                    Log.d(LOG_TAG, "API Response Fail");
                    Toast.makeText(getApplicationContext(), "API Response Fail", Toast.LENGTH_LONG).show();
                    return;
                }

                Log.d(LOG_TAG, "API Response Success");

                GoodTasteResponseModel res = response.body();

                textViewId = (TextView) findViewById(R.id.tv_api_id);
                textViewName = (TextView) findViewById(R.id.tv_api_name);
                textViewFriends = (TextView) findViewById(R.id.tv_api_friends);
                textViewId.setText(res.getId());
                textViewName.setText(res.getName());

                String friendsList = "";

                for (int i = 0; i < res.getFriends().size(); i++) {
                    friendsList += res.getFriends().get(i);
                    friendsList += "\n";
                }

                textViewFriends.setText(friendsList);
            }

            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected
             * exception occurred creating the request or processing the response.
             *
             * @param call
             * @param t
             */
            @Override
            public void onFailure(Call<GoodTasteResponseModel> call, Throwable t) {
                Log.d(LOG_TAG, "API Request Fail");
                Toast.makeText(getApplicationContext(), "API Request Fail", Toast.LENGTH_LONG).show();
            }
        });
    }
}
