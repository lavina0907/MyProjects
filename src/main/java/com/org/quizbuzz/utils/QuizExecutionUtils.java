package com.org.quizbuzz.utils;

import com.org.quizbuzz.enums.DurationType;
import java.time.LocalDateTime;

public class QuizExecutionUtils {

  public static LocalDateTime calculateEndTime(LocalDateTime startTime, Integer duration,
      DurationType durationType) {
    LocalDateTime endTime = null;
    if (durationType.equals(DurationType.MINUTES)) {
      endTime = startTime.plusMinutes(duration);
    } else if (durationType.equals(DurationType.HOURS)) {
      endTime = startTime.plusHours(duration);
    } else if (durationType.equals(DurationType.DAYS)) {
      endTime = startTime.plusDays(duration);
    }
    return endTime;
  }
}
