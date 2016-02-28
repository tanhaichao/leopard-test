package io.leopard.test;

import java.util.List;

import org.junit.Assert;

import io.leopard.burrow.refect.FieldUtil;

public class AssertList {

	public static void assertContainsKey(String fieldName, Object value, List<?> list) {
		boolean contains = false;
		for (Object element : list) {
			Object tmp = FieldUtil.getFieldValue(element, fieldName);
			if (tmp.equals(value)) {
				contains = true;
				break;
			}
		}
		if (!contains) {
			Assert.fail("元素不存在.");
		}
	}
}
