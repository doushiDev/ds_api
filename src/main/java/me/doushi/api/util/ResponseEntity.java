package me.doushi.api.util;

public class ResponseEntity<T>
{
  private String message;
  private int StatusCode;
  private T content;
  private String request; //请求路径


  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getRequest() {
    return request;
  }

  public void setRequest(String request) {
    this.request = request;
  }

  public int getStatusCode() {
    return StatusCode;
  }

  public void setStatusCode(int statusCode) {
    StatusCode = statusCode;
  }

  public Object getContent() {
    return this.content;
  }

  public void setContent(T content) {
    this.content = content;
  }

  public ResponseEntity() {
  }

  public ResponseEntity(String message, int statusCode, T content, String request) {
    this.message = message;
    StatusCode = statusCode;
    this.content = content;
    this.request = request;
  }

  @Override
  public String toString() {
    return "ResponseEntity{" +
            "message='" + message + '\'' +
            ", StatusCode=" + StatusCode +
            ", content=" + content +
            ", request='" + request + '\'' +
            '}';
  }

}