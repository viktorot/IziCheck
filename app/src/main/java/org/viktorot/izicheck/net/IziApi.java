package org.viktorot.izicheck.net;


import org.jsoup.nodes.Document;
import org.viktorot.izicheck.Constants;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IziApi {

    @GET("/profil/PreverjanjeStanja.aspx")
    Single<Document> getHtml();

    @FormUrlEncoded
    @POST("/profil/PreverjanjeStanja.aspx")
    Single<Document> check(
        @Field(Constants.REQ_FIELD_EVENTTARGET) String eventTarget,
        @Field(Constants.REQ_FIELD_EVENTARGUMENT) String eventArgument,
        @Field(Constants.REQ_FIELD_VIEWSTATE) String viewState,
        @Field(Constants.REQ_FIELD_VIEWSTATEGENERATOR) String viewStateGenerator,
        @Field(Constants.REQ_FIELD_VALIDATION) String eventValidator,
        @Field(Constants.REQ_FIELD_PHONE_NUMBER) String phoneNumber,
        @Field(Constants.REQ_FIELD_PUK) String puk,
        @Field(Constants.REQ_FIELD_CAPTCHA) String captcha
    );

}
