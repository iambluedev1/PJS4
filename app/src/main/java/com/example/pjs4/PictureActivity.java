package com.example.pjs4;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class PictureActivity extends AppCompatActivity {

    private Button btn_upload, btn_galery;
    private ImageView img_view;
    private StorageReference mStorageRef;
    public Uri imguri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        mStorageRef = FirebaseStorage.getInstance().getReference("Images");

        btn_upload = findViewById(R.id.btn_upload);
        btn_galery = findViewById(R.id.btn_galery);
        img_view = findViewById(R.id.img_view);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        btn_galery.setOnClickListener(v -> FileChooser());
        btn_upload.setOnClickListener(v -> Fileuploader());

    }

    private String getExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    private void Fileuploader() {
        StorageReference Ref = mStorageRef.child("Images" + "/" + System.currentTimeMillis() + "." + getExtension(imguri));

        Ref.putFile(imguri)
                .addOnSuccessListener(taskSnapshot -> {
                    // Get a URL to the uploaded content
                    //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    Toast.makeText(PictureActivity.this, "image Iploded SuccesFull", Toast.LENGTH_LONG).show();
                })
                .addOnFailureListener(exception -> {
                })
                .addOnProgressListener(taskSnapshot -> {
                    //calculating progress percentage
//                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
//                        //displaying percentage in progress dialog
//                        progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
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


}