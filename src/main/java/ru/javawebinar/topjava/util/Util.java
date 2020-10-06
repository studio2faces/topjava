package ru.javawebinar.topjava.util;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public class Util {

   private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final AtomicInteger count = new AtomicInteger();

    public static DateTimeFormatter getDateTimeFormatter() {
        return dateTimeFormatter;
    }

    public static int increment(){
        return count.incrementAndGet();
    }
}
