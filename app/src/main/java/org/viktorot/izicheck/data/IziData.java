package org.viktorot.izicheck.data;

import android.text.TextUtils;

import org.jsoup.nodes.Document;
import org.viktorot.izicheck.parser.IziHtmlParser;

public class IziData {

    public String viewState = "";
    public String viewStateGenerator = "";
    public String eventValidation = "";
    public String captchaUrl = "";

    public String phone = "";
    public String puk = "";
    public String captcha = "";

    public IziData() {

    }

    public IziData(Document document) throws Exception {
        this.viewState = IziHtmlParser.parseViewState(document);
        this.viewStateGenerator = IziHtmlParser.parseViewStateGenerator(document);
        this.eventValidation = IziHtmlParser.parserEventValidation(document);
        this.captchaUrl = IziHtmlParser.parseCaptcha(document);
    }

    public boolean isSet() {
        return !TextUtils.isEmpty(this.viewState) &&
                !TextUtils.isEmpty(this.viewStateGenerator) &&
                !TextUtils.isEmpty(this.eventValidation) &&
                !TextUtils.isEmpty(this.captchaUrl);
    }

    @Override
    public String toString() {
        return "IziData{" +
                "viewState='" + viewState + '\n' +
                ", viewStateGenerator='" + viewStateGenerator + '\n' +
                ", eventValidation='" + eventValidation + '\n' +
                ", captchaUrl='" + captchaUrl + '\n' +
                '}';
    }
}
