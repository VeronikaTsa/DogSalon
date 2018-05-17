package com.tsarova.salon.specification;

import com.tsarova.salon.entity.Entity;
import com.tsarova.salon.exception.RepositoryException;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

public interface Specification<T extends Entity> {
    //другой методполучает конекшн, возвр стэйьмент
    //Statement toSqlQuery(Connection connection) throws RepositoryException;
    List<T> execute() throws RepositoryException;

    //boolean specified(T t);
    //T returnResult(Connection connection);
    //List<T> returnResult(Connection connection);//разделить на
}
