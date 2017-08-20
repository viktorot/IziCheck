package org.viktorot.izicheck.net;


import org.jsoup.nodes.Document;
import org.viktorot.izicheck.Constants;
import org.viktorot.izicheck.data.IziData;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class IziApiClient {

    private final IziApi api;

    public IziApiClient() {
        OkHttpClient client = new  OkHttpClient.Builder()
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.IZI_URL)
                .client(client)
                .addConverterFactory(PageAdapter.FACTORY)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        api = retrofit.create(IziApi.class);
    }

    public Single<Document> getHtml() {
        return api.getHtml();
    }

    public Single<Document> check(IziData data) {
        return api.check(
                Constants.VALUE_EVENTTARGET,
                Constants.VALUE_EVENTARGUMENT,
                data.viewState,
                data.viewStateGenerator,
                data.eventValidation,
                data.phone,
                data.puk,
                data.captcha
        );
    }
}
