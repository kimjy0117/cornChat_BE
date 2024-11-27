package org.example.cornchat_be.domain.chat.entity.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.time.Instant;

public class DateUtil {
    public static Date convertToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime convertToLocalDateTime(Date date) {
        //Date를 Instatnt로 변환
        Instant instant = date.toInstant();

        // Instant를 시스템 기본 시간대에 맞는 LocalDateTime으로 변환
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}