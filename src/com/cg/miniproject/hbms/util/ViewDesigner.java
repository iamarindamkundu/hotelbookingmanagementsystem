package com.cg.miniproject.hbms.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ViewDesigner {

	public static void tableView(List<Map<String, Object>> items, List<String> headerOrder) {
		Map<String, Integer> designersMap = new HashMap<>();

		Map<String, Object> colName = null;
		for (Map<String, Object> item : items) {
			Set<String> headers = item.keySet();

			for (String header : headers) {
				if (designersMap.containsKey(header)) {
					if (designersMap.get(header) < item.get(header).toString().length()) {
						designersMap.put(header, item.get(header).toString().length() + 2);
					}
				} else {
					designersMap.put(header, item.get(header).toString().length() > header.length()
							? item.get(header).toString().length() + 2 : header.length() + 2);
				}
			}
			colName = item;
		}

		Map<String, Object> header = new HashMap<>();
		for (String key : colName.keySet()) {
			header.put(key, key);
		}

		header = myOutputFormatter(header, designersMap);

		String hr = "";

		for (String attName : headerOrder) {
			hr += header.get(attName) + "| ";
		}

		for (int i = 1; i <= hr.length() + 5; i++)
			System.out.print("~");
		System.out.println();

		System.out.println(hr);

		for (int i = 1; i <= hr.length() + 5; i++)
			System.out.print("~");
		System.out.println();

		for (Map<String, Object> item : items) {

			item = myOutputFormatter(item, designersMap);

			String itemDetail = "";

			for (String attName : headerOrder)
				itemDetail += item.get(attName) + "| ";

			System.out.println(itemDetail);

		}
	}

	private static Map<String, Object> myOutputFormatter(Map<String, Object> emp, Map<String, Integer> designersMap) {
		for (String header : emp.keySet()) {

			int nameLen = emp.get(header).toString().length();

			for (int i = 1; i <= designersMap.get(header) - nameLen; i++) {

				emp.put(header, emp.get(header).toString() + " ");
			}
		}
		return emp;
	}
}
