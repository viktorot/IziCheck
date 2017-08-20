package org.viktorot.izicheck.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;

import org.viktorot.izicheck.IziApplication;
import org.viktorot.izicheck.R;
import org.viktorot.izicheck.Storage;
import org.viktorot.izicheck.data.IziData;
import org.viktorot.izicheck.net.IziApiClient;
import org.viktorot.izicheck.parser.IziHtmlParser;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private SwipeRefreshLayout swipeRefreshLayout;
    private View dataLayout;
    private View errorLayout;

    private EditText etPhone;
    private EditText etPuk;
    private EditText etCaptcha;
    private ImageView imgCaptcha;

    private IziApiClient apiClient = new IziApiClient();

    private IziData data;

    private Storage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        storage = IziApplication.get(this).getStorage();

        swipeRefreshLayout = findViewById(R.id.root);
        swipeRefreshLayout.setOnRefreshListener(this::get);
        dataLayout = findViewById(R.id.data_layout);
        errorLayout = findViewById(R.id.error_layout);

        etPhone = findViewById(R.id.et_number);
        etPuk = findViewById(R.id.et_puk);
        etCaptcha = findViewById(R.id.et_captcha);
        Button check = findViewById(R.id.btn_check);
        check.setOnClickListener(view -> check());

        imgCaptcha = findViewById(R.id.captcha);

        get();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.open_settings:
                openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void get() {
        apiClient.getHtml()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe((disposable) -> {
                    hideErrorLayout();
                    hideDataLayout();
                    showLoading();
                })
                .map(IziData::new)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onDataReceived, this::onError);
    }

    private void check() {
        this.data.phone = etPhone.getText().toString();
        this.data.puk = etPuk.getText().toString();
        this.data.captcha = etCaptcha.getText().toString();

        apiClient.check(this.data)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe((disposable) -> {
                    Log.w(TAG, Thread.currentThread().getName());
                    showLoading();
                })
                .map(IziHtmlParser::parseAccountBalance)
                .delay(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onAccountBalanceReceived, this::onError);
    }

    private void onDataReceived(IziData data) {
        this.data = data;

        Log.v(TAG, data.toString());

        hideErrorLayout();
        hideLoading();
        showDataLayout();

        populateDataFromStorage();
        setCaptcha(data.captchaUrl);
    }

    private void onAccountBalanceReceived(String balance) {
        hideLoading();
        hideErrorLayout();

        new MaterialDialog.Builder(this)
                .title(R.string.balance)
                .content(balance)
                .positiveText(R.string.balance)
                .show();
    }

    private void onError(Throwable error) throws Exception {
        Log.e(TAG, "got some error", error);

        hideLoading();
        hideDataLayout();
        showErrorLayout();
    }

    private void populateDataFromStorage() {
        etPhone.setText(storage.getPhoneNumber());
        etPuk.setText(storage.getPuk());
    }

    private void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    private void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    private void showDataLayout() {
        dataLayout.setVisibility(View.VISIBLE);
    }

    private void hideDataLayout() {
        dataLayout.setVisibility(View.GONE);
    }

    private void showErrorLayout() {
        errorLayout.setVisibility(View.VISIBLE);
    }

    private void hideErrorLayout() {
        errorLayout.setVisibility(View.GONE);
    }

    private void setCaptcha(String url) {
        Picasso.with(this)
                .load(url)
                .into(imgCaptcha);
    }

    private void openSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
