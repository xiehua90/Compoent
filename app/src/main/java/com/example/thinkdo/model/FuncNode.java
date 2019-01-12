package com.example.thinkdo.model;

import java.util.ArrayList;

public class FuncNode {
    private int id;
    private int parentId;
    private String name;

    private FuncNode parent;

    private ArrayList<FuncNode> children;

    public FuncNode() {
    }

    public FuncNode(int id, int parentId, String name) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addChild(FuncNode node) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(node);
    }

    public ArrayList<FuncNode> getChildren() {
        return children;
    }

    public FuncNode getParent() {
        return parent;
    }

    public void setParent(FuncNode parent) {
        this.parent = parent;
    }
}
