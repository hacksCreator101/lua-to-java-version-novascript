package com.novascript.executor;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String ROBLOX_PACKAGE_NAME = "com.roblox.client";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Step 1: Check if Roblox is installed
        if (isAppInstalled(ROBLOX_PACKAGE_NAME)) {
            // Step 2: Prompt to uninstall Roblox if it's already installed
            new AlertDialog.Builder(this)
                .setTitle("Replace Roblox")
                .setMessage("NovaScript asks: Can we delete the original roblox and install the executor?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        uninstallRoblox();  // Step 3: Trigger uninstallation
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
        } else {
            // Step 4: If not installed, prompt for new installation directly
            installRoblox();
        }
    }

    // Check if the app is installed
    private boolean isAppInstalled(String packageName) {
        try {
            getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Uninstall the Roblox app
    private void uninstallRoblox() {
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE);
        uninstallIntent.setData(Uri.parse("package:" + ROBLOX_PACKAGE_NAME));
        startActivityForResult(uninstallIntent, 1);  // Use startActivityForResult to handle post-uninstall actions
    }

    // Redirect to install Roblox from Play Store or a specific URL
    private void installRoblox() {
        try {
            Intent playStoreIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + ROBLOX_PACKAGE_NAME));
            startActivity(playStoreIntent);
        } catch (ActivityNotFoundException e) {
            // If Play Store is not available, open in browser
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + ROBLOX_PACKAGE_NAME));
            startActivity(browserIntent);
        }
    }

    // Handle post-uninstall actions
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        // After uninstallation, prompt for installation
        if (requestCode == 1) {
            if (!isAppInstalled(ROBLOX_PACKAGE_NAME)) {
                // App is uninstalled, prompt to install the new version
                installRoblox();
            } else {
                Toast.makeText(this, "Uninstallation failed or canceled.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

