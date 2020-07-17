package com.pasksoftvalley.cargobookingapp.Model;

public class OrderDetailsModel {
    public String id;
    public String FirstName;
    public String LastName;
    public String Gender;
    public String PhoneNo;
    public String CNIC;
    public String BloodGroup;
    public String TestReport;
    public String AnyDiesese;
    public String Address;
    public String Longitute;
    public String Latitude;

    public OrderDetailsModel(String id, String firstName, String lastName, String gender, String PhoneNo, String CNIC,
                             String bloodGroup, String testReport, String anyDiesese, String
                                address, String longitute, String latitude) {
        this.id = id;
        FirstName = firstName;
        LastName = lastName;
        Gender = gender;
        this.PhoneNo =PhoneNo;
        this.CNIC = CNIC;
        BloodGroup = bloodGroup;
        TestReport = testReport;
        AnyDiesese = anyDiesese;
        Address = address;
        Longitute = longitute;
        Latitude = latitude;
    }
    public String getid() {
        return id;
    }
    public void setid(String id) {
        this.id = id;
    }
    public String getFirstName() {
        return FirstName;
    }
    public void setFirstName(String firstName) { FirstName = firstName; }
    public String getLastName() {
        return LastName;
    }
    public void setLastName(String lastName) {
        LastName = lastName;
    }
    public String getGender() {
        return Gender;
    }
    public void setGender(String gender) {
        Gender = gender;
    }
    public String getPhoneNo()
    {
        return PhoneNo;
    }
    public void setPhoneNo(String PhoneNo) {
        this.PhoneNo = PhoneNo;
    }
    public String getCNIC() {
        return CNIC;
    }
    public void setCNIC(String CNIC) {
        this.CNIC = CNIC;
    }
    public String getBloodGroup() {
        return BloodGroup;
    }
    public void setBloodGroup(String bloodGroup) {
        BloodGroup = bloodGroup;
    }
    public String getTestReport() {
        return TestReport;
    }
    public void setTestReport(String testReport) {
        TestReport = testReport;
    }
    public String getAnyDiesese() {
        return AnyDiesese;
    }
    public void setAnyDiesese(String anyDiesese) {
        AnyDiesese = anyDiesese;
    }
    public String getAddress() {
        return Address;
    }
    public void setAddress(String address) {
        Address = address;
    }
    public String getLongitute() {
        return Longitute;
    }
    public void setLongitute(String longitute) {
        Longitute = longitute;
    }
    public String getLatitude() {
        return Latitude;
    }
    public void setLatitude(String latitude) {
        Latitude = latitude;
    }


}



