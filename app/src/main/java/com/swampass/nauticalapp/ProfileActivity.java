package com.swampass.nauticalapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileActivity extends AppCompatActivity {



    private ProgressDialog  mProgress;
    private CircleImageView mProfilePic;
    private Button mSubmitBtn;
    private static final int GALLERY_REQUEST = 1;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    private Uri mImageUri = null;
    private DatabaseReference mDatabaseUsers;
    private StorageReference mStorageImage;
    private ImageView mpopup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar = (Toolbar) findViewById(R.id.tToolbar);
        if (toolbar != null)
            setSupportActionBar(toolbar);


        mAuth = FirebaseAuth.getInstance();

        mProfilePic = (CircleImageView) findViewById(R.id.profileimagebutton2);
        mSubmitBtn = (Button) findViewById(R.id.setupSubmit);
        mProgress = new ProgressDialog(this);

        mStorageImage = FirebaseStorage.getInstance().getReference().child("Profile_images");
        mAuth = FirebaseAuth.getInstance();
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");

        ImageView homeActivity = (ImageView) toolbar.findViewById(R.id.action_home);
        ImageView cnectActivity = (ImageView) toolbar.findViewById(R.id.action_msg);


        setmProfilePic();


        mProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
            }
        });



        homeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileActivity = new Intent(ProfileActivity.this, HomeActivity.class);
                startActivity(profileActivity);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }

        });
        cnectActivity.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent knct = new Intent(ProfileActivity.this, ConnectionsActivity.class);
                startActivity(knct);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }

        });
    }





    private void startSetupAccount() {

        FirebaseUser user = mAuth.getCurrentUser();



        if (mImageUri != null) {

            //For acutal end of setup
            mProgress.setMessage("Finishing Setup...");
            mProgress.show();
            // ends here
            StorageReference filepath = mStorageImage.child(mImageUri.getLastPathSegment());
            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    final String user_id = mAuth.getCurrentUser().getUid();
                    @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();



                    mDatabaseUsers.child(user_id).child("image").setValue(downloadUrl.toString());

                }
            });


        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();

            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);


        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mImageUri = result.getUri();
                mProfilePic.setImageURI(mImageUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }


    private void setmProfilePic()
    {
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference("Users");

        if(mAuth.getCurrentUser() != null) {

            mDatabaseUsers.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String pic = dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("image").getValue(String.class);

                    Picasso.with(getApplicationContext()).load(Uri.parse(pic)).fit().centerCrop().into(mProfilePic);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {


                }
            });
        }

        mpopup = (ImageView) findViewById(R.id.Popup);
        mpopup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                PopupMenu popup = new PopupMenu(ProfileActivity.this, mpopup);

                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());


                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        if (item.getItemId() == R.id.Logout_btn3) {

                            mAuth.signOut();
                            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                            finish();
                            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);

                        }
                        return true;
                    }
                });

                popup.show();
            }
        });






    }




    public class RoundTransformation implements com.squareup.picasso.Transformation {

        private final int radius;
        private final int margin;

        public RoundTransformation(final int radius, final int margin) {
            this.radius = radius;
            this.margin = margin;
        }

        @Override
        public Bitmap transform(Bitmap source) {
            final Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));



            Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            canvas.drawRoundRect(new RectF(margin, margin, source.getWidth() - margin, source.getHeight() - margin), radius, radius, paint);

            if (source != output) {
                source.recycle();
            }

            return output;
        }

        @Override
        public String key() {
            return "rounded";
        }

    }





    }


