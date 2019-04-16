package cz.intercity.smellsphishy.common.exception;

public class HeaderAnalysisException extends Exception {
    public HeaderAnalysisException (String errorMessage){
        super(errorMessage);
    }

    public HeaderAnalysisException(String errorMessage, Throwable throwable){
        super(errorMessage, throwable);
    }
}