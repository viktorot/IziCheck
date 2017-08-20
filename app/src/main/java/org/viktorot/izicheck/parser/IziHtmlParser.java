package org.viktorot.izicheck.parser;


import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.viktorot.izicheck.Constants;

import io.reactivex.annotations.NonNull;

public class IziHtmlParser {

    private IziHtmlParser() { }

    public static String parseViewState(@NonNull Document doc) throws Exception {
        Element el = doc.getElementById(Constants.HTML_ELEMENT_VIEWSTATE);
        if (el == null) {
            throw new Exception("element => " + Constants.HTML_ELEMENT_VIEWSTATE + " not found");
        }
        return el.val();
    }

    public static String parseViewStateGenerator(@NonNull Document doc) throws Exception {
        Element el = doc.getElementById(Constants.HTML_ELEMENT_VIEWSTATEGENERATOR);
        if (el == null) {
            throw new Exception("element => " + Constants.HTML_ELEMENT_VIEWSTATEGENERATOR + " not found");
        }
        return el.val();
    }

    public static String parserEventValidation(@NonNull Document doc) throws Exception {
        Element el = doc.getElementById(Constants.HTML_ELEMENT_VALIDATION);
        if (el == null) {
            throw new Exception("element => " + Constants.HTML_ELEMENT_VALIDATION + " not found");
        }
        return el.val();
    }

    public static String parseCaptcha(@NonNull Document doc) throws Exception {
        Element el = doc.getElementById(Constants.HTML_ELEMENT_CAPTCHA);
        if (el == null) {
            throw new Exception("element => " + Constants.HTML_ELEMENT_CAPTCHA + " not found");
        }
        Element img = el.select("img").first();
        String src = img.attr("src");
        return String.format("%s%s", Constants.CAPTCHA_BASE_URL, src);
    }

    public static String parseAccountBalance(@NonNull Document doc) throws Exception {
        Element table = doc.getElementsByClass(Constants.HTML_ELEMENT_RESULT_TABLE).first();
        if (table == null) {
            throw new Exception("element => " + Constants.HTML_ELEMENT_RESULT_TABLE + " not found");
        }
        Element tableBody = table.select("tbody").first();
        Element row = tableBody.select("tr").last();

        return row.text();
    }
}
