package com.example.demo.tasklist.model;

import com.example.demo.tasklist.InstanceBuilder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author arim
 */
@JsonSerialize
public class TaskList {
    protected String id;
    protected List<ListItem> items = new ArrayList<>();

    public String getId() {
        return id;
    }

    public List<ListItem> getItems() {
        return items;
    }

    public boolean hasItems() {
        return items != null && !items.isEmpty();
    }

    public static class Builder implements InstanceBuilder<TaskList> {
        TaskList list = new TaskList();

        public Builder id(String id) {
            list.id = id;
            return this;
        }

        public Builder items(List<ListItem> items) {
            list.items = new ArrayList<>(items);
            Collections.sort(list.items);
            return this;
        }

        @Override
        public TaskList build() {
            return list;
        }
    }
}
