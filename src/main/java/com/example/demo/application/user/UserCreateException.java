package com.example.demo.application.user;

//JDBCからのエラー発生時にアプリケーション層が受け取るクラス
public class UserCreateException extends RuntimeException {

    public UserCreateException(String msg, Throwable cause) {super(msg, cause);}

    public UserCreateException(String msg) {super(msg);}

}
