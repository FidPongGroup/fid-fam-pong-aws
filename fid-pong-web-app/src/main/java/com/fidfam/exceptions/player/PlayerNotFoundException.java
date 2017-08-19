package com.fidfam.exceptions.player;

public class PlayerNotFoundException extends PlayerException{
    public PlayerNotFoundException(String msg, Throwable cause){
        super(msg, cause);
    }
    public PlayerNotFoundException(String msg){
        super(msg);
    }
}
