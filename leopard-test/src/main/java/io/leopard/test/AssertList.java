package io.leopard.test;

import java.util.List;

import org.junit.Assert;

import io.leopard.burrow.refect.FieldUtil;
import io.leopard.json.Json;

public class AssertList {

	public static void assertContainsKey(String fieldName, Object value, List<?> list) {
		Json.printList(list, "list");
		boolean contains = false;
		for (Object element : list) {
			Object tmp = FieldUtil.getFieldValue(element, fieldName);
			// System.out.println("tmp:" + tmp + " value:" + value);
			if (tmp.toString().equals(value.toString())) {// TODO ahai 未完整实现
				contains = true;
				break;
			}
		}
		if (!contains) {
			Assert.fail("元素[" + fieldName + "." + value + "]不存在.");
		}
	}
}
