package income.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Created by Janusz on 02.11.2016.
 */
public class DateUtil {
        private static final String DATE_PATTER = "dd.MM.yyyy";
        private static final DateTimeFormatter DATE_FORMATTER =
                DateTimeFormatter.ofPattern(DATE_PATTER);

        public static String format(LocalDate date) {
            if (date == null) {
                return null;
            }
            return DATE_FORMATTER.format(date);
        }

        public static LocalDate parse(String dateString) {
            try {
                return DATE_FORMATTER.parse(dateString, LocalDate::from);
            } catch (DateTimeParseException e) {
                return null;
            }
        }

        public static boolean validDate(String dateString) {
            if (DateUtil.parse(dateString) != null) return true;
            else return false;
        }
    }

