package com.example.bldeacet;

public class UserHelperClass2 {

    String fname, femail, fpassword, fphoneNo, fbranch;

    public UserHelperClass2() {
    }

    public UserHelperClass2(String fname, String femail, String fpassword, String fphoneNo, String fbranch) {
        this.fname = fname;
        this.femail = femail;
        this.fpassword = fpassword;
        this.fphoneNo = fphoneNo;
        this.fbranch = fbranch;

    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFemail() {
        return femail;
    }

    public void setFemail(String femail) {
        this.femail = femail;
    }

    public String getFpassword() {
        return fpassword;
    }

    public void setFpassword(String fpassword) {
        this.fpassword = fpassword;
    }

    public String getFphoneNo() {
        return fphoneNo;
    }

    public void setFphoneNo(String fphoneNo) {
        this.fphoneNo = fphoneNo;
    }

    public String getFbranch() {
        return fbranch;
    }

    public void setFbranch(String fbranch) {
        this.fbranch = fbranch;
    }
}
