package com.example.urlconnection;


public class ApiModel
{
    String apikey;
    int  status;
    int userid;

    public ApiModel(String apikey, int status, int userid) {
        this.apikey = apikey;
        this.status = status;
        this.userid=userid;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }


    @Override
    public String toString() {
        return "ApiModel{" +
                "apikey='" + apikey + '\'' +
                ", status=" + status +", status=" + status +
                '}';
    }
}
