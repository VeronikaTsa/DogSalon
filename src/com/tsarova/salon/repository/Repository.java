package com.tsarova.salon.repository;

import com.tsarova.salon.entity.Entity;
import com.tsarova.salon.exception.RepositoryException;
import com.tsarova.salon.specification.Specification;

import java.util.List;

public interface Repository<T extends Entity> {
    boolean add(T t) throws RepositoryException;
    boolean remove(T t) throws RepositoryException;
    boolean update(T t) throws RepositoryException;
    T find(T t) throws RepositoryException;//можно специфик
    List<T> findAll() throws RepositoryException;



    List<T> query(Specification<T> specification) throws RepositoryException;
    //List<T> queryR(Specification<T> specification) throws RepositoryException;


}
