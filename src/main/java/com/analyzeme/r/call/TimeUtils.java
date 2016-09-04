package com.analyzeme.r.call;

import com.analyzeme.data.dataset.DataEntry;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lagroffe on 28.07.2016 11:56
 */
public class TimeUtils {

    private static boolean checkOrder(List<LocalDate> time) {
        for (int i = 0; i < time.size() - 1; i++) {
            if (time.get(i).compareTo(time.get(i + 1)) > 0) {
                return false;
            }
        }
        return true;
    }

    public static int getFrequency(
            List<DataEntry> time) {
        if (time == null || time.size() < 2) {
            throw new IllegalArgumentException("Not long enough");
        }
        List<LocalDate> timeList = new ArrayList<>();
        for (DataEntry entry : time) {
            timeList.add(entry.getDateValue());
        }
        if (!checkOrder(timeList)) {
            throw new IllegalArgumentException("not supported");
        }
        return calculateFrequency(timeList);
    }

    //now it is supposed here that all series have one period only
    //TODO: add parameter like year, month or some other to change a number of periods
    private static int calculateFrequency(
            final List<LocalDate> time) {
        long duration = ChronoUnit.DAYS.between(time.get(0), time.get(1));
        int counter = 2;
        long temp;
        for (int i = 1; i < time.size() - 1; i++) {
            temp = ChronoUnit.DAYS.between(time.get(i), time.get(i + 1));
            if (temp != duration) {
                throw new IllegalArgumentException("not supported: should be level");
            }
            counter++;
        }
        return counter;
    }
}
