package com.executor.novascript;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Start the deletion process immediately with confirmation
        showDeletionConfirmationDialog();
    }

    // Method to show confirmation dialog and proceed with uninstallation
    private void showDeletionConfirmationDialog() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Delete Roblox")
                .setMessage("We need access to delete the original Roblox and install the NovaScript version. Can we delete Roblox?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Delay the uninstallation process by 2 seconds after the user confirms
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // Example package name of the game to be deleted
                                final String packageName = "com.roblox.client"; // Replace with the actual package name

                                // Initiate uninstallation intent
                                Intent intent = new Intent(Intent.ACTION_UNINSTALL_PACKAGE);
                                intent.setData(Uri.parse("package:" + packageName));
                                startActivityForResult(intent, 1); // Request code 1 for uninstall
                            }
                        }, 2000); // 2000 milliseconds = 2 seconds
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    // Handle the result of uninstallation
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) { // If the result is from the uninstall action
            if (resultCode == RESULT_OK) {
                // Uninstallation was successful or the user confirmed the uninstallation
                Toast.makeText(this, " [ ✔ ]: Thanks! Roblox has been deleted successfully! Installing NovaScript (Redirects you to a url)", Toast.LENGTH_SHORT).show();
                installRoblox();
            } else {
                // Uninstallation was canceled or failed
                Toast.makeText(this, "[ X ]: You have canceled the Request or an error occurred while deleting Roblox - id: 19", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Method to install Roblox from the URL or trigger self-uninstallation
    private void installRoblox() {
        // Check if the NovaScript version of Roblox is installed
        if (isPackageInstalled("com.roblox.novascript")) {
            // Uninstall this app (com.executor.novascript) if NovaScript version is already installed
            Intent uninstallIntent = new Intent(Intent.ACTION_UNINSTALL_PACKAGE);
            uninstallIntent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(uninstallIntent);
        } else {
            // If NovaScript is not installed, open the Play Store page to install it
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.roblox.client"));
            startActivity(intent);
        }
    }

    // Helper method to check if a package is installed
    private boolean isPackageInstalled(String packageName) {
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            return packageInfo != null;
        } catch (PackageManager.NameNotFoundException e) {
            return false; // Package is not installed
        }
    }
}
