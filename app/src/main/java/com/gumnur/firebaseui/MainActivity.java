package com.gumnur.firebaseui;

        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.net.Uri;
        import android.provider.MediaStore;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;
        import com.kairos.*;

        import org.json.JSONException;

        import java.io.UnsupportedEncodingException;


public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    Bitmap bitmap;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;

    //Bitmap For Camera And Galler
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        bitmap = (Bitmap)data.getExtras().get("data");
        imageView.setImageBitmap(bitmap);

        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
    }
    }

    //Code For Lost People
    private void Lost(Bitmap lostImage, Kairos kairos1, KairosListener KL) throws UnsupportedEncodingException, JSONException {
        Bitmap image = lostImage;
        String subjectId = "People Lost";
        String galleryId = "mom";
        String selector = "FULL";
        String multipleFaces = "false";
        String minHeadScale = "0.25";
        kairos1.enroll(image,
                subjectId,
                galleryId,
                selector,
                multipleFaces,
                minHeadScale,
                KL);
    }

    //Code For People Who are Found
    private void Found(Bitmap foundImage, Kairos kairos1, KairosListener KL) throws UnsupportedEncodingException, JSONException {
        Bitmap image = foundImage;
        String subjectId = "People Found";
        String galleryId = "a-gallery";
        String selector = "FULL";
        String multipleFaces = "false";
        String minHeadScale = "0.25";
        kairos1.enroll(image,
                subjectId,
                galleryId,
                selector,
                multipleFaces,
                minHeadScale,
                KL);
    }

    private static final String TAG = "Log";

    //onCreate Stuff - Id's of buttons, etc..
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate");

        Button btnCamera =(Button)findViewById(R.id.btnCamera);
        Button btnLost = (Button)findViewById(R.id.btnLost);
        imageView =(ImageView)findViewById(R.id.imageView);
        Button btnGallery =(Button) findViewById(R.id.btnGallery);
        Button btnFound = (Button)findViewById(R.id.btnFound);



        //Camera Button
        btnCamera.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
            }
        });

        //Gallery Button
        btnGallery.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openGallery();

            }
        });

        // listener
        final KairosListener listener = new KairosListener() {

            @Override
            public void onSuccess(String response) {
                Log.d("SUCCESS!!", response);
            }

            @Override
            public void onFail(String response) {
                Log.d("FAILED!!", response);
            }
        };


        /* * * instantiate a new kairos instance * * */
        final Kairos myKairos = new Kairos();

        /* * * set authentication * * */
        String app_id = "cb837617";
        String api_key = "8fe7b3f71543b4a8c257b994feec0174";
        myKairos.setAuthentication(this, app_id, api_key);

        try {


            /* * * * * * * * * * * * * * * * * * * * */
            /* * *  Kairos Method Call Examples * * */
            /* * * * * * * * * * * * * * * * * * * */
            /* * * * * * * * * * * * * * * * * * **/
            /* * * * * * * * * * * * * * * * * * */
            /* * * * * * * * * * * * * * * * * **/
            /* * * * * * * * * * * * * * * * * */
            /* * * * * * * * * * * * * * * * **/
            /* * * * * * * * * * * * * * * * */


            //  List galleries
            myKairos.listGalleries(listener);


            /* * * * * * * * DETECT EXAMPLES * * * * * * *
            // Bare-essentials Example:
            // This example uses only an image url, setting optional params to null
            String image = "http://media.kairos.com/liz.jpg";
            myKairos.detect(image, null, null, listener);
            // Fine-grained Example:
            // This example uses a bitmap image and also optional parameters
            Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.liz);
            String selector = "FULL";
            String minHeadScale = "0.25";
            myKairos.detect(image, selector, minHeadScale, listener);
            */


            /* * * * * * * RECOGNIZE EXAMPLES * * * * * * *
            // Bare-essentials Example:
            // This example uses only an image url, setting optional params to null
            String image = "http://media.kairos.com/liz.jpg";
            String galleryId = "friends";
            myKairos.recognize(image, galleryId, null, null, null, null, listener);
            // Fine-grained Example:
            // This example uses a bitmap image and also optional parameters
            Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.liz);
            String galleryId = "friends";
            String selector = "FULL";
            String threshold = "0.75";
            String minHeadScale = "0.25";
            String maxNumResults = "25";
            myKairos.recognize(image,
                    galleryId,
                    selector,
                    threshold,
                    minHeadScale,
                    maxNumResults,
                    listener);
                    */


            /* * * * GALLERY-MANAGEMENT EXAMPLES * * * *
            //  List galleries
            myKairos.listGalleries(listener);
            //  List subjects in gallery
            myKairos.listSubjectsForGallery("your_gallery_name", listener);
            // Delete subject from gallery
            myKairos.deleteSubject("your_subject_id", "your_gallery_name", listener);
            // Delete an entire gallery
            myKairos.deleteGallery("your_gallery_name", listener);
            */



        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //Lost Button
        btnLost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Lost(bitmap, myKairos, listener);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        btnFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Found(bitmap, myKairos, listener);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void openGallery(){
        Intent Gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(Gallery, PICK_IMAGE);

    }

}