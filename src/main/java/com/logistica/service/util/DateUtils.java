package com.logistica.service.util;

import java.time.LocalDate;

public class DateUtils {

    public static boolean isValidInvoicePeriod(LocalDate startDate, LocalDate endDate) {
        // Check if start date is first day of the month
        boolean isFirstDayOfMonth = startDate.getDayOfMonth() == 1;

        // Check if end date is last day of the month
        boolean isLastDayOfMonth = endDate.getDayOfMonth() == endDate.lengthOfMonth();

        // Check if the duration between start date and end date does not exceed one month
        boolean isWithinOneMonth = startDate.plusMonths(1).minusDays(1).isEqual(endDate);

        // Return true if all conditions are met
        return isFirstDayOfMonth && isLastDayOfMonth && isWithinOneMonth;
    }
}
