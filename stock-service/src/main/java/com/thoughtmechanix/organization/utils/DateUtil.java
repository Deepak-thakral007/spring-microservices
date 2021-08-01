package com.thoughtmechanix.organization.utils;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Component
public class DateUtil {

    int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
    private SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("dd-MMM-yyyy");
    private SimpleDateFormat outputFormat = new java.text.SimpleDateFormat("ddMMMyyyy");
    private String strStartDate;
    private String strCurrentDate;
    private String strEndDate;
    private Date startDate;
    private Date currentDate;
    private Date endDate;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

    private String day;
    private String month;
    private String year;

    public static void main(String str[]) throws ParseException {

        DateUtil dataUtil = new DateUtil();
    /*    dataUtil.setStartDate("10-Jun-2018");
        dataUtil.setEndDate("30-Jun-2018");
        Optional<String> date;
        while (dataUtil.nextDate().isPresent()) {
            System.out.println(dataUtil.getStrCurrentDate());
            // System.out.println(dataUtil.getCurrentDate().get(Calendar.DAY_OF_WEEK));

        }*/
        System.out.println(dataUtil.getDate("10-Jun-2018"));
        System.out.println(dataUtil.getFileMonth("10-Jun-2018"));
        System.out.println(dataUtil.getFileYear("10-Jun-2018"));
        ;
        // "https://www.nseindia.com/content/historical/EQUITIES/2018/JUL/cm05JUL2018bhav.csv.zip";
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getStartDate() {
        return strStartDate;
    }

    public void setStartDate(String strStartDate) {
        this.strStartDate = strStartDate;
    }

    public String getStrCurrentDate() {
        return outputFormat.format(currentDate).toUpperCase();
    }

    public Calendar getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        return calendar;
    }

    public void setCurrentDate(String strCurrentDate) {
        this.strCurrentDate = strCurrentDate;
    }

    public String getEndDate() {
        return strEndDate;
    }

    public void setEndDate(String strEndDate) {
        this.strEndDate = strEndDate;
    }

    public Optional<String> nextDate() throws ParseException {

        if (startDate == null || endDate == null) {
            startDate = dateFormat.parse(strStartDate);
            endDate = dateFormat.parse(strEndDate);
            currentDate = startDate;
            day = String.valueOf(getCurrentDate().get(Calendar.DAY_OF_MONTH));
            month = String.valueOf(getMonth(getCurrentDate().get(Calendar.MONTH)));
            year = String.valueOf(getCurrentDate().get(Calendar.YEAR));

            return Optional.ofNullable(outputFormat.format(currentDate));
        }

        final Calendar calendar = Calendar.getInstance();

        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        while (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        currentDate = calendar.getTime();
        day = String.valueOf(getCurrentDate().get(Calendar.DAY_OF_MONTH));
        month = String.valueOf(getMonth(getCurrentDate().get(Calendar.MONTH)));
        year = String.valueOf(getCurrentDate().get(Calendar.YEAR));
        return currentDate.compareTo(endDate) >= 1 ? Optional.empty()
                : Optional.ofNullable(outputFormat.format(calendar.getTime()));

    }

    public String getDate(String date) throws ParseException {

        return outputFormat.format(dateFormat.parse(date)).toUpperCase();

    }

    public int getFileYear(String date) throws ParseException {

        LocalDate local_date_1 = LocalDate.parse(date, dateTimeFormatter);
        return local_date_1.getYear();

    }

    public int getFileMonthInNumber(String date) {
        LocalDate local_date_1 = LocalDate.parse(date, dateTimeFormatter);
        return local_date_1.getMonth().getValue();

    }

    public String getFileMonth(String date) {
        LocalDate local_date_1 = LocalDate.parse(date, dateTimeFormatter);
        return getMonth(local_date_1.getMonth().getValue()-1);

    }

    private String getMonth(int i) {

        switch (i) {
            case 1:

                return "FEB";

            case 2:

                return "MAR";

            case 3:

                return "APR";

            case 4:

                return "MAY";

            case 5:

                return "JUN";

            case 6:

                return "JUL";
            case 7:

                return "AUG";

            case 8:

                return "SEP";

            case 9:

                return "OCT";

            case 10:

                return "NOV";

            case 11:

                return "DEC";

            case 0:

                return "JUN";

            default:

        }
        return "";
    }

    private String getDay(int i) {
        return "";
    }

}
