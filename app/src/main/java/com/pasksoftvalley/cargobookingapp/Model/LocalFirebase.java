package com.pasksoftvalley.cargobookingapp.Model;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.pasksoftvalley.cargobookingapp.Presenter.MainActivity;

import java.util.ArrayList;
import java.util.Map;

public class LocalFirebase
{
    public static void signUpLawyer(Employee account)
    {

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference();

        String uName=BackWorks.beforeAt(account.getEmail());
        myRef.child("Users").child(uName).child("firstName").setValue(account.getFirstName());
        myRef.child("Users").child(uName).child("lastName").setValue(account.getLastName());
        myRef.child("Users").child(uName).child("status").setValue(account.getStatus());
        myRef.child("Users").child(uName).child("email").setValue(account.getEmail());
        myRef.child("Users").child(uName).child("dateOfBirth").setValue(account.getDob());
        myRef.child("Users").child(uName).child("cnic").setValue(account.getCnic());
        myRef.child("Users").child(uName).child("phone").setValue(account.getPhoneNo());
        myRef.child("Users").child(uName).child("country").setValue(account.getCountry());
        myRef.child("Users").child(uName).child("address").setValue(account.getAddress());
        myRef.child("Users").child(uName).child("age").setValue(account.getAge());


        myRef.child("Users").child(uName).child("education").setValue(account.getEducation());
        myRef.child("Users").child(uName).child("specialization").setValue(account.getSpecialization());
        myRef.child("Users").child(uName).child("experience").setValue(account.getExperience());
        myRef.child("Users").child(uName).child("availability").setValue(account.getAvailability());



        Map<String,String> dateTime= ServerValue.TIMESTAMP;
        myRef.child("Users").child(uName).child("updateDate").setValue(dateTime);


    }

    public static void signUpLawExpert(Admin account)
    {

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference();

        String uName=BackWorks.beforeAt(account.getEmail());




        myRef.child("Users").child(uName).child("firstName").setValue(account.getFirstName());
        myRef.child("Users").child(uName).child("lastName").setValue(account.getLastName());
        myRef.child("Users").child(uName).child("status").setValue(account.getStatus());
        myRef.child("Users").child(uName).child("email").setValue(account.getEmail());
        myRef.child("Users").child(uName).child("dateOfBirth").setValue(account.getDob());
        myRef.child("Users").child(uName).child("cnic").setValue(account.getCnic());
        myRef.child("Users").child(uName).child("phone").setValue(account.getPhoneNo());
        myRef.child("Users").child(uName).child("country").setValue(account.getCountry());
        myRef.child("Users").child(uName).child("address").setValue(account.getAddress());
        myRef.child("Users").child(uName).child("age").setValue(account.getAge());


        myRef.child("Users").child(uName).child("education").setValue(account.getEducation());
        myRef.child("Users").child(uName).child("specialization").setValue(account.getSpecialization());
        myRef.child("Users").child(uName).child("firmName").setValue(account.getFirmName());
        myRef.child("Users").child(uName).child("experience").setValue(account.getExperience());

        myRef.child("Users").child(uName).child("availability").setValue(account.getAvailability());
        Map<String,String> dateTime= ServerValue.TIMESTAMP;
        myRef.child("Users").child(uName).child("updateDate").setValue(dateTime);


    }

    public static void signUpClient(AppUser account)
    {

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference();

        String uName=BackWorks.beforeAt(account.getEmail());




        myRef.child("Users").child(uName).child("firstName").setValue(account.getFirstName());
        myRef.child("Users").child(uName).child("lastName").setValue(account.getLastName());
        myRef.child("Users").child(uName).child("status").setValue(account.getStatus());
        myRef.child("Users").child(uName).child("email").setValue(account.getEmail());
        myRef.child("Users").child(uName).child("dateOfBirth").setValue(account.getDob());
        myRef.child("Users").child(uName).child("cnic").setValue(account.getCnic());
        myRef.child("Users").child(uName).child("phone").setValue(account.getPhoneNo());
        myRef.child("Users").child(uName).child("country").setValue(account.getCountry());
        myRef.child("Users").child(uName).child("address").setValue(account.getAddress());
        myRef.child("Users").child(uName).child("age").setValue(account.getAge());




        Map<String,String> dateTime= ServerValue.TIMESTAMP;
        myRef.child("Users").child(uName).child("updateDate").setValue(dateTime);


    }

    public static void saveChangesToClient(AppUser account)
    {

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference();

        String uName=BackWorks.beforeAt(account.getEmail());


        // myRef.child("Users").child(uName).child("firstName").setValue(account.getFirstName());
        //  myRef.child("Users").child(uName).child("lastName").setValue(account.getLastName());
        myRef.child("Users").child(uName).child("dateOfBirth").setValue(account.getDob());
        myRef.child("Users").child(uName).child("phone").setValue(account.getPhoneNo());
        myRef.child("Users").child(uName).child("address").setValue(account.getAddress());


        Map<String,String> dateTime= ServerValue.TIMESTAMP;
        myRef.child("Users").child(uName).child("updateDate").setValue(dateTime);


    }


    public static void saveChangesToLawyer(Employee account)
    {

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference();

        String uName=BackWorks.beforeAt(account.getEmail());




        // myRef.child("Users").child(uName).child("firstName").setValue(account.getFirstName());
        //  myRef.child("Users").child(uName).child("lastName").setValue(account.getLastName());
        myRef.child("Users").child(uName).child("dateOfBirth").setValue(account.getDob());
        myRef.child("Users").child(uName).child("phone").setValue(account.getPhoneNo());
        myRef.child("Users").child(uName).child("address").setValue(account.getAddress());


        myRef.child("Users").child(uName).child("education").setValue(account.getEducation());
        myRef.child("Users").child(uName).child("specialization").setValue(account.getSpecialization());
        myRef.child("Users").child(uName).child("experience").setValue(account.getExperience());

        Map<String,String> dateTime= ServerValue.TIMESTAMP;
        myRef.child("Users").child(uName).child("updateDate").setValue(dateTime);


    }

    public static void saveChangesToLawExpert(Admin account)
    {

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference();
        String uName=BackWorks.beforeAt(account.getEmail());
        myRef.child("Users").child(uName).child("dateOfBirth").setValue(account.getDob());
        myRef.child("Users").child(uName).child("phone").setValue(account.getPhoneNo());
        myRef.child("Users").child(uName).child("address").setValue(account.getAddress());
        myRef.child("Users").child(uName).child("education").setValue(account.getEducation());
        myRef.child("Users").child(uName).child("specialization").setValue(account.getSpecialization());
        myRef.child("Users").child(uName).child("experience").setValue(account.getExperience());
        myRef.child("Users").child(uName).child("firmName").setValue(account.getFirmName());
        Map<String,String> dateTime= ServerValue.TIMESTAMP;
        myRef.child("Users").child(uName).child("updateDate").setValue(dateTime);


    }






    public static void getUserStatus(String email)
    {

        FirebaseDatabase database=FirebaseDatabase.getInstance();


        String uName=BackWorks.beforeAt(email);
        DatabaseReference myRef=database.getReference("Users").child(uName);



        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.child("status").getValue(String.class);
                MainActivity.userStatus=value;

            }

            @Override
            public void onCancelled(DatabaseError error) {
                MainActivity.userStatus="error";
            }
        });

    }
}