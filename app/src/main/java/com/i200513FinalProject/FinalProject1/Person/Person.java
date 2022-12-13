package com.i200513FinalProject.FinalProject1.Person;

public class Person {
    String person_ID,person_Email,person_Username;
    String person_ImgUrl;
    String person_Password;
    String person_Cnic;

    public Person(String person_ID, String person_Email, String person_Username, String person_ImgUrl, String person_Password, String person_Cnic) {
        this.person_ID = person_ID;
        this.person_Email = person_Email;
        this.person_Username = person_Username;
        this.person_ImgUrl = person_ImgUrl;
        this.person_Password = person_Password;
        this.person_Cnic = person_Cnic;
    }

    public String getPerson_ID() {
        return person_ID;
    }

    public void setPerson_ID(String person_ID) {
        this.person_ID = person_ID;
    }

    public String getPerson_Email() {
        return person_Email;
    }

    public void setPerson_Email(String person_Email) {
        this.person_Email = person_Email;
    }

    public String getPerson_Username() {
        return person_Username;
    }

    public void setPerson_Username(String person_Username) {
        this.person_Username = person_Username;
    }

    public String getPerson_ImgUrl() {
        return person_ImgUrl;
    }

    public void setPerson_ImgUrl(String person_ImgUrl) {
        this.person_ImgUrl = person_ImgUrl;
    }

    public String getPerson_Password() {
        return person_Password;
    }

    public void setPerson_Password(String person_Password) {
        this.person_Password = person_Password;
    }

    public String getPerson_Cnic() {
        return person_Cnic;
    }

    public void setPerson_Cnic(String person_Cnic) {
        this.person_Cnic = person_Cnic;
    }

    public String printString()
    {
        return this.person_Username.concat(" ").concat(this.person_Password);
    }

}

