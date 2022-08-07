package es.library.databaseserver.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Utils {

	public static <T> Set<T> intersection(Collection<Set<T>> input) {
	    if(input.isEmpty()) {
	        return Collections.emptySet();
	    } else {
	        Set<T> first = input.iterator().next();
	        return first.stream().filter(e -> input.stream().allMatch(s -> s.contains(e))).collect(Collectors.toCollection(HashSet::new));
	    }
	}
	
}
