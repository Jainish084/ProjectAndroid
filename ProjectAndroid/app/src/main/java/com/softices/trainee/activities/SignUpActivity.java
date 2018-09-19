package com.softices.trainee.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.softices.trainee.R;
import com.softices.trainee.database.DbHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.Manifest.permission.CAMERA;
import static com.softices.trainee.methods.L.isValidEmail;
import static com.softices.trainee.methods.L.isValidFirstName;
import static com.softices.trainee.methods.L.isValidLastName;
import static com.softices.trainee.methods.L.isValidMobileNumber;
import static com.softices.trainee.methods.L.isValidPassword;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    public EditText edtFirstName, edtLastName, edtEmail, edtMobileNumber, edtPassword,
            edtConfirmPassword;
    public Button btnSignUp;
    public TextView txtLoginNow;
    public DbHelper dbHelper;
    public RadioGroup radioGroup;
    public Toolbar toolbar;
    public CircleImageView circleImageView;
    Bitmap myBitmap;
    Uri picUri;


    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList();
    private ArrayList<String> permissions = new ArrayList();

    private final static int ALL_PERMISSIONS_RESULT = 107;
    private static final int CAMERA_PIC_REQUEST = 100;
    private static final int SELECT_IMAGE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        init();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        permissions.add(CAMERA);
        permissionsToRequest = findUnAskedPermissions(permissions);
        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap;
        if (resultCode == Activity.RESULT_OK) {
            if (getPickImageResultUri(data) != null) {
                picUri = getPickImageResultUri(data);
                try {
                    myBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), picUri);
//                    myBitmap = rotateImageIfRequired(myBitmap, picUri);
//                    myBitmap = getResizedBitmap(myBitmap, 500);
                    circleImageView.setImageBitmap(myBitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                bitmap = (Bitmap) data.getExtras().get("data");
                myBitmap = bitmap;
                if (circleImageView != null) {
                    circleImageView.setImageBitmap(myBitmap);
                }
            }
        }
    }

    public Uri getPickImageResultUri(Intent data) {
        boolean isCamera = true;
        if (data != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }
        return isCamera ? getCaptureImageOutputUri() : data.getData();
    }

    private ArrayList<String> findUnAskedPermissions(ArrayList<String> permissions) {
        ArrayList<String> result = new ArrayList();
        for (String permission : permissions) {
            if (!hasPermission(permission)) {
                result.add(permission);
            }
        }
        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    public Intent getPickImageChooserIntent() {
        // Determine Uri of camera image to save.
        Uri outputFileUri = getCaptureImageOutputUri();
        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getPackageManager();
        // collect all camera intents
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(captureIntent, CAMERA_PIC_REQUEST);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }
        // collect all gallery intents
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(galleryIntent, SELECT_IMAGE);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }
        // the main intent is the last in the list (fucking android) so pickup the useless one
        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);
        // Create a chooser from the main intent
        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");
        // Add all other intents
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));
        return chooserIntent;
    }

    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
        }
        return outputFileUri;
    }

    public void init() {
        edtFirstName = (EditText) findViewById(R.id.edt_first_name);
        edtLastName = (EditText) findViewById(R.id.edt_last_name);
        edtEmail = (EditText) findViewById(R.id.edt_signup_email);
        edtMobileNumber = (EditText) findViewById(R.id.edt_mobile_number);
        edtPassword = (EditText) findViewById(R.id.edt_signup_password);
        edtConfirmPassword = (EditText) findViewById(R.id.edt_signup_confirmPassword);
        btnSignUp = (Button) findViewById(R.id.btn_signup);
        txtLoginNow = (TextView) findViewById(R.id.txt_login_now);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        circleImageView = (CircleImageView) findViewById(R.id.circle_img_profile);
        circleImageView.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        txtLoginNow.setOnClickListener(this);
        dbHelper = new DbHelper(this);
        edtFirstName.setText("Jainish");
        edtLastName.setText("Beladiya");
        edtEmail.setText("aaa@aaa.aaa");
        edtMobileNumber.setText("9638785300");
        edtPassword.setText("123456");
        edtConfirmPassword.setText("123456");
    }


    public void clickedSignUp() {
        String firstName = edtFirstName.getText().toString();
        String lastName = edtLastName.getText().toString();
        String email = edtEmail.getText().toString();
        String mobileNumber = edtMobileNumber.getText().toString();
        String password = edtPassword.getText().toString();
        String confirmPassword = edtConfirmPassword.getText().toString();
        int selectedRadioButton = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) findViewById(selectedRadioButton);

        if (!isValidFirstName(firstName)) {
            Toast.makeText(SignUpActivity.this, "Please Enter valid First Name!",
                    Toast.LENGTH_LONG).show();
        } else if (!isValidLastName(lastName)) {
            Toast.makeText(SignUpActivity.this, "Please Enter valid Last Name",
                    Toast.LENGTH_LONG).show();
        } else if (!isValidEmail(email)) {
            Toast.makeText(SignUpActivity.this, "Please Enter valid Email!",
                    Toast.LENGTH_LONG).show();
        } else if (!isValidMobileNumber(mobileNumber)) {
            Toast.makeText(SignUpActivity.this, "Please Enter valid Mobile Number",
                    Toast.LENGTH_LONG).show();
        } else if (!isValidPassword(password)) {
            Toast.makeText(SignUpActivity.this, "Please Enter valid Password",
                    Toast.LENGTH_LONG).show();
        } else if (!password.equals(confirmPassword)) {
            Toast.makeText(SignUpActivity.this, "Password and Confirm Password " +
                    "must be same!", Toast.LENGTH_LONG).show();
        } else if (dbHelper.insertDataIntoDatabase(firstName, lastName, email, mobileNumber,
                radioButton.getText().toString(), password)) {
            Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
            Toast.makeText(SignUpActivity.this, "User register Successfully.!", Toast.LENGTH_LONG).show();
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(SignUpActivity.this, "Something went Wrong.!",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_signup:
                clickedSignUp();
                break;
            case R.id.circle_img_profile:
                startActivityForResult(getPickImageChooserIntent(), 200);
                break;
            case R.id.txt_login_now:
                Intent txtLoginNow = new Intent(SignUpActivity.this,
                        SignInActivity.class);
                startActivity(txtLoginNow);
                break;
            default:
                break;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
