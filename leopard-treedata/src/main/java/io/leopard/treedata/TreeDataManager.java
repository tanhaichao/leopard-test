package io.leopard.treedata;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class TreeDataManager {

	public List<Children> parse() throws IOException {
		List<Row> rows = this.load();
		List<Children> list = new ArrayList<Children>();
		for (Row row : rows) {
			if (row.getFloor() == 0) {
				Children children = new Children(row.getName());
				list.add(children);
			}
		}

		int preFloor = 0;
		Children preChildren = null;
		Map<Integer, Children> parentMap = new HashMap<Integer, Children>();

		int index = 0;// 大类的位置
		for (Row row : rows) {
			if (row.getFloor() <= 0) {
				parentMap.clear();
				preChildren = list.get(index);
				parentMap.put(0, preChildren);
				index++;
				continue;
			}
			String name = row.getName();
			String colour = row.getColour();

			int floor = row.getFloor();
			int diff = row.getFloor() - preFloor;
			preFloor = row.getFloor();
			if (diff > 0) {
				// 下一级
				Children c = preChildren.add(name, colour);
				System.err.println("下 floor:" + floor + " diff:" + diff + " name:" + name + " c:" + c.getName() + " pre:" + preChildren.getName());
				parentMap.put(floor, c);
				preChildren = c;
			}
			else if (diff < 0) {
				// 上一级
				Children c = parentMap.get(floor - 1);
				System.err.println("上 floor:" + floor + " diff:" + diff + " name:" + name + " c:" + c.getName() + " pre:" + preChildren.getName());
				preChildren = c.add(name, colour);
				parentMap.put(floor, preChildren);
			}
			else {
				// 同级
				System.out.println("floor - 1:" + (floor - 1));
				Children c = parentMap.get(floor - 1);
				Children current = c.add(name, colour);
				parentMap.put(floor, current);
				System.err.println("同 floor:" + floor + " diff:" + diff + " name:" + name + " c:" + c.getName() + " pre:" + preChildren.getName());
				preChildren = current;
			}
		}
		return list;
	}

	public List<Row> load() throws IOException {
		Resource resource = new ClassPathResource("/wheel.txt");
		InputStream is = resource.getInputStream();
		String content = IOUtils.toString(is);
		is.close();
		// System.out.println("content:" + content);
		content = content.replace("\r", "");
		String[] lines = content.split("\n");

		List<Row> list = new ArrayList<Row>();
		for (String line : lines) {
			if (StringUtils.isBlank(line)) {
				continue;
			}
			int floor = this.floor(line);
			Row row = new Row();
			row.setFloor(floor);

			line = line.trim();
			String[] blocks = line.split("\t+");
			row.setName(blocks[0].trim());
			if (blocks.length > 1) {
				row.setColour(blocks[1].trim());
			}
			// System.out.println("floor:" + floor);
			list.add(row);
		}
		return list;
	}

	protected int floor(String line) {
		int floor = 0;
		for (char c : line.toCharArray()) {
			if (c == '\t') {
				floor++;
			}
			else {
				break;
			}
		}
		return floor;
	}
}
