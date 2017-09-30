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
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
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
    private Button btnCheck;

    private IziApiClient apiClient = new IziApiClient();

    private IziData data = new IziData();

    private Storage storage;

    private CompositeDisposable compositeDisposable;

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
        btnCheck = findViewById(R.id.btn_check);
        btnCheck.setOnClickListener(view -> check());

        imgCaptcha = findViewById(R.id.captcha);
    }

    @Override
    protected void onStart() {
        super.onStart();
        compositeDisposable = new CompositeDisposable();
        populateUIFromStorage();

        if (!data.isSet()) {
            get();
        }
    }

    @Override
    protected void onStop() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
        super.onStop();
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
        Disposable disp = apiClient.getHtml()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe((disposable) -> {
                    hideErrorLayout();
                    hideDataLayout();
                    disableCheckButton();
                    showLoading();
                })
                .map(IziData::new)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onDataReceived, this::onError);

        compositeDisposable.add(disp);
    }

    private void check() {
        this.data.phone = etPhone.getText().toString();
        this.data.puk = etPuk.getText().toString();
        this.data.captcha = etCaptcha.getText().toString();

        Disposable disp = apiClient.check(this.data)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe((disposable) -> {
                    Log.w(TAG, Thread.currentThread().getName());
                    disableCheckButton();
                    showLoading();
                })
                .map(IziHtmlParser::parseAccountBalance)
                .delay(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onAccountBalanceReceived, this::onError);

        compositeDisposable.add(disp);
    }

    private void onDataReceived(IziData data) {
        this.data = data;

        Log.v(TAG, data.toString());

        hideErrorLayout();
        hideLoading();
        enableCheckButton();
        showDataLayout();

        etCaptcha.setText("");
        setCaptcha(data.captchaUrl);
    }

    private void onAccountBalanceReceived(String balance) {
        hideLoading();
        hideErrorLayout();
        enableCheckButton();

        // TODO: show try again

        new MaterialDialog.Builder(this)
                .title(R.string.balance)
                .content(balance)
                .positiveText(R.string.ok)
                .show();
    }

    private void onError(Throwable error) {
        Log.e(TAG, "something went wrong", error);

        hideLoading();
        hideDataLayout();
        enableCheckButton();
        showErrorLayout();
    }

    private void populateUIFromStorage() {
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

    private void enableCheckButton() {
        btnCheck.setEnabled(true);
    }

    private void disableCheckButton() {
        btnCheck.setEnabled(false);
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
