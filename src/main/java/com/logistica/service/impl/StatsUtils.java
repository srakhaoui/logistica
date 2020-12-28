package com.logistica.service.impl;

import com.logistica.service.dto.Courbe;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.util.Map;
import java.util.TreeMap;

public class StatsUtils {

    public static final DateTimeFormatter MONTH_AS_NUMBER_FORMAT = DateTimeFormatter.ofPattern("M");
    public static final DateTimeFormatter MONTH_AS_STRING_FORMAT = DateTimeFormatter.ofPattern("MMM");
    public static final DateTimeFormatter MONTH_YEAR_AS_STRING_FORMAT = DateTimeFormatter.ofPattern("MMMyyyy");

    public static Courbe<String, Float> mapToCourbe(LocalDate dateDebut, LocalDate dateFin, Map<String, Float> statisticsByYearMonthMap) {
        completeMissingDates(dateDebut, dateFin, statisticsByYearMonthMap);
        Map<String, Float> chiffreAffaireTrieParDateMap = sortByYearAndMonth(statisticsByYearMonthMap);
        Courbe<String, Float> courbe = new Courbe<>();
        chiffreAffaireTrieParDateMap.entrySet().forEach(chiffreAffaireParMois -> {
            courbe.getAbscisses().add(chiffreAffaireParMois.getKey());
            courbe.getOrdonnees().add(chiffreAffaireParMois.getValue());
        });
        return courbe;
    }

    private static void completeMissingDates(LocalDate dateDebut, LocalDate dateFin, Map<String, Float> chiffreAffaireByYearMonthMap) {
        long durationInMonths = ChronoUnit.MONTHS.between(dateDebut, dateFin);
        for (int i = 0; i <= durationInMonths; i++) {
            LocalDate entryDate = dateDebut.plusMonths(i);
            String moisAnneeAsStr = getMoisAnneeAsStr(entryDate.getYear(), entryDate.getMonthValue());
            if (!chiffreAffaireByYearMonthMap.containsKey(moisAnneeAsStr)) {
                chiffreAffaireByYearMonthMap.put(moisAnneeAsStr, 0.0F);
            }
        }
    }

    public static String getMoisAnneeAsStr(Integer year, Integer month) {
        TemporalAccessor monthAsNumber = MONTH_AS_NUMBER_FORMAT.parse(month.toString());
        String monthAsStr = MONTH_AS_STRING_FORMAT.format(monthAsNumber);
        return monthAsStr + year;
    }

    private static Map<String, Float> sortByYearAndMonth(Map<String, Float> statisticsByYearMonthMap) {
        Map<String, Float> chiffreAffaireTrieParDateMap = new TreeMap<>(StatsUtils::getYearMonthComparator);
        chiffreAffaireTrieParDateMap.putAll(statisticsByYearMonthMap);
        return chiffreAffaireTrieParDateMap;
    }

    private static int getYearMonthComparator(String startDateAsStr, String endDateAsStr) {
        TemporalAccessor startDate = MONTH_YEAR_AS_STRING_FORMAT.parse(startDateAsStr);
        TemporalAccessor endDate = MONTH_YEAR_AS_STRING_FORMAT.parse(endDateAsStr);
        int compraison = 0;
        int yearOfStartDate = startDate.get(ChronoField.YEAR);
        int yearOfEndDate = endDate.get(ChronoField.YEAR);
        if (yearOfStartDate > yearOfEndDate) {
            compraison = 1;
        } else if (yearOfStartDate < yearOfEndDate) {
            compraison = -1;
        } else {
            int monthOfStartDate = startDate.get(ChronoField.MONTH_OF_YEAR);
            int monthOfEndDate = endDate.get(ChronoField.MONTH_OF_YEAR);
            if (monthOfStartDate > monthOfEndDate) {
                compraison = 1;
            } else if (monthOfStartDate < monthOfEndDate) {
                compraison = -1;
            }
        }
        return compraison;
    }
}
