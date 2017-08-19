package com.fidfam.exceptions.player;

import com.fidfam.exceptions.FailureReason;
import lombok.Getter;
import org.springframework.dao.DataIntegrityViolationException;

public class SavePlayerException extends PlayerException{

    public static final String NICKNAME_UNIQUE_INDEX_NAME = "NICKNAME_UNIQUE";

    public static final String EMAIL_UNIQUE_INDEX_NAME = "EMAIL_UNIQUE";


        @Getter
        private FailureReason reason
                ;
    public SavePlayerException(DataIntegrityViolationException cause){
        super("Error Saving Player: "+ cause.getLocalizedMessage(), cause);
        if(cause.getMessage().contains(NICKNAME_UNIQUE_INDEX_NAME)){
            this.reason = FailureReason.NICKNAME;
        }else if (cause.getMessage().contains(EMAIL_UNIQUE_INDEX_NAME)){
            this.reason = FailureReason.EMAIL;
        }else{
            this.reason = FailureReason.DATA_VIOLATION;
        }
    }

    public SavePlayerException(String msg, Throwable cause){
        super(msg, cause);
        this.reason = FailureReason.DATA_VIOLATION;
    }

}
