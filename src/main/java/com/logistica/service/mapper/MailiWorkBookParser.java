package com.logistica.service.mapper;

import com.logistica.service.dto.BonGasoilInfo;
import com.logistica.web.rest.errors.BadRequestAlertException;
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
                            bonGasoilInfos.add(bonGasoilInfoFrom(matricule, fuelingDateMatcher, fuelingItem));
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

    private BonGasoilInfo bonGasoilInfoFrom(String matricule, Matcher fuelingDateMatcher, Row fuelingItem) {
        return BonGasoilInfo.newInstance()
            .matricule(matricule)
            .dateBonGasoil(getDateBonGasoilTs(fuelingDateMatcher))
            .quantiteEnLitre(getFloatValueAtIndex(fuelingItem.getCell(3), "Quantite en Litre non fournie", "gasoil.quantite.non.fournie"))
            .prixUnitaire(getFloatValueAtIndex(fuelingItem.getCell(5), "Prix unitaire non fournie", "gasoil.prix.unitaire.non.fournie"))
            .numeroBonGasoil(getLongValueAtIndex(fuelingItem.getCell(8), "Numero du bon de gasoil non fournie", "gasoil.bon.gasoil.non.fournie"))
            .kilometrageInitial(getIntValueAtIndex(fuelingItem.getCell(9), "Kilometrage initial non fournie", "gasoil.kilometrage.initial.non.fournie"))
            .kilometrageFinal(getIntValueAtIndex(fuelingItem.getCell(10), "Kilometrage final non fourni", "gasoil.kilometrage.final.non.fourni"))
            .citerne(Optional.ofNullable(fuelingItem.getCell(11)).map(Cell::getStringCellValue).orElseThrow(() -> new BadRequestAlertException("Citerne non fournie", "Gasoil", "Citerne non renseignée")));
    }

    private Float getFloatValueAtIndex(Cell cell, String missingMessage, String errorKey) {
        return Optional.ofNullable(cell).map(Cell::getNumericCellValue).map(Double::floatValue).orElseThrow(() -> new BadRequestAlertException(missingMessage, "Gasoil", errorKey));
    }

    private Long getLongValueAtIndex(Cell cell, String missingMessage, String errorKey) {
        return Optional.ofNullable(cell).map(Cell::getNumericCellValue).map(Double::longValue).orElseThrow(() -> new BadRequestAlertException(missingMessage, "Gasoil", errorKey));
    }

    private Integer getIntValueAtIndex(Cell cell, String missingMessage, String errorKey) {
        return Optional.ofNullable(cell).map(Cell::getNumericCellValue).map(Double::intValue).orElseThrow(() -> new BadRequestAlertException(missingMessage, "Gasoil", errorKey));
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
