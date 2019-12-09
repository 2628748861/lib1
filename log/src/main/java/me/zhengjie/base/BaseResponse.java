package me.zhengjie.base;

public class BaseResponse
{
    private int code;
    private String message;
    private Object data;

    private BaseResponse(Builder builder) {
        this.code = builder.code;
        this.message = builder.message;
        this.data = builder.data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static Builder success(){
        return new Builder().code(1);
    }
    public static Builder failed(){
        return new Builder().code(0);
    }



    public static class Builder{
        private int code;
        private String message;
        private Object data;

        public Builder code(int code) {
            this.code = code;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder data(Object data) {
            this.data = data;
            return this;
        }

        public BaseResponse build(){
            return new BaseResponse(this);
        }

    }
}

