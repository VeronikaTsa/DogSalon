package com.tsarova.salon.repository;

/**
 * @author Veronika Tsarova
 */
public class SQLQuery {

    private SQLQuery() {
    }

    public static final String DEFINE_USER_ID = "select last_insert_id() as id;";

    public static final String DEFINE_USER = "SELECT user.user_id, user.login, user_role.user_role " +
            "FROM user left join user_role on user.user_role_id=user_role.user_role_id " +
            "    where user.email = ? AND user.password = ?";

    public static final String DEFINE_USER_CONTENT = "SELECT first_name, " +
            "last_name, telephone, birthday, sex " +
            "FROM user_content where user_id = ?";

    public static final String DEFINE_ROLE = "Select user_role_id, user_role from user_role where user_role_id=?";

    public static final String IF_EXIST_USER = "Select user_id from user where email=?";

    public static final String IF_EXIST_LOGIN = "Select user_id from user where login=?";

    public static final String CREATE_USER = "Insert into user(email, password, login, user_role_id) " +
            "values ( ?, ?, ?, (select user_role_id from user_role where user_role = ?));";

    public static final String ADD_FEEDBACK = "Insert into feedback(user_id, content) " +
            "values ( ?, ?);";

    public static final String ADD_SERVICE = "Insert into service(service_name, content, price) " +
            "values ( ?, ?, ?);";

    public static final String REPLACE_ANSWER = "Replace into answer(question_answer_id, user_id, content) " +
            "values (?, ?, ?);";

    public static final String ADD_QUESTION = "Insert into question(user_id, content) " +
            "values ( ?, ?);";

    public static final String DELETE_FEEDBACK = "delete from feedback " +
            "where feedback_id = ?;";

    public static final String DELETE_ANSWER = "delete from answer " +
            "where question_answer_id = ?;";

    public static final String DELETE_SERVICE = "delete from service " +
            "where service_id = ?;";

    public static final String DELETE_QUESTION = "delete from question " +
            "where question_id = ?;";

    public static final String FIND_FEEDBACKS = "select feedback.content, " +
            "feedback.create_time, feedback.feedback_id, user.login " +
            "from feedback left join user on feedback.user_id = user.user_id order by feedback.create_time desc; ";

    public static final String FIND_SERVICES = "select content, service_name, service_id, picture, price " +
            "from service; ";

    public static final String FIND_NOT_ANSWERED_QUESTIONS = "SELECT user.login as author_login, question.question_id, " +
            "question.content, question.create_time " +
            "FROM (question INNER JOIN user ON question.user_id = user.user_id) " +
            "LEFT JOIN answer " +
            "on question.question_id = answer.question_answer_id " +
            "    WHERE answer.question_answer_id IS NULL;";

    public static final String INFO_BY_LOGIN = "SELECT user.email, user.login, user.user_id, " +
            "user_content.first_name, user_content.last_name, " +
            "user_content.telephone, user_content.birthday, user_content.sex " +
            "FROM user left join user_content on user.user_id=user_content.user_id " +
            "where user.login = ?;";

    public static final String FIND_QUESTIONS_ANSWERS = "SELECT " +
            "d.question_answer_id, " +
            "b.content as question_content, " +
            "b.create_time as question_create_time, " +
            "b.question_user_login, " +
            "d.content as answer_content, " +
            "d.create_time as answer_create_time, " +
            "d.answer_user_login " +
            "FROM " +
            "(select " +
            "question.question_id, question.content, question.create_time, user.login as question_user_login " +
            "from question join user on question.user_id=user.user_id) " +
            "as b " +
            "right join " +
            "(select " +
            "answer.question_answer_id, answer.content, answer.create_time, user.login as answer_user_login " +
            "from answer join user on answer.user_id=user.user_id) " +
            "as d " +
            "on b.question_id = d.question_answer_id order by d.create_time desc;";

    public static final String UPDATE_SERVICE = "UPDATE service " +
            "    SET service_name = ?, content = ?, picture = ?, price = ? " +
            "    WHERE service_id = ?;";

    public static final String UPDATE_USER = "UPDATE user " +
            "    SET login = ?, email = ?, password = ?" +
            "    WHERE user_id = ?;";

    public static final String REPLACE_USER_CONTENT = "replace into user_content(first_name, " +
            "last_name, telephone, birthday, sex,user_id) " +
            "values( ?, ?, ?, ?, ?, ?);";

    public static final String DELETE_USER_CONTENT = "delete from user_content where user_id = ?;";
}