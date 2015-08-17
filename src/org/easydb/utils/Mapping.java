package org.easydb.utils;

import java.lang.reflect.Field;

public class Mapping {
	public static boolean checkEntityMapping(Object[] dbColumns,
			Object[] mappedWithEntity, Field[] declaredFieldsOfEntity) {

		boolean columnMatched = false;

		// Checks length of the 3 sets of fields
		if ((dbColumns.length != mappedWithEntity.length)
				|| (mappedWithEntity.length != declaredFieldsOfEntity.length))
			return false;

		// Checks if declaredField names are same as mappedField names
		for (Field declaredField : declaredFieldsOfEntity) {
			columnMatched = false;
			for (Object mappedField : mappedWithEntity) {
				if (((String) mappedField).equals(declaredField.getName())) {
					columnMatched = true;
					break;
				}
			}
			if (!columnMatched)
				return false;
		}

		return true;
	}
}
