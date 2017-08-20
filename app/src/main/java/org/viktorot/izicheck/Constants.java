package org.viktorot.izicheck;

public class Constants {
    private Constants() { }

    public static final String IZI_URL = "https://na.izimobil.si";
    public static final String CAPTCHA_BASE_URL = IZI_URL + "/profil/";

    public static final String REQ_FIELD_EVENTTARGET = "__EVENTTARGET";
    public static final String REQ_FIELD_EVENTARGUMENT = "__EVENTARGUMENT";
    public static final String REQ_FIELD_VIEWSTATE = "__VIEWSTATE";
    public static final String REQ_FIELD_VIEWSTATEGENERATOR = "__VIEWSTATEGENERATOR";
    public static final String REQ_FIELD_VALIDATION = "__EVENTVALIDATION";
    public static final String REQ_FIELD_PHONE_NUMBER = "ctl00$MainContent$txtMSISDN";
    public static final String REQ_FIELD_PUK = "ctl00$MainContent$txtPUK";
    public static final String REQ_FIELD_CAPTCHA = "ctl00$MainContent$txtCaptcha1";

    public static final String HTML_ELEMENT_VIEWSTATE = REQ_FIELD_VIEWSTATE;
    public static final String HTML_ELEMENT_VIEWSTATEGENERATOR = REQ_FIELD_VIEWSTATEGENERATOR;
    public static final String HTML_ELEMENT_VALIDATION = REQ_FIELD_VALIDATION;

    public static final String HTML_ELEMENT_CAPTCHA = "MainContent_trCaptchaImage";
    public static final String HTML_ELEMENT_RESULT_TABLE = "bg_Table";

    public static final String VALUE_EVENTTARGET = "ctl00$MainContent$btnCheckBalance";
    public static final String VALUE_EVENTARGUMENT = "";
}
