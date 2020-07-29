package com.dukaanwala.service;

import java.text.ParseException;
import java.util.Date;
import java.util.function.Function;
import java.util.function.Predicate;


/**
 * UtilService
 */
public interface UtilService {

    public String convertDateToString(Date date);
    public String convertDateToCustomString(Date date);
    public String convertDateToCustomString(Date date, String format);
    public String stringToMD5(String password);
    public boolean autheticatePassword(String password1, String password2);
    public Date getCurrentTime();
    public Date getCurrentDate();
    public Date convertStringToDate(String dateString) throws ParseException;
    public Date convertCustomStringToDate(String dateString) throws ParseException;
    public String create4digitRandomNumber();
    public String create6digitRandomPassword();
    public String generateReferalCode(String name);
    public int generateRandomNumberwithLimit(int limit);
    public <T> Predicate<T> distinctByKeys(Function<? super T, ?>... keyExtractors); 
    public String addTimetoExistingTimeInString(String beforeTime,String afterTime);
    public String formatPhoneNumber(String phone);
    public String getCurrencySymbol(String currency);
    public String formatDoubleValue(double doubleValue, String format);

}