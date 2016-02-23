package com.example.demo.tasklist.web;

import com.example.demo.tasklist.data.TaskListDao;
import com.example.demo.tasklist.model.TaskList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author arim
 */
@RestController
@RequestMapping("/rest/list")
public class TaskListController {
    @Autowired
    private TaskListDao dao;

    @RequestMapping(value = "/{listId:[a-z0-9]+}", method = RequestMethod.GET)
    public TaskList get(@PathVariable("listId") String listId) {
        return dao.findById(listId);
    }

    @RequestMapping(value = "/{listId:[a-z0-9]+}", method = RequestMethod.POST)
    public TaskList save(@PathVariable("listId") String listId, @RequestBody TaskList list) {
        return dao.save(listId, list);
    }
}
