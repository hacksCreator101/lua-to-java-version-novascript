private static final String PASTEBIN_URL = "https://pastebin.com/raw/gzuqVtX7"; // this is the URL that has NovaScript

private void fetchScriptFromPastebin() {
    new Thread(() -> {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(PASTEBIN_URL).build();
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                String scriptContent = response.body().string();
                runOnUiThread(() -> {
                    // Show Toast with the fetched script
                    Toast.makeText(this, "Fetched Script: " + scriptContent, Toast.LENGTH_LONG).show();
                });
            } else {
                runOnUiThread(() -> {
                    // Show Toast when script fetch fails
                    Toast.makeText(this, "Failed to fetch script", Toast.LENGTH_LONG).show();
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
            // Show Toast on error
            runOnUiThread(() -> Toast.makeText(this, "Error fetching script", Toast.LENGTH_LONG).show());
        }
    }).start();
}
