package com.way.common.exception;

public class ScheduleJobExecption extends BaseException {

    private static final long serialVersionUID = 3076984003548588117L;

    private IError error;
    private String extMessage;

    public ScheduleJobExecption() {
        this.error = DefaultError.SYSTEM_INTERNAL_ERROR;
        this.extMessage = null;
    }

    public ScheduleJobExecption(String message) {
        super(message);
        this.error = DefaultError.SYSTEM_INTERNAL_ERROR;
        this.extMessage = null;
        this.extMessage = message;
    }

    public ScheduleJobExecption(String message, Throwable cause) {
        super(message, cause);
        this.error = DefaultError.SYSTEM_INTERNAL_ERROR;
        this.extMessage = null;
        this.extMessage = message;
    }

    public ScheduleJobExecption(Throwable cause) {
        super(cause);
        this.error = DefaultError.SYSTEM_INTERNAL_ERROR;
        this.extMessage = null;
    }

    public ScheduleJobExecption(IError error) {
        this.error = DefaultError.SYSTEM_INTERNAL_ERROR;
        this.extMessage = null;
        this.error = error;
    }

    public ScheduleJobExecption(String message, IError error) {
        super(message);
        this.error = DefaultError.SYSTEM_INTERNAL_ERROR;
        this.extMessage = message;
        this.error = error;
    }

    public ScheduleJobExecption(String message, Throwable cause, IError error) {
        super(message, cause);
        this.error = DefaultError.SYSTEM_INTERNAL_ERROR;
        this.extMessage = message;
        this.error = error;
    }

    public ScheduleJobExecption(Throwable cause, IError error) {
        super(cause);
        this.error = DefaultError.SYSTEM_INTERNAL_ERROR;
        this.extMessage = null;
        this.error = error;
    }

    public IError getError() {
        return this.error;
    }

    public String getExtMessage() {
        return this.extMessage;
    }

    public void setExtMessage(String extMessage) {
        this.extMessage = extMessage;
    }

    @Override
    public String toString() {
        return super.toString() + ",ErrorCode : " + this.error.getErrorCode() + ", ErrorMessage : " + this.error.getErrorMessage() + ", ExtMessage : " + this.extMessage;
    }
}
