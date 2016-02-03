package com.britesnow.contactsdemo;

import java.util.Arrays;

public interface ErrorType {

	public String getMessage();

	default String formatMessage(Object... vals) {

		// here we convert the Array values in string for better display
		vals = Arrays.asList(vals).stream().map(v ->
						(v.getClass().isArray())?Arrays.toString((Object[])v):v
		).toArray();

		return String.format(getMessage(), vals);
	}
}
