package io.leopard.treedata;

import java.util.ArrayList;
import java.util.List;

public class Row {

	private int floor;

	private List<String> data = new ArrayList<String>();

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public String getData(int index) {
		if (index >= data.size()) {
			return null;
		}
		return data.get(index);
	}

	public void addData(String value) {
		data.add(value);
	}

}
