package com.javaoffers.brief.modelhelper.exception;

/**
 * @Description:
 * @Auther: create by cmj on 2021/12/9 11:33
 */
public class SqlParseException extends BaseException {
    public SqlParseException(String message) {
        super(message);
    }

    public SqlParseException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
        super.getCause().printStackTrace();
    }
}
