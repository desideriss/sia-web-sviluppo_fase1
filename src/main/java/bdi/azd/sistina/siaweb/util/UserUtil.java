package bdi.azd.sistina.siaweb.util;

import bdi.azd.sistina.siaweb.auth.util.RuoliUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class UserUtil {
	
	private UserUtil() {}
	
	public static String getLoggedUserId() {
		return RuoliUtil.getLoggedUser().getId();
	}
	
	
}
