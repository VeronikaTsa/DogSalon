package com.tsarova.salon.repository;

public class SQLQuery {
    private SQLQuery() {
    }
    public static final String INSERT_USER = "Insert into user (login, password, role, ent_id) values(?, ?, ?, ?)";
    public static final String CHECK_EMAIL = "Select * from user where email=?";
    public static final String DELETE_USER = "Delete from user where email=? and password=?";
    public static final String FIND_ALL_FEEDBACKS = "select feedback.content, feedback.create_time, feedback.feedback_id, user.login \n" +
            "\tfrom feedback left join user on feedback.user_id = user.user_id; ";
    public static final String FIND_ALL_QUESTIONS_WITH_ANSWERS = "SELECT question.content, question.create_time, " +
            "answer.content, answer.create_time, answer.last_update\n" +
            "\tFROM question right join answer \n" +
            "\t\ton question.question_id = answer.question_answer_id;";

    public static final String DEFINE_USER = "SELECT user.user_id, user.login, user_role.user_role \n" +
            "FROM user left join user_role on user.user_role_id=user_role.user_role_id \n" +
            "    where user.email = ? AND user.password = ?";

    public static final String DEFINE_USER_CONTENT = "SELECT first_name, \n" +
            "last_name, telephone, birthday, picture, sex\n" +
            "FROM user_content where user_id = ?";

    public static final String DEFINE_ROLE = "Select user_role_id, user_role from user_role where user_role_id=?";
    public static final String IF_EXIST_USER = "Select user_id from user where email=?";
    public static final String IF_EXIST_LOGIN = "Select user_id from user where login=?";

    public static final String CREATE_USER = "Insert into user(email, password, login, user_role_id) " +
            "values ( ?, ?, ?, (select user_role_id from user_role where user_role = ?));";

    public static final String ADD_FEEDBACK = "Insert into feedback(user_id, content) " +
            "values ( ?, ?);";//если останется юзер айди в таблице и запрос
                            // на поиск будет через соединение таблиц, надо добавить сюда и в класс отзыва юзер айди

    public static final String ADD_SERVICE = "Insert into service(service_name, content, price) " +
            "values ( ?, ?, ?);";

    public static final String ADD_ANSWER = "Insert into answer(question_answer_id, user_id, content) " +
            "values (?, ?, ?);";//если останется юзер айди в таблице и запрос
    // на поиск будет через соединение таблиц, надо добавить сюда и в класс отзыва юзер айди

    public static final String ADD_QUESTION = "Insert into question(user_id, content) " +
            "values ( ?, ?);";//если останется юзер айди в таблице и запрос
    // на поиск будет через соединение таблиц, надо добавить сюда и в класс отзыва юзер айди
    public static final String DELETE_FEEDBACK = "delete from feedback " +
            "where feedback_id = ?;";
    public static final String DELETE_SERVICE = "delete from service " +
            "where service_id = ?;";

    public static final String DELETE_QUESTION = "delete from question " +
            "where question_id = ?;";
    public static final String FIND_FEEDBACKS = "select feedback.content, feedback.create_time, feedback.feedback_id, user.login \n" +
            "\tfrom feedback left join user on feedback.user_id = user.user_id order by feedback.create_time desc; ";

    public static final String FIND_SERVICES = "select content, service_name, service_id, picture, price \n" +
            "\tfrom service; ";

    public static final String FIND_QUESTIONS = "SELECT question.content, question.create_time, question_id, user_login" +
            "\tFROM question right join answer \n" +
            "\t\ton question.question_id = answer.question_answer_id; ";

    public static final String FIND_NOT_ANSWERED_QUESTIONS = "SELECT user.login as author_login, question.question_id, question.content, question.create_time\n" +
            "\tFROM (question INNER JOIN user ON question.user_id = user.user_id) \n" +
            "\tLEFT JOIN answer\n" +
            "\t\ton question.question_id = answer.question_answer_id \n" +
            "    WHERE answer.question_answer_id IS NULL;";


    public static final String INFO_BY_LOGIN = "SELECT user.email, user.login, user.user_id,\n" +
            "\tuser_content.first_name, user_content.last_name, user_content.telephone, user_content.birthday, user_content.picture, user_content.sex\n" +
            "\t\tFROM user left join user_content on user.user_id=user_content.user_id \n" +
            "\t\twhere user.login = ?;";

    public static final String FIND_QUESTIONS_ANSWERS = "SELECT \n" +
            "d.question_answer_id,\n" +
            "b.content as question_content,\n" +
            "b.create_time as question_create_time, \n" +
            "b.question_user_login,\n" +
            "d.content as answer_content,\n" +
            "d.create_time as answer_create_time, \n" +
            "d.last_update as answer_last_update, \n" +
            "d.answer_user_login\n" +
            "\tFROM \n" +
            "\t\t(select \n" +
            "\t\t\tquestion.question_id, question.content, question.create_time, user.login as question_user_login \n" +
            "\t\t\t\tfrom question join user on question.user_id=user.user_id) \n" +
            "\t\tas b \n" +
            "\tright join \n" +
            "\t\t(select \n" +
            "\t\t\tanswer.last_update, answer.question_answer_id, answer.content, answer.create_time, user.login as answer_user_login  \n" +
            "\t\t\t\tfrom answer join user on answer.user_id=user.user_id) \n" +
            "\t\tas d\n" +
            "\ton b.question_id = d.question_answer_id order by d.create_time desc;";

    public static final String UPDATE_SERVICE = "UPDATE service \n" +
            "    SET service_name = ?, content = ?, picture = ?, price = ?\n" +
            "    WHERE service_id = ?;";

    public static final String UPDATE_USER = "UPDATE user \n" +
            "    SET login = ?, email = ?, password = ?" +
            "    WHERE user_id = ?;";

    public static final String UPDATE_USER_INFO = "UPDATE user_content \n" +
            "    SET first_name = ?, last_name = ?, telephone = ?, birthday = ?, sex = ?" +
            "    WHERE user_id = ?;";
}