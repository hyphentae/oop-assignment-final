package com.hyphentae.lms.shared;

import java.sql.SQLException;
import java.util.List;

public interface CrudRepository<T, ID> {
    T findById(ID id) throws SQLException;
    List<T> findAll() throws SQLException;
    void save(T entity) throws SQLException;
    void update(T entity) throws SQLException;
    void deleteById(ID id) throws SQLException;
}
