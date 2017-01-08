package com.rajendarreddyj.springbatchlistener.listener;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.core.ItemWriteListener;
import org.springframework.jdbc.core.JdbcTemplate;

import com.rajendarreddyj.springbatchlistener.model.User;

/**
 * @author rajendarreddy
 *
 */
public class WriteListener implements ItemWriteListener<User> {

    private String INSERT_QUERY = "insert into user_stats(firstName,lastName,city,id) values (?,?,?,?)";
    private DataSource dataSource;

    public DataSource getDataSource() {
        return this.dataSource;
    }

    public void setDataSource(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void afterWrite(final List<? extends User> items) {
        System.out.println("Feeding the stats table");
        int result = 0;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(this.getDataSource());

        for (User user : items) {
            Object[] params = { user.getFirstName(), user.getLastName(), user.getCity(), user.getId() };
            result += jdbcTemplate.update(this.INSERT_QUERY, params);

        }
        System.out.println("Number of rows inserted: " + result);

    }

    @Override
    public void beforeWrite(final List<? extends User> items) {
        System.out.println("Going to write following items: " + items.toString());

    }

    @Override
    public void onWriteError(final Exception arg0, final List<? extends User> arg1) {
        System.out.println("Error occurred when writing items!");

    }

}
