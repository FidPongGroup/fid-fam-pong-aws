package com.fidfam.test;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

public class TestUtils {
    public static final String EMAIL_CONSTRAINT_MESSAGE = "org.springframework.dao.DataIntegrityViolationException: " +
            "could not execute statement; SQL [n/a]; constraint [\"EMAIL_UNIQUE_INDEX_8 ON PUBLIC.PLAYER(EMAIL) " +
            "VALUES ('john.tacos@email.com', 1)\"; SQL statement:\n" +
            "insert into player (id, email, first_name, last_name, nickname) " +
            "values (null, ?, ?, ?, ?) [23505-193]]; " +
            "nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement";
    public static final String NICKNAME_CONSTRAINT_MESSAGE = "could not execute statement; SQL [n/a]; " +
            "constraint [\"NICKNAME_UNIQUE_INDEX_8 ON PUBLIC.PLAYER(NICKNAME) VALUES ('Tacos', 1)\"; SQL statement:\n" +
            "insert into player (id, email, first_name, last_name, nickname) values (null, ?, ?, ?, ?) [23505-193]]; " +
            "nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement";
    public static final String SOME_BAD_CONSTRAINT ="could not execute statement; SQL [n/a]; constraint [null]; " +
            "nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement";

    public static DataIntegrityViolationException createEmailException(){
        return new DataIntegrityViolationException(EMAIL_CONSTRAINT_MESSAGE);
    }
    public static DataIntegrityViolationException createNicknameException(){
        return new DataIntegrityViolationException(NICKNAME_CONSTRAINT_MESSAGE);
    }
    public static DataIntegrityViolationException createOtherException(){
        return new DataIntegrityViolationException(SOME_BAD_CONSTRAINT);
    }
}
