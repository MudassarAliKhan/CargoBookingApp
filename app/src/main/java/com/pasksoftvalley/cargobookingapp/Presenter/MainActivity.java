package com.pasksoftvalley.cargobookingapp.Presenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

public class MainActivity extends AppCompatActivity {

    public static String userStatus="";
    static SharedPreferences userDetails;
    static Activity act;
    // declaration of views
    TextView displayedName, displayedEmail;
    Button googleSignIn;
    ProgressBar progressBar;
    private ProgressDialog mProgressDialog;

    private Button SignUp;
    // google sign ins
    private static final int RC_SIGN_IN = 1;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(Build.VERSION.SDK_INT >=21)
        {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }
        mProgressDialog = new ProgressDialog(this);
        SignUp =(Button)findViewById(R.id.SignUps) ;
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,SignUp.class);
                startActivity(i);
            }
        });
        /*Home home = new Home();
        home.displayedEmail = displayedEmail;
        home.displayedName = displayedName;*/
        /*Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Google SignIn and Other Work started here**/
        act=this;
        //google work starts
        userDetails = getSharedPreferences("user_info", Context.MODE_PRIVATE);

        // code starts here
        //views from drawer menu display
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    LocalFirebase.getUserStatus(user.getEmail());
                    //  startActivity(refresh);
                } else {
                }
                // ...
            }
        };
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                            @Override
                            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                                Toast.makeText(MainActivity.this, "Could not establish connection", Toast.LENGTH_SHORT).show();
                            }
                        }
                )
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
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
    public void signUpUser() {
        try {
            Signout();
            signIn();
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Could not establish connection. Please make sure you are connected to the internet.", Toast.LENGTH_LONG).show();
        }
    }
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    public void Signout() {
        final SharedPreferences.Editor ed = userDetails.edit();
        ed.putString("status", "error");
        ed.apply();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient);
        mAuth.signOut();
        //Toast.makeText(MainActivity.this, "User Signed out", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onBackPressed()
    {
        try
        {
            showAlertExit();

        }catch(Exception e)
        {
            Toast.makeText(getApplication(),e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount account)
    {
        final Intent restart = new Intent(this, MainActivity.class);
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            final FirebaseUser user = mAuth.getCurrentUser();
                            try {
                                LocalFirebase.getUserStatus(user.getEmail());

                                // checking the user its status
                                FirebaseDatabase database = FirebaseDatabase.getInstance();


                                String uName = BackWorks.beforeAt(user.getEmail());
                                DatabaseReference myRef = database.getReference("Users").child(uName);
                                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot)
                                    {
                                        String value = dataSnapshot.child("status").getValue(String.class);
                                        MainActivity.userStatus = value;
                                        if (value == null) {
                                            mProgressDialog.dismiss();
                                            showErrorDialog("The email is not registered to the system! Please create account");
                                        } else {
                                            saveUserData(user.getEmail());
                                            mProgressDialog.dismiss();
                                            if(userStatus.equals("Admin"))
                                            {
                                                startActivity(new Intent(getApplicationContext(),AdminDashBoard.class));
                                            }
                                            else
                                                if(userStatus.equals("AppUser"))
                                            {
                                                startActivity(new Intent(getApplicationContext(),Home.class));
                                            }
                                        }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError error) {
                                        MainActivity.userStatus = "error";
                                    }
                                });

                                // ------------------ends status and error showing ---------->


                            } catch (NullPointerException e) {

                            }
                        } else {
                            // If sign in fails, display a message to the user.
                        }
                        // ...
                    }
                });
    }
    public void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Sorry")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //code for sign in
        final Intent refresh = new Intent(this, MainActivity.class);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);

        if (requestCode == RC_SIGN_IN) {
            mProgressDialog.setMessage("Signing you in....");
            mProgressDialog.show();
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();


                try {
                    firebaseAuthWithGoogle(account);
                } catch (Exception e) {
                }


                // startActivity(refresh);
            } else {
                mProgressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Could not establish connection", Toast.LENGTH_SHORT).show();

            }
        }
        // ----------------------------- sign in ends --------------------------------



    }



    public void openSignIn(View v)
    {

        FrameLayout layout = (FrameLayout) findViewById(R.id.proglayout);
        mProgressDialog.setMessage("Signing you in...");
        mProgressDialog.show();
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        RelativeLayout rl = new RelativeLayout(MainActivity.this);
        rl.setGravity(Gravity.CENTER);
        layout.addView(rl,params);
        mProgressDialog.dismiss();
        signUpUser();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    public boolean checkInternetConnection()
    {
        boolean ch=false;
        ConnectivityManager ConnectionManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=ConnectionManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected())
        {
            ch=true;

        }
        else
        {
            ch=false;

        }
        return ch;
    }
    public static void saveUserData(String email) {
        try {
            final SharedPreferences.Editor ed = userDetails.edit();

            final String uName = BackWorks.beforeAt(email);

            //read for new requests form database
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Users").child(uName);
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String uStatus= String.valueOf(dataSnapshot.child("status").getValue());
                    if(uStatus.equals("Client")) {
                        ed.putString("userName", uName);
                        ed.putString("email", String.valueOf(dataSnapshot.child("email").getValue()));
                        ed.putString("status", String.valueOf(dataSnapshot.child("status").getValue()));
                        ed.putString("fullName", String.valueOf(dataSnapshot.child("firstName").getValue() + " " + String.valueOf(dataSnapshot.child("lastName").getValue())));
                        ed.putString("cnic", String.valueOf(dataSnapshot.child("cnic").getValue()));
                        ed.putString("phone", String.valueOf(dataSnapshot.child("phone").getValue()));
                        ed.putString("country", String.valueOf(dataSnapshot.child("country").getValue()));
                        ed.putString("postalCode", String.valueOf(dataSnapshot.child("postalCode").getValue()));
                        ed.apply();
                    }

                    else if(uStatus.equals("Employee")) {
                        ed.putString("userName", uName);
                        ed.putString("email", String.valueOf(dataSnapshot.child("email").getValue()));
                        ed.putString("status", String.valueOf(dataSnapshot.child("status").getValue()));
                        ed.putString("fullName", String.valueOf(dataSnapshot.child("firstName").getValue() + " " + String.valueOf(dataSnapshot.child("lastName").getValue())));
                        ed.putString("cnic", String.valueOf(dataSnapshot.child("cnic").getValue()));
                        ed.putString("phone", String.valueOf(dataSnapshot.child("phone").getValue()));
                        ed.putString("country", String.valueOf(dataSnapshot.child("country").getValue()));
                        ed.putString("postalCode", String.valueOf(dataSnapshot.child("postalCode").getValue()));

                        ed.putString("qualification",String.valueOf(dataSnapshot.child("qualification").getValue()));
                        ed.putString("specialization",String.valueOf(dataSnapshot.child("specialization").getValue()));
                        ed.putString("experience",String.valueOf(dataSnapshot.child("experience").getValue()));
                        ed.apply();
                    }

                    else if(uStatus.equals("Admin")) {
                        ed.putString("userName", uName);
                        ed.putString("email", String.valueOf(dataSnapshot.child("email").getValue()));
                        ed.putString("status", String.valueOf(dataSnapshot.child("status").getValue()));
                        ed.putString("fullName", String.valueOf(dataSnapshot.child("firstName").getValue() + " " + String.valueOf(dataSnapshot.child("lastName").getValue())));
                        ed.putString("cnic", String.valueOf(dataSnapshot.child("cnic").getValue()));
                        ed.putString("phone", String.valueOf(dataSnapshot.child("phone").getValue()));
                        ed.putString("country", String.valueOf(dataSnapshot.child("country").getValue()));
                        ed.putString("postalCode", String.valueOf(dataSnapshot.child("postalCode").getValue()));

                        ed.putString("qualification",String.valueOf(dataSnapshot.child("qualification").getValue()));
                        ed.putString("specialization",String.valueOf(dataSnapshot.child("specialization").getValue()));
                        ed.putString("firmName",String.valueOf(dataSnapshot.child("firmName").getValue()));
                        ed.putString("experience",String.valueOf(dataSnapshot.child("experience").getValue()));
                        ed.apply();
                    }
                }
                @Override
                public void onCancelled(DatabaseError error) {
                    MainActivity.userStatus = "error";
                }
            });


            userStatus = getUserStatus();

        } catch (Exception e) {
            Toast.makeText(act, "Some problem occurred! Please sign in properly.", Toast.LENGTH_SHORT).show();
        }



    }

    public void showAlertExit()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Caution");
        builder.setMessage("You are offline please sign in first");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
                // Do nothing
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
    public static String getUserName() {
        String name = userDetails.getString("fullName", "Sorry can't get name");
        return name;
    }


    public static String getUName() {
        String name = userDetails.getString("userName", "Sorry can't get name");
        return name;
    }

    public static String getEmail() {
        String email = userDetails.getString("email", "Sorry can't get email");
        return email;
    }
    public static String getUserStatus() {
        String status = userDetails.getString("status", "Sorry can't get email");
        return status;
    }
    public static String getUserCountry() {
        String status = userDetails.getString("country", "Sorry can't get email");
        return status;
    }

}
