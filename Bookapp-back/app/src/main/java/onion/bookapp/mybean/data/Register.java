package onion.bookapp.mybean.data;

public class Register {
    String userid="",psw="",city="",school="",major="",errormessage="";

    public Register(String userid, String psw, String city, String school, String major, String errormessage) {
        this.userid = userid;
        this.psw = psw;
        this.city = city;
        this.school = school;
        this.major = major;
        this.errormessage = errormessage;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getErrormessage() {
        return errormessage;
    }

    public void setErrormessage(String errormessage) {
        this.errormessage = errormessage;
    }
}
