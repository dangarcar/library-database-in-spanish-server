package es.library.databaseserver.shared;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.context.SecurityContextHolder;

public class Utils {

	public static void logActionByUser(Class<?> caller, String action, Level logLevel) {
		Logger logger = LogManager.getLogger(caller);
		
		String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		logger.log(logLevel, "Usuario {} {}.", username.equals("anonymousUser")? "anónimo":username, action);
	}
	
	public static RowMapper<Long> idRowMapper = (rs, rowNum) -> {
			return rs.getLong("ID");
	};
	
	public static <T> Set<T> intersection(Collection<Set<T>> input) {
	    if(input.isEmpty()) {
	        return Collections.emptySet();
	    } else {	    	
	        Set<T> first = input.iterator().next();
	        return first.stream().filter(e -> input.stream().allMatch(s -> s.contains(e))).collect(Collectors.toCollection(HashSet::new));
	    }
	}
	
}
