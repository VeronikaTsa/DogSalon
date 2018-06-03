package com.tsarova.salon.receiver;

import com.tsarova.salon.entity.Entity;
import com.tsarova.salon.exception.ReceiverException;
import com.tsarova.salon.exception.RepositoryException;
import com.tsarova.salon.repository.Repository;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReceiverValidator {
    private static Logger logger = LogManager.getLogger();

    public static boolean add(Repository repository, Entity entity) throws ReceiverException {
        try {
            return repository.add(entity);
        } catch (RepositoryException e) {
            logger.catching(Level.ERROR, e);
            throw new ReceiverException(e);
        }
    }

    public static boolean remove(Repository repository, Entity entity) throws ReceiverException {
        try {
            return repository.remove(entity);
        } catch (RepositoryException e) {
            logger.catching(Level.ERROR, e);
            throw new ReceiverException(e);
        }
    }
}