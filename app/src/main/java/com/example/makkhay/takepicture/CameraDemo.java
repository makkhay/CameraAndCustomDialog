package com.example.makkhay.takepicture;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;

public class CameraDemo extends AppCompatActivity
{

    LayoutInflater inflater;
    private EditText savePicture;
    int TAKE_PHOTO_CODE = 0;
    public static int count = 0;
    ImageView picture;
    TextView name;
    Button saveButton1;
    final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) +
            "/Picture Folder";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_demo);
        //inflater to use save_picture.xml
        inflater = CameraDemo.this.getLayoutInflater();
        picture = (ImageView) findViewById(R.id.imageView);

        File newDir = new File(dir);
        newDir.mkdirs();


        Button capture = (Button) findViewById(R.id.btnCapture);
        capture.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                setImageName();
                count++;
                String file = dir + count + ".jpg";
                File newFile = new File(file);
                try {
                    newFile.createNewFile();
                } catch (IOException e) {
                }

                Uri outputFileUri = Uri.fromFile(newFile);
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        super.onActivityResult(requestCode, resultCode, data);
        String path = dir + count + ".jpg";
        picture.setImageDrawable(Drawable.createFromPath(path));

        if (requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK) {
            Toast.makeText(this, "Your photo is saved in " + dir,
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Dialog box to set the picture name
     */

    public void setImageName()
    {
        // Create a new instance of ALertDialog box
        AlertDialog.Builder builder = new AlertDialog.Builder(CameraDemo.this);
        // inflate from save_picture.xml
        View mView = inflater.inflate(R.layout.save_picture, null);
        // We can use savePicture editText id from save_picture.xml now
        savePicture = (EditText) mView.findViewById(R.id.savePicture);
        name = (TextView) findViewById(R.id.textView3);
        saveButton1 = (Button) mView.findViewById(R.id.saveButton);
        builder.setView(mView);
        // dialog box
        final AlertDialog dialog = builder.create();
        dialog.show();
        // setting onClick listener
        saveButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name.setText(savePicture.getText().toString());
                dialog.dismiss();


            }
        });

    }
}



