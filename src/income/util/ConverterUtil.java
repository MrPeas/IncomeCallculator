package income.util;

import java.math.BigDecimal;

/**
 * Created by Janusz on 05.12.2016.
 */
public class ConverterUtil {

    public static boolean isParseToBigDecimal(String number) {
        try {
            BigDecimal parsTest = new BigDecimal(number);
            if (parsTest.doubleValue() < 0.00) {
                throw new NumberFormatException();
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public static boolean isParseToString(String text) {
        try {
            if (text.isEmpty()) {
                return false;
            } else {
                return true;
            }
        } catch (NullPointerException e) {
            return false;
        }
    }
}