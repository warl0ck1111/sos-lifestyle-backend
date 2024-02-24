package com.example.sosinventory.utils;

import lombok.experimental.UtilityClass;

import java.util.Locale;

@UtilityClass
public class ReferentialUtils {
    public Locale getLocale(Language language) {

        if (language != null) {

            if (language == Language.French) {
                return Locale.FRENCH;
            } else if (language == Language.English) {
                return Locale.ENGLISH;
            } else if (language == Language.German) {
                return Locale.GERMAN;
            } else if (language == Language.Italian) {
                return Locale.ITALIAN;
            } else if (language == Language.Dutch) {
                return new Locale("nl");
            } else if (language == Language.Japanese) {
                return new Locale("ja");
            } else if (language == Language.Spanish) {
                return new Locale("es");
            } else if (language == Language.Portuguese) {
                return new Locale("pt", "PT");
            } else if (language == Language.Portuguese_Brazil) {
                return new Locale("pt", "BR");
            } else if (language == Language.Polish) {
                return new Locale("pl");
            } else if (language == Language.Arabic) {
                return new Locale("ar");
            } else if (language == Language.Russian) {
                return new Locale("ru");
            } else if (language == Language.Swedish) {
                return new Locale("sv");
            } else if (language == Language.Thai) {
                return new Locale("th");
            } else if (language == Language.Turkish) {
                return new Locale("tr");
            } else if (language == Language.Chinese) {
                return Locale.PRC;
            } else if (language == Language.Norwegian) {
                return new Locale("no");
            } else if (language == Language.Danish) {
                return new Locale("da");
            } else if (language == Language.Finnish) {
                return new Locale("fi");
            } else if (language == Language.Indonesian) {
                return new Locale("id");
            } else if (language == Language.Korean) {
                return Locale.KOREAN;
            } else {
                return null;
            }

        } else {
            return Locale.ENGLISH;
        }
    }

}
