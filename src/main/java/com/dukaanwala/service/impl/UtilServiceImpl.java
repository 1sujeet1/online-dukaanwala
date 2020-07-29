package com.dukaanwala.service.impl;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.dukaanwala.service.UtilService;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

/**
 * UtilServiceImpl
 */
@Service("utilService")
public class UtilServiceImpl implements UtilService {

    @Override
    public String convertDateToString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        return dateFormat.format(date);

    }

    @Override
    public String convertDateToCustomString(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    @Override
    public String convertDateToCustomString(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DATE);

        if (!((day > 10) && (day < 19)))
            switch (day % 10) {
            case 1:
                return new SimpleDateFormat("d'st' 'of' MMMM',' yyyy").format(date);
            case 2:
                return new SimpleDateFormat("d'nd' 'of' MMMM',' yyyy").format(date);
            case 3:
                return new SimpleDateFormat("d'rd' 'of' MMMM',' yyyy").format(date);
            default:
                return new SimpleDateFormat("d'th' 'of' MMMM',' yyyy").format(date);
            }
        return new SimpleDateFormat("d'th' 'of' MMMM',' yyyy").format(date);
    }

    @Override
    public String stringToMD5(String password) {
        return DigestUtils.md5Hex(password);
    }

    @Override
    public boolean autheticatePassword(String password1, String password2) {
        return password1.equalsIgnoreCase(DigestUtils.md5Hex(password2));
    }

    @Override
    public Date getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            return dateFormat.parse(dateFormat.format(new Date()));
        } catch (ParseException e) {

            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Date getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            System.out.println("only date :" + dateFormat.format(new Date()));
            return convertStringToDate(dateFormat.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Date convertStringToDate(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.parse(dateString);
    }

    @Override
    public Date convertCustomStringToDate(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        return dateFormat.parse(dateString);
    }

    @Override
    public String create4digitRandomNumber() {
        return String.format("%04d", new Random().nextInt(10000));
    }

    @Override
    public String generateReferalCode(String name) {
        StringBuilder generateString = new StringBuilder();
        if (name.length() > 3) {
            generateString.append(name.substring(0, 4));
        }
        generateString.append(String.format("%04d", generateRandomNumberwithLimit(10000)));
        return generateString.toString();
    }

    @Override
    public int generateRandomNumberwithLimit(int limit) {

        return (int) (Math.random() * limit);
    }

    public <T> Predicate<T> distinctByKeys(Function<? super T, ?>... keyExtractors) {
        final Map<List<?>, Boolean> seen = new ConcurrentHashMap<>();

        return t -> {
            final List<?> keys = Arrays.stream(keyExtractors).map(ke -> ke.apply(t)).collect(Collectors.toList());

            return seen.putIfAbsent(keys, Boolean.TRUE) == null;
        };
    }

    @Override
    public String addTimetoExistingTimeInString(String beforeTime, String afterTime) {
        if (beforeTime == null || afterTime == null) {
            return null;
        }

        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        try {
            Date dBefore = df.parse(beforeTime);
            Date dAfter = df.parse(afterTime);

            Calendar cal = Calendar.getInstance();
            cal.setTime(dBefore);
            cal.add(Calendar.SECOND, dAfter.getSeconds());
            cal.add(Calendar.MINUTE, dAfter.getMinutes());
            cal.add(Calendar.HOUR, dAfter.getHours());

            return df.format(cal.getTime());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public String formatPhoneNumber(String phone) {
        String phoneNumber = phone.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "$1-$2-$3");
        return phoneNumber;
    }

    @Override
    public String getCurrencySymbol(String currency) {
        String currencySymbol = "";
        switch(currency){
            case "INR":
            currencySymbol = "₹";
            break;
            case "EUR":
            currencySymbol = "€";
            break;
            case "POUND":
            currencySymbol = "£";
            break;
            default:
            currencySymbol = "$";

        }
        return currencySymbol;
    }

    @Override
    public String create6digitRandomPassword() {
        return String.format("%06d", new Random().nextInt(1000000));
    }

    @Override
    public String formatDoubleValue(double doubleValue, String format) {
        double input = doubleValue;
        DecimalFormat df2 = new DecimalFormat(format);
        String result = df2.format(input);
        return result;
    }

   
}