/*
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.svakatha;
//package com.google.sample.cloudvision;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AlertDialog;

import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequest;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ServerValue;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
//import com.google.sample.cloudvision;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;


public class MainActivity1 extends AppCompatActivity {
    private static final String CLOUD_VISION_API_KEY = "AIzaSyBYZftL8rp5UhFUPMHM_1dJ-3tfqNVN34E";
    public static final String FILE_NAME = "temp.jpg";
    private static final String ANDROID_CERT_HEADER = "X-Android-Cert";
    private static final String ANDROID_PACKAGE_HEADER = "X-Android-Package";
    private static final int MAX_LABEL_RESULTS = 10;
    private static final int MAX_DIMENSION = 1200;

    private static final String TAG = MainActivity1.class.getSimpleName();
    private static final int GALLERY_PERMISSIONS_REQUEST = 0;
    private static final int GALLERY_IMAGE_REQUEST = 1;
    public static final int CAMERA_PERMISSIONS_REQUEST = 2;
    public static final int CAMERA_IMAGE_REQUEST = 3;

    //private TextView mImageDetails;
    private ImageView mMainImage;
    public ImageView image2;
    public ImageView image3;
    public ImageView image4;
    public ImageView image5;
    public ImageView image6;
    public ImageView image7;
//testing
    public int i = 0;

    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_closet);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        image2 = findViewById(R.id.imageView2);
        image3 = findViewById(R.id.imageView3);
        image4 = findViewById(R.id.imageView4);
        image5 = findViewById(R.id.imageView5);
        image6 = findViewById(R.id.imageView6);
        image7 = findViewById(R.id.imageView7);

        //Initializing Firebase Instance
        mAuth = FirebaseAuth.getInstance();
        String currentUser=mAuth.getCurrentUser().getUid();
        mStorageRef = FirebaseStorage.getInstance().getReference();
//        db.collection("users").document(currentUser)
//                .collection("ClosetDetails").get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        List<DocumentSnapshot> docList=queryDocumentSnapshots.getDocuments();
//                        Picasso.get().load(docList.get(0).getString("downloadUrl")).into(image2);
//                        Picasso.get().load(docList.get(1).getString("downloadUrl")).into(image3);
//                        Picasso.get().load(docList.get(2).getString("downloadUrl")).into(image4);
//                        Picasso.get().load(docList.get(3).getString("downloadUrl")).into(image5);
//                    }
//                });
        db.collection("users").document(currentUser)
                .collection("ClosetDetails").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> docList=queryDocumentSnapshots.getDocuments();
                        Toast.makeText(MainActivity1.this, "Success", Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity1.this,String.valueOf(queryDocumentSnapshots.size()), Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity1.this, Boolean.toString(queryDocumentSnapshots.isEmpty()), Toast.LENGTH_SHORT).show();
                        int count=queryDocumentSnapshots.size();
                        if(queryDocumentSnapshots.isEmpty() && (count==0)){
                            Toast.makeText(MainActivity1.this, "No Closet Image Exist", Toast.LENGTH_SHORT).show();
                        }
                        else if(count<=4){
                            Toast.makeText(MainActivity1.this, "In abc", Toast.LENGTH_SHORT).show();

                            Query query=db.collection("users").document(currentUser)
                                    .collection("ClosetDetails")
                                    .orderBy("Time", Query.Direction.DESCENDING);
                            query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                    List<DocumentSnapshot> innerdocList=queryDocumentSnapshots.getDocuments();
                                    int innercount=innerdocList.size();
                                    if(innercount==1){
                                        Picasso.get().load(innerdocList.get(0).getString("downloadUrl")).into(image2);}
                                        if(innercount==2){
                                            Picasso.get().load(innerdocList.get(0).getString("downloadUrl")).into(image2);
                                        Picasso.get().load(innerdocList.get(1).getString("downloadUrl")).into(image3);}
                                        if(innercount==3){
                                            Picasso.get().load(innerdocList.get(0).getString("downloadUrl")).into(image2);
                                            Picasso.get().load(innerdocList.get(1).getString("downloadUrl")).into(image3);
                                        Picasso.get().load(innerdocList.get(2).getString("downloadUrl")).into(image4);}
                                        if(innercount==4){
                                            Picasso.get().load(innerdocList.get(0).getString("downloadUrl")).into(image2);
                                            Picasso.get().load(innerdocList.get(1).getString("downloadUrl")).into(image3);
                                            Picasso.get().load(innerdocList.get(2).getString("downloadUrl")).into(image4);
                                            Picasso.get().load(innerdocList.get(3).getString("downloadUrl")).into(image5);}
                                }
                            });
                        }else {
                            Toast.makeText(MainActivity1.this, "Not Enough Doc", Toast.LENGTH_SHORT).show();
                            Picasso.get().load(docList.get(0).getString("downloadUrl")).into(image2);
                            Picasso.get().load(docList.get(1).getString("downloadUrl")).into(image3);
                            Picasso.get().load(docList.get(2).getString("downloadUrl")).into(image4);
                            Picasso.get().load(docList.get(3).getString("downloadUrl")).into(image5);
                        }
                    }
                })
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        List<DocumentSnapshot> docList=queryDocumentSnapshots.getDocuments();
//                        int count=docList.size();
//                        if(!queryDocumentSnapshots.isEmpty() && (count>4)){
//                            Toast.makeText(MainActivity1.this, "No ClosetDetails Exists", Toast.LENGTH_SHORT).show();
//                        }else {
//                            Query query=db.collection("users").document(currentUser)
//                                    .collection("ClosetDetails")
//                                    .orderBy("Time", Query.Direction.DESCENDING);
//                            query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                                @Override
//                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                                        Picasso.get().load(docList.get(0).getString("downloadUrl")).into(image2);
//                                        Picasso.get().load(docList.get(1).getString("downloadUrl")).into(image3);
//                                        Picasso.get().load(docList.get(2).getString("downloadUrl")).into(image4);
//                                        Picasso.get().load(docList.get(3).getString("downloadUrl")).into(image5);
//                                }
//                            });
//                        }
//                    }
             //   })
            .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity1.this, "Failed", Toast.LENGTH_SHORT).show();
                Log.i("Status",e.toString());
            }
        });



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity1.this);
            builder
                    .setMessage("Upload a Picture")
                    .setPositiveButton("Gallery", (dialog, which) -> startGalleryChooser())
                    .setNegativeButton("Camera", (dialog, which) -> startCamera());
            builder.create().show();
        });

        //mImageDetails = findViewById(R.id.image_details);
        mMainImage = findViewById(R.id.main_image);

    }

    public void startGalleryChooser() {
        if (PermissionUtils.requestPermission(this, GALLERY_PERMISSIONS_REQUEST, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select a photo"),
                    GALLERY_IMAGE_REQUEST);
        }
    }

    public void startCamera() {
        if (PermissionUtils.requestPermission(
                this,
                CAMERA_PERMISSIONS_REQUEST,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri photoUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", getCameraFile());
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(intent, CAMERA_IMAGE_REQUEST);
        }
    }

    public File getCameraFile() {
        File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return new File(dir, FILE_NAME);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            uploadImage(data.getData());
        } else if (requestCode == CAMERA_IMAGE_REQUEST && resultCode == RESULT_OK) {
            Uri photoUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", getCameraFile());
            uploadImage(photoUri);
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PERMISSIONS_REQUEST:
                if (PermissionUtils.permissionGranted(requestCode, CAMERA_PERMISSIONS_REQUEST, grantResults)) {
                    startCamera();
                }
                break;
            case GALLERY_PERMISSIONS_REQUEST:
                if (PermissionUtils.permissionGranted(requestCode, GALLERY_PERMISSIONS_REQUEST, grantResults)) {
                    startGalleryChooser();
                }
                break;
        }
    }

    public void uploadImage(Uri uri) {
        String DownloadURL;
        if (uri != null) {
            try {
                // scale the image to save on bandwidth
                Bitmap bitmap =
                        scaleBitmapDown(
                                MediaStore.Images.Media.getBitmap(getContentResolver(), uri),
                                MAX_DIMENSION);

                callCloudVision(bitmap);
                mMainImage.setImageBitmap(bitmap);
                i++;
              //testing
                if (i == 7)
                {   i = 1;
                    image2.setImageBitmap(bitmap);
                    image3.setImageDrawable(null);
                    image4.setImageDrawable(null);
                    image5.setImageDrawable(null);
                    image6.setImageDrawable(null);
                    image7.setImageDrawable(null);
                }else if( i == 1)
                {
                    image2.setImageBitmap(bitmap);

                }else if (i == 2)
                {
                    image3.setImageBitmap(bitmap);

                }else if (i == 3)
                {
                    image4.setImageBitmap(bitmap);

                }else if (i == 4)
                {
                    image5.setImageBitmap(bitmap);

                }else if (i == 5)
                {
                    image6.setImageBitmap(bitmap);

                }else if (i == 6)
                {
                    image7.setImageBitmap(bitmap);

                }
                mAuth=FirebaseAuth.getInstance();
                String currentUser=mAuth.getCurrentUser().getUid();
                StorageReference filePath=mStorageRef.child("UserClosetImages").child(currentUser).child(UUID.randomUUID().toString());
                filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.i("Status","Uploaded");
                        filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Log.i("Status",uri.toString());
                                Map<String ,String> data=new HashMap<>();
                                 data.put("downloadUrl",uri.toString());
                                 Map<String,Object> data_time=new HashMap<>();
                                 data_time.put("Time",new Timestamp(new Date()));
//                                ClosetModel closetModel=new ClosetModel();
//                                closetModel.setDonwloadUrl(uri.toString());
                                String currentUser=mAuth.getCurrentUser().getUid();
                                db.collection("users").document(currentUser)
                                        .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        String docName=documentSnapshot.getString("closetChoiceDocName");
                                        db.collection("users").document(currentUser)
                                                .collection("ClosetDetails").document(docName)
                                                .set(data,SetOptions.merge());
                                        db.collection("users").document(currentUser)
                                                .collection("ClosetDetails").document(docName)
                                                .set(data_time,SetOptions.merge());
                                    }
                                });
                            }
                        });
                    }
                });


            } catch (IOException e) {
                Log.d(TAG, "Image picking failed because " + e.getMessage());
                Toast.makeText(this, R.string.image_picker_error, Toast.LENGTH_LONG).show();
            }
        } else {
            Log.d(TAG, "Image picker gave us a null image.");
            Toast.makeText(this, R.string.image_picker_error, Toast.LENGTH_LONG).show();
        }
    }

    private Vision.Images.Annotate prepareAnnotationRequest(Bitmap bitmap) throws IOException {
        HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

        VisionRequestInitializer requestInitializer =
                new VisionRequestInitializer(CLOUD_VISION_API_KEY) {
                    /**
                     * We override this so we can inject important identifying fields into the HTTP
                     * headers. This enables use of a restricted cloud platform API key.
                     */
                    @Override
                    protected void initializeVisionRequest(VisionRequest<?> visionRequest)
                            throws IOException {
                        super.initializeVisionRequest(visionRequest);

                        String packageName = getPackageName();
                        visionRequest.getRequestHeaders().set(ANDROID_PACKAGE_HEADER, packageName);

                        String sig = PackageManagerUtils.getSignature(getPackageManager(), packageName);

                        visionRequest.getRequestHeaders().set(ANDROID_CERT_HEADER, sig);
                    }
                };

        Vision.Builder builder = new Vision.Builder(httpTransport, jsonFactory, null);
        builder.setVisionRequestInitializer(requestInitializer);

        Vision vision = builder.build();

        BatchAnnotateImagesRequest batchAnnotateImagesRequest =
                new BatchAnnotateImagesRequest();
        batchAnnotateImagesRequest.setRequests(new ArrayList<AnnotateImageRequest>() {{
            AnnotateImageRequest annotateImageRequest = new AnnotateImageRequest();

            // Add the image
            Image base64EncodedImage = new Image();
            // Convert the bitmap to a JPEG
            // Just in case it's a format that Android understands but Cloud Vision
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();

            // Base64 encode the JPEG
            base64EncodedImage.encodeContent(imageBytes);
            annotateImageRequest.setImage(base64EncodedImage);

            // add the features we want
            annotateImageRequest.setFeatures(new ArrayList<Feature>() {{
                Feature labelDetection = new Feature();
                labelDetection.setType("LABEL_DETECTION");
                labelDetection.setMaxResults(MAX_LABEL_RESULTS);
                add(labelDetection);
            }});

            // Add the list of one thing to the request
            add(annotateImageRequest);
        }});

        Vision.Images.Annotate annotateRequest =
                vision.images().annotate(batchAnnotateImagesRequest);
        // Due to a bug: requests to Vision API containing large images fail when GZipped.
        annotateRequest.setDisableGZipContent(true);
        Log.d(TAG, "created Cloud Vision request object, sending request");

        return annotateRequest;
    }

    private static class LableDetectionTask extends AsyncTask<Object, Void, String> {
        private final WeakReference<MainActivity1> mActivityWeakReference;
        private Vision.Images.Annotate mRequest;
        String userDocName;
        private FirebaseAuth mAuth=FirebaseAuth.getInstance();
        private StorageReference mStorageRef;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String closetDocName=UUID.randomUUID().toString();

        LableDetectionTask(MainActivity1 activity, Vision.Images.Annotate annotate) {
            mActivityWeakReference = new WeakReference<>(activity);
            mRequest = annotate;
        }

        @Override
        protected String doInBackground(Object... params) {
            try {
                Log.d(TAG, "created Cloud Vision request object, sending request");
                BatchAnnotateImagesResponse response = mRequest.execute();
                return convertResponseToString(response);

            } catch (GoogleJsonResponseException e) {
                Log.d(TAG, "failed to make API request because " + e.getContent());
            } catch (IOException e) {
                Log.d(TAG, "failed to make API request because of other IOException " +
                        e.getMessage());
            }
            return "Cloud Vision API request failed. Check logs for details.";
        }

        protected void onPostExecute(String result) {
            MainActivity1 activity = mActivityWeakReference.get();
            if (activity != null && !activity.isFinishing()) {
                //TextView imageDetail = activity.findViewById(R.id.image_details);
                //imageDetail.setText(result);
                String currentUSer=mAuth.getCurrentUser().getUid();
              //  db.collection("users").document(currentUSer).update("closetChoiceDocName",FieldValue.delete());
                Map<String,String >data=new HashMap<>();
                data.put("AnalysisText",result);
                db.collection("users").document(currentUSer)
                        .collection("ClosetDetails").document(closetDocName)
                        .set(data,SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Map<String,String>data=new HashMap<>();
                        data.put("closetChoiceDocName",closetDocName);
                        db.collection("users").document(currentUSer).set(data, SetOptions.merge());
                    }
                });
            }
        }

//        private void setText(String result) {
//            FirebaseAuth mAuth=FirebaseAuth.getInstance();
//            StorageReference mStorageRef;
//            FirebaseFirestore db = FirebaseFirestore.getInstance();
//            String CurrentUser=mAuth.getCurrentUser().getUid();
//        }
    }

    private void callCloudVision(final Bitmap bitmap) {
        // Switch text to loading
        //mImageDetails.setText(R.string.loading_message);

        // Do the real work in an async task, because we need to use the network anyway
        try {
            AsyncTask<Object, Void, String> labelDetectionTask = new LableDetectionTask(this, prepareAnnotationRequest(bitmap));
            labelDetectionTask.execute();
        } catch (IOException e) {
            Log.d(TAG, "failed to make API request because of other IOException " +
                    e.getMessage());
        }
    }

    private Bitmap scaleBitmapDown(Bitmap bitmap, int maxDimension) {

        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        int resizedWidth = maxDimension;
        int resizedHeight = maxDimension;

        if (originalHeight > originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = (int) (resizedHeight * (float) originalWidth / (float) originalHeight);
        } else if (originalWidth > originalHeight) {
            resizedWidth = maxDimension;
            resizedHeight = (int) (resizedWidth * (float) originalHeight / (float) originalWidth);
        } else if (originalHeight == originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = maxDimension;
        }
        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false);
    }

    private static String convertResponseToString(BatchAnnotateImagesResponse response) {
        StringBuilder message = new StringBuilder("Detected:\n\n");

        List<EntityAnnotation> labels = response.getResponses().get(0).getLabelAnnotations();
        if (labels != null) {
            for (EntityAnnotation label : labels) {
                message.append(String.format(Locale.US, "%.3f: %s", label.getScore(), label.getDescription()));
                message.append("\n");
            }
        } else {
            message.append("nothing");
        }

        return message.toString();
    }


}
