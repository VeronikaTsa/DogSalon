package com.tsarova.salon.specification.impl;

import com.tsarova.salon.entity.Question;
import com.tsarova.salon.exception.RepositoryException;
import com.tsarova.salon.specification.Specification;

import java.util.List;

public class QuestionSpecification implements Specification<Question> {
    @Override
    public List<Question> execute() throws RepositoryException {
        return null;
    }
}
