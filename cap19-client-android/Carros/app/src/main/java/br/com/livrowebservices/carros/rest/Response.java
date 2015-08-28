package br.com.livrowebservices.carros.rest;

public class Response {
    private String status;
    private String msg;

    public Response() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Response{" +
                "status='" + status + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }

    public boolean isOk() {
        return "OK".equalsIgnoreCase(status);
    }
}
