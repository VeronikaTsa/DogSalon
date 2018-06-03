package com.tsarova.salon.repository.impl;

import com.tsarova.salon.entity.Question;
import com.tsarova.salon.entity.QuestionAnswer;
import com.tsarova.salon.exception.ConnectionPoolException;
import com.tsarova.salon.exception.RepositoryException;
import com.tsarova.salon.pool.ConnectionPoolImpl;
import com.tsarova.salon.repository.Repository;
import com.tsarova.salon.repository.SQLQuery;
import com.tsarova.salon.specification.Specification;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionAnswerRepository implements Repository<QuestionAnswer>{
    private static Logger logger = LogManager.getLogger();

    @Override
    public boolean add(QuestionAnswer questionAnswer) throws RepositoryException {
        return false;
    }

    @Override
    public boolean remove(QuestionAnswer questionAnswer) throws RepositoryException {
        return false;
    }

    @Override
    public boolean update(QuestionAnswer questionAnswer) throws RepositoryException {
        return false;
    }

    @Override
    public QuestionAnswer find(QuestionAnswer questionAnswer) throws RepositoryException {
        return null;
    }

    @Override
    public List<QuestionAnswer> findAll() throws RepositoryException {
        final String SQL_FIND_ALL_QUESTIONS = SQLQuery.FIND_QUESTIONS_ANSWERS;
        List<QuestionAnswer> questionAnswerList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionPoolImpl.getInstance().getConnection();

            statement = connection.prepareStatement(SQL_FIND_ALL_QUESTIONS);
            resultSet = statement.executeQuery();
            System.out.println("МЫ ХОТЯ БЫ ЗДЕСЬ");
            while (!resultSet.isLast()){
                if (resultSet.next()) {
                    Long questionAnswerId = Long.valueOf(resultSet.getInt("question_answer_id"));
                    String questionContent = String.valueOf(resultSet.getString("question_content"));
                    Date questionCreateTime = new Date(resultSet.getTimestamp("question_create_time").getTime());
                    String questionUserLogin = resultSet.getString("question_user_login");
                    String answerContent = String.valueOf(resultSet.getString("answer_content"));
                    Date answerCreateTime = new Date(resultSet.getTimestamp("answer_create_time").getTime());
                    String answerUserLogin = resultSet.getString("answer_user_login");

                    System.out.println("questionContent: " + questionContent);
                    QuestionAnswer questionAnswer = new QuestionAnswer(questionAnswerId, questionUserLogin,questionContent,
                            questionCreateTime, answerUserLogin, answerContent, answerCreateTime);
                    questionAnswerList.add(questionAnswer);                        //??????????????????


                }
            }

        } catch (SQLException | NullPointerException | ConnectionPoolException e) {
            logger.catching(Level.ERROR, e);
            throw new RepositoryException(e);
        } finally {
            ConnectionPoolImpl.getInstance().closeConnection(connection);
        }
        return questionAnswerList;
    }

    @Override
    public List<QuestionAnswer> query(Specification<QuestionAnswer> specification) throws RepositoryException {
        return null;
    }
}
