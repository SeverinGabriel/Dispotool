package ch.gabrieltransport.auftragverwaltung.business;

import java.time.LocalDateTime;

public class TimeHelper {

	public static boolean isDaylong(LocalDateTime start, LocalDateTime end){
		return start.getHour() == 0 && end.getHour() == 0;
	}
	public static boolean isMorning(LocalDateTime start, LocalDateTime end){
		if(start.getDayOfYear() == end.getDayOfYear()){
			if(start.getHour() >= 5  && end.getHour() <= 12){
				return true;
			}
		}
		return false;
	}
	public static boolean isAfternoon(LocalDateTime start, LocalDateTime end){
		if(start.getDayOfYear() == end.getDayOfYear()){
			if(start.getHour() >= 12  && end.getHour() <= 22){
				return true;
			}
		}
		return false;
	}
	public static boolean isExactlyMorning(LocalDateTime start, LocalDateTime end){
		if(start.getDayOfYear() == end.getDayOfYear()){
			if(start.getHour() == 8  && end.getHour() == 12){
				return true;
			}
		}
		return false;
	}
	public static boolean isExactlyAfternoon(LocalDateTime start, LocalDateTime end){
		if(start.getDayOfYear() == end.getDayOfYear()){
			if(start.getHour() == 13  && end.getHour() == 18){
				return true;
			}
		}
		return false;
	}
}
