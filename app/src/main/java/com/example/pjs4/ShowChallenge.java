package com.example.pjs4;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ShowChallenge extends AppCompatActivity {

    private TextView tv_title, tv_description, tv_level, tv_type, tv_verif;
    private Button btn_upload, btn_galery;
    private ImageView img_view;
    private StorageReference mStorageRef;
    public Uri imguri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_challenge);

        mStorageRef = FirebaseStorage.getInstance().getReference("Images");
        tv_title = findViewById(R.id.tv_title);
        tv_description = findViewById(R.id.tv_description);
        tv_level = findViewById(R.id.tv_level);
        tv_type = findViewById(R.id.tv_type);
        tv_verif = findViewById(R.id.tv_verif);
        btn_upload = findViewById(R.id.btn_poster);
        btn_galery = findViewById(R.id.btn_galery);
        img_view = findViewById(R.id.img_view);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        initChallenge();


        btn_galery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileChooser();
            }
        });
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fileuploader();
            }
        });

    }

    private String getExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    private void Fileuploader() {
        StorageReference Ref = mStorageRef.child("Images" + "/" + System.currentTimeMillis() + "." + getExtension(imguri));

        Ref.putFile(imguri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Toast.makeText(ShowChallenge.this, "Défi relevé", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {

                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        //calculating progress percentage
//                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
//                        //displaying percentage in progress dialog
//                        progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                    }
                });
    }


    private void FileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            imguri = data.getData();
            img_view.setImageURI(imguri);
        }
    }

    private void initChallenge() {
        Bundle b = getIntent().getExtras();
        tv_title.setText(b.getString("tv_challengeName"));
        tv_description.setText(b.getString("tv_challengeDesc"));
        tv_type.setText(b.getString("tv_challengeType"));
        tv_level.setText(b.getString("tv_challengeLevel"));
        String verif = b.getString("verif");

        if (verif.equals("true")) {
            tv_verif.setText("Challenge déjà terminé BRAVO !");

            btn_galery.setVisibility(View.GONE);
            btn_upload.setVisibility(View.GONE);
        }
    }
}

