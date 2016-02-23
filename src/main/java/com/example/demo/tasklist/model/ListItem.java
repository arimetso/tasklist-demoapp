package com.example.demo.tasklist.model;

import com.example.demo.tasklist.InstanceBuilder;

/**
 * @author arim
 */
public class ListItem implements Comparable<ListItem> {
    protected String id;
    protected Integer index;
    protected String text;

    public String getId() {
        return id;
    }

    public Integer getIndex() {
        return index;
    }

    public String getText() {
        return text;
    }

    @Override
    public int compareTo(ListItem other) {
        try {
            return this.index.compareTo(other.index);
        } catch (Exception e) {
            return 0; // treat as same if index is missing
        }
    }

    public static class Builder implements InstanceBuilder<ListItem> {
        ListItem item = new ListItem();

        public Builder id(String id) {
            item.id = id;
            return this;
        }

        public Builder index(int index) {
            item.index = index;
            return this;
        }

        public Builder text(String text) {
            item.text = text;
            return this;
        }

        @Override
        public ListItem build() {
            return item;
        }
    }
}
