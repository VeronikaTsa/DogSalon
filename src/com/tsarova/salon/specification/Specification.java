package com.tsarova.salon.specification;

import com.tsarova.salon.entity.Entity;
import com.tsarova.salon.exception.RepositoryException;

import java.util.List;

/**
 * @author Veronika Tsarva
 * @param <T>
 */
public interface Specification<T extends Entity> {
    List<T> execute() throws RepositoryException;
}
