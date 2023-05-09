package bdi.azd.sistina.siaweb.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	public static Timestamp getClosedDate() {
		Calendar cal = Calendar.getInstance();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		cal.setTimeInMillis(timestamp.getTime());
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return timestamp;
	}
	
	public static Date set00_00(Date target) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(target);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 1);
		return cal.getTime();
	}
	
	public static Date set23_59(Date target) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(target);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}
	
	public static Date inizioEventoMenoUno(Date dateNew) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateNew);
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}
	
	public static Date inizioEventoPiuUno(Date dateNew) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateNew);cal.add(Calendar.DATE, +1);
		return cal.getTime();
		}

	public static boolean equalsOnlyDate(Date date1, Date date2) {
		if (date1 == null && date2 == null) {
			return true;
		} else if ((date1 == null && date2 != null) || (date1 != null && date2 == null)) {
			return false;
		}
		if (DateUtil.set00_00(date1).equals(DateUtil.set00_00(date2))) {
			return true;
		}
		return false;
	}

}
