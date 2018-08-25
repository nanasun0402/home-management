package com.caring.dao.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author james
 */
public class DaoUtils {

    protected static Logger LOG = LoggerFactory.getLogger(DaoUtils.class);

    protected static final String YYYYMMDD_PATTERN = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)";

    public static boolean matchesStandardDateFormat(String date) {
        return StringUtils.isNotEmpty(date) && date.matches(YYYYMMDD_PATTERN);
    }

    public static SimpleDateFormat getStandardFormatOnyyyyMMddHHmm() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm");
    }

    public static SimpleDateFormat getStandardFormatOnyyyyMMdd() {
        return new SimpleDateFormat("yyyy-MM-dd");
    }

    public static SimpleDateFormat getStandardFormatOnHHmm() {
        return new SimpleDateFormat("HH:mm");
    }

    public static String getCurrentTimeOnyyyyMMddHHmm() {
        return getStandardFormatOnyyyyMMddHHmm().format(Calendar.getInstance().getTime());
    }

    public static String getCurrentTimeOnyyyyMMdd() {
        return getStandardFormatOnyyyyMMdd().format(Calendar.getInstance().getTime());
    }

    public static Date parse(SimpleDateFormat formater, String date) {
        try {
            return formater.parse(date);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Date parseAsStandardDateFormat(String date) {
        try {
            return DaoUtils.getStandardFormatOnyyyyMMdd().parse(date);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String toJson(Object obj) {
        try {
            ObjectMapper JSON = new ObjectMapper();
            JSON.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            JSON.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, Boolean.FALSE);
            JSON.configure(SerializationFeature.INDENT_OUTPUT, Boolean.TRUE);
            JSON.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, Boolean.FALSE);
            return JSON.writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            LOG.error("{}", ex.fillInStackTrace());
            throw new RuntimeException("序列化JSON错误");
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            ObjectMapper JSON = new ObjectMapper();
            return JSON.readValue(json, clazz);
        } catch (IOException ex) {
            LOG.error("{}", ex.fillInStackTrace());
            throw new RuntimeException("解析JSON错误");
        }
    }

    public static JsonNode fromJson(String json) {
        try {
            ObjectMapper JSON = new ObjectMapper();
            JSON.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            JSON.configure(SerializationFeature.INDENT_OUTPUT, Boolean.TRUE);
            return JSON.readTree(json);
        } catch (IOException ex) {
            LOG.error("{}", ex.fillInStackTrace());
            throw new RuntimeException("JSON 读取错误");
        }
    }
}
