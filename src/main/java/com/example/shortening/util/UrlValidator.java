package com.example.shortening.util;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlValidator {

    public static String validate(String targetUrl) {
        StringBuilder errors = new StringBuilder();

        if(targetUrl.indexOf(" ") > -1) {
            errors.append("URL 사이에 공백이 들어갈 수 없습니다. \n");
        }

        if(StringUtils.isEmpty(targetUrl)) {
            errors.append("URL은 공백일 수 없습니다. \n");
        }

        Pattern p = Pattern.compile("^(https?):\\/\\/([^:\\/\\s]+)(:([^\\/]*))?((\\/[^\\s/\\/]+)*)?\\/?([^#\\s\\?]*)(\\?([^#\\s]*))?(#(\\w*))?$");
        Matcher m = p.matcher(targetUrl);
        if(!m.matches()) {
            errors.append("URL 형식이 맞지 않습니다. \n");
        }

        return errors.toString();
    }
}
