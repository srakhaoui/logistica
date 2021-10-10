package com.logistica.service.mapper;

import com.logistica.service.dto.BonGasoilInfo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Boolean.FALSE;

@Component
public class MailiWorkBookParser {

    private static final Predicate<String> END_MATCHER = Pattern.compile("Totals of Vehicle:").asPredicate();
    private static final Predicate<String> DRIVER_MATCHER = Pattern.compile("Driver:").asPredicate();
    private static final Pattern MATRICULE_FINDER = Pattern.compile("T\\d+:?\\s*(\\w+)");
    private static final Predicate<String> FUELING_TOTAL_FINDER = Pattern.compile("Total:").asPredicate();
    private static final Pattern DATE_FUELING_FINDER = Pattern.compile("(\\d{2}/\\d{2}/\\d{4}\\s+\\d{2}:\\d{2})\\s+-");
    private static final SimpleDateFormat DATE_TIME_FORMATTER = new SimpleDateFormat("dd/MM/yyyy hh:mm");

    public List<BonGasoilInfo> parse(Workbook workbook) {
        return bonGasoilInfosFrom(workbook.getSheetAt(0));
    }

    private List<BonGasoilInfo> bonGasoilInfosFrom(Sheet reportSheet) {
        List<BonGasoilInfo> bonGasoilInfos = new ArrayList<>();
        String cellBValue = "";
        String matricule = "";
        int i = 0;
        do {
            Row row = reportSheet.getRow(i);
            if (isFuelingRow(row)) {
                Matcher matriculeMatcher = MATRICULE_FINDER.matcher(row.getCell(2).getStringCellValue());
                if (matriculeMatcher.find()) {
                    matricule = matriculeMatcher.group(1);
                    Row fuelingItem = reportSheet.getRow(++i);
                    do {
                        Matcher fuelingDateMatcher = DATE_FUELING_FINDER.matcher(getOrDefaultToEmpty(fuelingItem, 1));
                        if (fuelingDateMatcher.find()) {
                            LocalDateTime bonGasoilDateTs = getDateBonGasoilTs(fuelingDateMatcher);
                            float quantiteEnLitre = (float) fuelingItem.getCell(3).getNumericCellValue();
                            bonGasoilInfos.add(new BonGasoilInfo(matricule, bonGasoilDateTs, quantiteEnLitre));
                        }
                        fuelingItem = reportSheet.getRow(i++);
                    } while (!FUELING_TOTAL_FINDER.test(getOrDefaultToEmpty(fuelingItem, 1)));
                    i--;
                }
            }
            cellBValue = getCellBValue(reportSheet, i++);
        } while (!END_MATCHER.test(cellBValue));
        return bonGasoilInfos;
    }

    private LocalDateTime getDateBonGasoilTs(Matcher dateFuelingMatcher) {
        Date bonGasoilDate;
        try {
            bonGasoilDate = DATE_TIME_FORMATTER.parse(dateFuelingMatcher.group(1));
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(bonGasoilDate.getTime()), ZoneOffset.UTC);
    }

    private String getOrDefaultToEmpty(Row fuelingItem, int i) {
        return Optional.ofNullable(fuelingItem.getCell(i)).map(Cell::getStringCellValue).orElse("");
    }

    private boolean isNonEmpty(Row row) {
        return row.getCell(0) != null;
    }

    private Boolean isFuelingRow(Row row) {
        return Optional.ofNullable(row.getCell(0)).map(cell -> DRIVER_MATCHER.test(cell.getStringCellValue())).orElse(FALSE);
    }

    private String getCellBValue(Sheet reportSheet, int i) {
        return Optional.ofNullable(reportSheet.getRow(i)).map(aRow -> aRow.getCell(1)).map(Cell::getStringCellValue).orElse("");
    }
}
