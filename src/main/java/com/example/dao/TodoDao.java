package com.example.dao;

import com.example.domain.Todo;
import org.springframework.orm.ibatis.SqlMapClientOperations;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("deprecation")
public class TodoDao {

    private final SqlMapClientOperations operations;

    public TodoDao(SqlMapClientOperations operations) {
        this.operations = operations;
    }

    public void insert(Todo todo) {
        operations.insert("com.example.dao.TodoDao.insert", todo);
    }

    public Todo select(int id) {
        return (Todo) operations.queryForObject("com.example.dao.TodoDao.select", id);
    }

}
