package com.example.sosinventory.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


@UtilityClass
@Slf4j
public class TimeUtils {

    private final DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");


    public static LocalTime parseLocalTime(String timeAsString) {
        try {
            if (StringUtils.isBlank(timeAsString)) {
                return null;
            } else {
                if (StringUtils.containsIgnoreCase(timeAsString, ":")) {
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    return LocalTime.parse(timeAsString.substring(0, 8), dateTimeFormatter);
                } else {
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HHmmss");
                    return LocalTime.parse(timeAsString.substring(0, 6), dateTimeFormatter);
                }
            }
        } catch (DateTimeParseException e) {
            System.out.println(("parseLocalTime/ unable to parse " + timeAsString));
            return null;
        }
    }
}
