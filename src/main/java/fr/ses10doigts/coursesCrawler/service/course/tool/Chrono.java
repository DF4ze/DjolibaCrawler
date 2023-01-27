package fr.ses10doigts.coursesCrawler.service.course.tool;

import java.time.LocalTime;
import java.util.Date;

public class Chrono {


    private long time1;
    private long time2;

    public Chrono() {
    }

    public void pick() {
	time1 = new Date().getTime();
    }

    public long compare() {
	long delta = 0;

	time2 = new Date().getTime();
	delta = time2-time1;

	return delta;
    }

    public String compareToHour() {
	long millis = compare();
	long seconds = millis / 1000;
	LocalTime timeOfDay = LocalTime.ofSecondOfDay(seconds);
	String time = timeOfDay.toString();

	return time;
    }
}
