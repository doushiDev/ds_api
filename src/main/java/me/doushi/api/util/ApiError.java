package me.doushi.api.util;

/**
 *
 * Created by songlijun on 15/10/19.
 */
public class ApiError {

    private int error_code;
    private String error; //错误标题
    private String request; //请求路径
    private String error_detail; //错误描述


    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getError_detail() {
        return error_detail;
    }

    public void setError_detail(String error_detail) {
        this.error_detail = error_detail;
    }

    public ApiError() {
        super();
    }

    public ApiError(int error_code, String error, String request, String error_detail) {
        this.error_code = error_code;
        this.error = error;
        this.request = request;
        this.error_detail = error_detail;
    }
}
