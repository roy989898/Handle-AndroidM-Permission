package pom.tryandroidpermissionlibrary;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;

import com.mukesh.permissions.AppPermissions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bt_takephoto)
    Button btTakephoto;
    @BindView(R.id.activity_main)
    LinearLayout activityMain;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private AppPermissions runtimePermission;
    private int mRequestCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        runtimePermission = new AppPermissions(this);
        runtimePermission.requestPermission(Manifest.permission.CAMERA, mRequestCode);
    }

    @OnClick(R.id.bt_takephoto)
    public void onClick() {
        if (runtimePermission.hasPermission(Manifest.permission.CAMERA))
            dispatchTakePictureIntent();
        else
            runtimePermission.requestPermission(Manifest.permission.CAMERA, mRequestCode);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == mRequestCode) { //The request code you passed along with the request.
            //grantResults holds a list of all the results for the permissions requested.
            for (int grantResult : grantResults) {
                if (grantResult == PackageManager.PERMISSION_DENIED) {
                    Log.d("PermissionResult=>", "Denied");
                    return;
                }
            }
            Log.d("PermissionResult=>", "All Permissions Granted");
        }
    }
}
