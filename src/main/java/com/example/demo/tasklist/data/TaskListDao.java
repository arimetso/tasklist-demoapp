package com.example.demo.tasklist.data;

import com.example.demo.tasklist.model.ListItem;
import com.example.demo.tasklist.model.TaskList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO implementation for task lists.
 *
 * <p>The implementation handles both task_list and task_list_item tables.
 * It is somewhat inefficient with the existence checks and all,
 * but we're gonna allow that for this demo's purposes.</p>
 *
 * @author arim
 */
@Repository
public class TaskListDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public TaskList findById(String id) {
        TaskList.Builder builder = new TaskList.Builder().id(id);
        if (exists(id)) {
            builder.items(jdbcTemplate.query(ListItemRowMapper.SQL, new ListItemRowMapper(), id));
        }
        return builder.build();
    }

    public TaskList save(String listId, TaskList list) {
        if (exists(listId)) {
            jdbcTemplate.update("update task_list set last_updated = now() where id = ?", listId);
        } else {
            jdbcTemplate.update("insert into task_list (id, created, last_updated) values (?, now(), now())", listId);
        }

        jdbcTemplate.update("delete task_list_item where list_id = ?", listId);
        if (list.hasItems()) {
            jdbcTemplate.batchUpdate(ListItemSetter.SQL, list.getItems(), 100, new ListItemSetter(listId));
        }
        return findById(listId);
    }

    private boolean exists(String listId) {
        String sql = "select count(id) from task_list where id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, i) -> rs.getInt(1), listId) > 0;
    }

    public static class ListItemRowMapper implements RowMapper<ListItem> {
        public static final String SQL = "select text from task_list_item where list_id = ? order by item_index asc";

        @Override
        public ListItem mapRow(ResultSet rs, int i) throws SQLException {
            return new ListItem.Builder().index(i).text(rs.getString("text")).build();
        }
    }

    public static class ListItemSetter implements ParameterizedPreparedStatementSetter<ListItem> {
        public static final String SQL = "insert into task_list_item (list_id, item_index, text) values (?, ?, ?)";

        private final String listId;
        private int itemIndex;

        public ListItemSetter(String listId) {
            this.listId = listId;
            this.itemIndex = 0;
        }

        @Override
        public void setValues(PreparedStatement ps, ListItem listItem) throws SQLException {
            ps.setString(1, listId);
            ps.setInt(2, itemIndex++);
            ps.setString(3, listItem.getText());
        }
    }
}
