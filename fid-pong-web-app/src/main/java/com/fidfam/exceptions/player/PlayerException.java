package com.fidfam.exceptions.player;

public abstract class PlayerException extends IllegalStateException{

    public PlayerException(String msg){
        super(msg);
    }
    public PlayerException(String msg, Throwable cause){
        super(msg, cause);
    }
}
