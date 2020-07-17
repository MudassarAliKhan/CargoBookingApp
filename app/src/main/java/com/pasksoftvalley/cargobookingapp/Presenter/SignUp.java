package com.pasksoftvalley.cargobookingapp.Presenter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.rpc.context.AttributeContext;
import com.pasksoftvalley.cargobookingapp.Model.BackWorks;
import com.pasksoftvalley.cargobookingapp.Model.Employee;
import com.pasksoftvalley.cargobookingapp.Model.LocalFirebase;
import com.pasksoftvalley.cargobookingapp.Model.Validator;
import com.pasksoftvalley.cargobookingapp.R;

public class SignUp extends AppCompatActivity {
    SharedPreferences userInfo;
    Button googleSignIn;
    private static final int RC_SIGN_IN=1;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Employee");

        // CREATE PROGRESS BAR
        ViewGroup layout = (ViewGroup) findViewById(android.R.id.content).getRootView();
        progressBar = new ProgressBar(SignUp.this,null,android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams params = new
                RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);

        RelativeLayout rl = new RelativeLayout(SignUp.this);

        rl.setGravity(Gravity.CENTER);
        rl.addView(progressBar);

        layout.addView(rl,params);
        progressBar.setVisibility(View.GONE);

        //google work starts

        userInfo=getSharedPreferences("user_info", Context.MODE_PRIVATE);
        final SharedPreferences.Editor ed =userInfo.edit();

        googleSignIn=(Button) findViewById(R.id.signUpBtn);

        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText name=(EditText)findViewById(R.id.full_name);
                EditText cnic=(EditText)findViewById(R.id.cnic);
                EditText ph=(EditText)findViewById(R.id.phoneNo);
                EditText country=(EditText)findViewById(R.id.country);

                if(!Validator.isSignUpValid(name.getText().toString(),cnic.getText().toString(),ph.getText().toString(),country.getText().toString()))
                {
                    Toast.makeText(SignUp.this, "Please enter valid information in the form. Make sure to fill the * fields.", Toast.LENGTH_LONG).show();

                }

                else {
                    signOutUser();
                    signUpUser();
                }
            }
        });

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null)
                {
                    // User is signed in

                    //   Toast.makeText(SignUp.this, user.getEmail(), Toast.LENGTH_SHORT).show();
                    ed.putString("user_name", BackWorks.beforeAt(user.getEmail()));
                    ed.putString("user_type","st");
                    ed.apply();
                }
                else
                {
                    // User is signed out
                    // Log.d("fh", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        mGoogleApiClient =new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                            @Override
                            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                                Toast.makeText(SignUp.this, "Could not establish connection", Toast.LENGTH_SHORT).show();
                            }
                        }
                )
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();



        // google work ends

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home)
        {
            Intent i=new Intent(this,Home.class);
            startActivity(i);

        }
        else if (id == R.id.action_home)
        {
            Intent i=new Intent(this,Home.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    public void signOutUser()
    {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient);
        mAuth.signOut();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        progressBar.setVisibility(View.VISIBLE);
        Toast.makeText(SignUp.this, "Please wait while processing", Toast.LENGTH_LONG).show();
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result= Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess())
            {
                GoogleSignInAccount account=result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }
            else
            {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(SignUp.this, "Could not establish connection", Toast.LENGTH_SHORT).show();

            }
        }

    }



    private void firebaseAuthWithGoogle(GoogleSignInAccount account)
    {
        final Intent goMain=new Intent(this,MainActivity.class);

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            final FirebaseUser user = mAuth.getCurrentUser();
                            try {
                                // checking the user its status
                                FirebaseDatabase database=FirebaseDatabase.getInstance();


                                String uName=BackWorks.beforeAt(user.getEmail());

                                DatabaseReference myRef=database.getReference("Users").child(uName);



                                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        // This method is called once with the initial value and again
                                        // whenever data at this location is updated.
                                        String value = dataSnapshot.child("status").getValue(String.class);

                                        if(value!=null)
                                        {
                                            progressBar.setVisibility(View.GONE);
                                            showErrorDialog("This email is already registered to the system. Please use other email");
                                        }




                                        else
                                        {
                                            String email=user.getEmail();
                                            createUserInFireBase(email);
                                            createCurrentUserDataLocally(email);
                                            //  MainActivity.saveUserData(email);
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(SignUp.this, "Your account has been created.", Toast.LENGTH_SHORT).show();
                                            startActivity(goMain);

                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError error) {
                                        MainActivity.userStatus="error";
                                    }
                                });

                                // ------------------ends status and error showing ---------->
                            }
                            catch(NullPointerException e)
                            {
                                Toast.makeText(SignUp.this, "Please enter email properly", Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            // If sign in fails, display a message to the user.

                        }

                        // ...
                    }
                });
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void signUpUser()
    {


        try {
            signIn();
        }
        catch(Exception e)
        {
            Toast.makeText(SignUp.this, "Could not establish connection", Toast.LENGTH_SHORT).show();
        }

    }


    public void createUserInFireBase(String email)
    {

        EditText name=(EditText)findViewById(R.id.full_name);
        EditText lastName=(EditText)findViewById(R.id.last_name);
        EditText cnic=(EditText)findViewById(R.id.cnic);
        EditText ph=(EditText)findViewById(R.id.phoneNo);
        EditText country=(EditText)findViewById(R.id.country);
        EditText podtal=(EditText)findViewById(R.id.postalAddress);

        EditText qualification=(EditText)findViewById(R.id.qualification);
        EditText specialization=(EditText)findViewById(R.id.specialization);
        EditText exp=(EditText)findViewById(R.id.experience);




        Employee lawyer=new Employee();


        lawyer.setFirstName(name.getText().toString());
        lawyer.setLastName(lastName.getText().toString());
        lawyer.setStatus("AppUser");
        lawyer.setEmail(email);

        // for date picker -------------------------------------
        DatePicker datePicker=(DatePicker) findViewById(R.id.dateBirthPicker);
        int   day  = datePicker.getDayOfMonth();
        int   month= datePicker.getMonth();
        int   year = datePicker.getYear();

        long birthTimeStamp=BackWorks.convertStringDateToTimeStamp(day,month,year);

        lawyer.setDob(birthTimeStamp);
        lawyer.setCnic(cnic.getText().toString());
        lawyer.setPhoneNo(ph.getText().toString());
        lawyer.setCountry(country.getText().toString());
        lawyer.setAddress(podtal.getText().toString());



        lawyer.setEducation(qualification.getText().toString());
        lawyer.setSpecialization(specialization.getText().toString());
        lawyer.setAvailability(true);
        lawyer.setExperience(Integer.parseInt(exp.getText().toString()));


        LocalFirebase.signUpLawyer(lawyer);

        Toast.makeText(SignUp.this, "Account created Successfully", Toast.LENGTH_SHORT).show();

    }


    public void createCurrentUserDataLocally(String email)
    {
        userInfo=getSharedPreferences("user_info", Context.MODE_PRIVATE);
        final SharedPreferences.Editor ed =userInfo.edit();
        EditText name=(EditText)findViewById(R.id.full_name);
        EditText lastName=(EditText)findViewById(R.id.last_name);
        EditText cnic=(EditText)findViewById(R.id.cnic);
        EditText ph=(EditText)findViewById(R.id.phoneNo);
        EditText country=(EditText)findViewById(R.id.country);
        EditText podtal=(EditText)findViewById(R.id.postalAddress);
        EditText qualification=(EditText)findViewById(R.id.qualification);
        EditText specialization=(EditText)findViewById(R.id.specialization);
        EditText exp=(EditText)findViewById(R.id.experience);

        ed.putString("status","AppUser");
        ed.putString("fullName",name.getText().toString()+" "+lastName.getText().toString());
        ed.putString("cnic",cnic.getText().toString());
        ed.putString("phone",ph.getText().toString());
        ed.putString("country",country.getText().toString());
        ed.putString("postalCode",podtal.getText().toString());

        ed.putString("qualification",qualification.getText().toString());
        ed.putString("specialization",specialization.getText().toString());
        ed.putString("experience",exp.getText().toString());

        ed.putString("email",email);
        ed.putString("userName", BackWorks.beforeAt(email));



        ed.apply();

    }

    String beforeAt(String Email)
    {
        String[] split= Email.split("@");
        return split[0];
    }

    public void signOut(View v)
    {
        mAuth.signOut();
    }


    public void showErrorDialog(String message)
    {
        new AlertDialog.Builder(this)
                .setTitle("Sorry")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok,null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}
