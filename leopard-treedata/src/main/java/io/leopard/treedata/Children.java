package io.leopard.treedata;

import java.util.ArrayList;
import java.util.List;

public class Children {

	private String name;

	private String colour;

	private List<Children> children;

	public Children(String name) {
		this(name, null);
	}

	public Children(String name, String colour) {
		this.name = name;
		this.colour = colour;
	}

	public Children add(String name) {
		return this.add(name, null);
	}

	public Children add(String name, String colour) {
		Children children = new Children(name, colour);
		if (this.children == null) {
			this.children = new ArrayList<Children>();
		}
		this.children.add(children);
		return children;
	}

	public List<Children> getChildren() {
		return children;
	}

	public void setChildren(List<Children> children) {
		this.children = children;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

}
