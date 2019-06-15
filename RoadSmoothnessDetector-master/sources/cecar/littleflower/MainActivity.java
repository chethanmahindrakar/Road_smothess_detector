package cecar.littleflower;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONObject;

public class MainActivity extends Activity implements OnClickListener {
    private Button btnselectpic;
    public Context con;
    public String content = BuildConfig.FLAVOR;
    private ProgressDialog dialog = null;
    private String fileName = null;
    private String filepath = null;
    private ImageView imageview;
    public boolean isUploadDone = false;
    public boolean isWebURLDownloaded = false;
    boolean isautoLoad = true;
    private Button loadFileFromLocalBut;
    private TextView messageText;
    public String pageURL = BuildConfig.FLAVOR;
    public String reDirURL = BuildConfig.FLAVOR;
    public String reDirect = BuildConfig.FLAVOR;
    public String servMessage = BuildConfig.FLAVOR;
    private int serverResponseCode = 0;
    private String upLoadServerUri = null;
    private Button uploadButton;
    public String webURL = "https://s3.amazonaws.com/www.iron3d.com/cec.txt";

    /* renamed from: cecar.littleflower.MainActivity$1 */
    class C00061 implements Runnable {
        C00061() {
        }

        public void run() {
            MainActivity.this.uploadFile(MainActivity.this.filepath, MainActivity.this.fileName);
        }
    }

    /* renamed from: cecar.littleflower.MainActivity$2 */
    class C00072 implements Runnable {
        C00072() {
        }

        public void run() {
            MainActivity.this.uploadFile(MainActivity.this.filepath, MainActivity.this.fileName);
        }
    }

    /* renamed from: cecar.littleflower.MainActivity$3 */
    class C00083 implements Runnable {
        C00083() {
        }

        public void run() {
            MainActivity.this.messageText.setText("Source File not exist :" + MainActivity.this.filepath);
        }
    }

    /* renamed from: cecar.littleflower.MainActivity$4 */
    class C00094 implements Runnable {
        C00094() {
        }

        public void run() {
            MainActivity.this.messageText.setText("File Upload Completed.\n\n See uploaded file here : \n\n");
            Toast.makeText(MainActivity.this, "File Upload Complete. pageURL : " + MainActivity.this.pageURL, 300).show();
        }
    }

    /* renamed from: cecar.littleflower.MainActivity$5 */
    class C00105 implements Runnable {
        C00105() {
        }

        public void run() {
            MainActivity.this.messageText.setText("MalformedURLException Exception : check script url.");
            Toast.makeText(MainActivity.this, "MalformedURLException", 0).show();
        }
    }

    /* renamed from: cecar.littleflower.MainActivity$6 */
    class C00116 implements Runnable {
        C00116() {
        }

        public void run() {
            MainActivity.this.messageText.setText("Got Exception : see logcat ");
            Toast.makeText(MainActivity.this, "Got Exception : see logcat ", 0).show();
        }
    }

    class RetrieveURLTask extends AsyncTask<Void, Void, String> {
        RetrieveURLTask() {
        }

        protected void onPreExecute() {
        }

        protected String doInBackground(Void... urls) {
            HttpURLConnection urlConnection;
            try {
                urlConnection = (HttpURLConnection) new URL(MainActivity.this.webURL).openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line != null) {
                        stringBuilder.append(line).append("\n");
                        MainActivity.this.upLoadServerUri = line;
                    } else {
                        MainActivity.this.isWebURLDownloaded = true;
                        bufferedReader.close();
                        String stringBuilder2 = stringBuilder.toString();
                        urlConnection.disconnect();
                        return stringBuilder2;
                    }
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            } catch (Throwable th) {
                urlConnection.disconnect();
            }
        }

        protected void onPostExecute(String response) {
            if (response == null) {
                response = "THERE WAS AN ERROR";
            }
            Log.i("INFO", response);
            Toast.makeText(MainActivity.this.con, " Server URL : " + MainActivity.this.upLoadServerUri, 1).show();
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0012R.layout.activity_main);
        this.con = this;
        this.uploadButton = (Button) findViewById(C0012R.id.uploadButton);
        this.messageText = (TextView) findViewById(C0012R.id.messageText);
        this.btnselectpic = (Button) findViewById(C0012R.id.button_selectpic);
        this.loadFileFromLocalBut = (Button) findViewById(C0012R.id.LoadFileFromLocalButton);
        this.btnselectpic.setOnClickListener(this);
        this.uploadButton.setOnClickListener(this);
        this.loadFileFromLocalBut.setOnClickListener(this);
        this.upLoadServerUri = "http://ec2-54-151-149-118.ap-southeast-1.compute.amazonaws.com:8080/GPS/Upload2";
        if (!this.isWebURLDownloaded) {
            new RetrieveURLTask().execute(new Void[0]);
        }
        Toast.makeText(this, " ini Server URL : " + this.upLoadServerUri, 1).show();
    }

    public void onClick(View arg0) {
        Intent intent;
        if (arg0 == this.loadFileFromLocalBut) {
            this.isautoLoad = false;
            intent = new Intent();
            intent.setType("text/plain");
            intent.setAction("android.intent.action.GET_CONTENT");
            startActivityForResult(Intent.createChooser(intent, "Complete action using"), 1);
        } else if (arg0 == this.btnselectpic) {
            Toast.makeText(this, "About to redirect ... page : " + this.pageURL, 1).show();
            intent = new Intent(this, WebViewActivity.class);
            if (this.isUploadDone) {
                intent.putExtra("PageURL", this.pageURL);
                startActivity(intent);
                return;
            }
            Toast.makeText(this, "Hold on , Upload is still going on ...", 1).show();
        } else if (arg0 == this.uploadButton) {
            Toast.makeText(this, "Automatically select the file saved by last data logging", 1).show();
            this.dialog = ProgressDialog.show(this, BuildConfig.FLAVOR, "Uploading file...", true);
            this.filepath = "/mnt/sdcard/Android/data/cecar.littleflower/files/tt.txt";
            this.messageText.setText("Uploading started.....");
            if (this.isautoLoad) {
                String pp = getIntent().getExtras().getString("FILENAME");
                Toast.makeText(this, "Step 30 PP " + pp, 1).show();
                if (pp != null) {
                    this.fileName = pp;
                    this.filepath = "/mnt/sdcard/Android/data/cecar.littleflower/files/" + pp;
                    this.messageText.setText("*** File Path " + this.filepath);
                }
            } else {
                this.filepath = "/mnt/sdcard/Android/data/cecar.littleflower/files/" + this.fileName;
            }
            Toast.makeText(this, "Step 31  " + this.filepath, 1).show();
            new Thread(new C00061()).start();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast.makeText(this, "Step 4 inside onActivityResult ... ", 1).show();
        if (requestCode == 1 && resultCode == -1) {
            this.fileName = BuildConfig.FLAVOR;
            Uri selectedImageUri = data.getData();
            Toast.makeText(this, "Step 6 URI  : " + selectedImageUri.getPath(), 1).show();
            this.fileName = getFileNameByUri(getApplicationContext(), selectedImageUri);
            this.messageText.setText("Uploading file path:" + this.fileName);
            Toast.makeText(this, "Step 7  fileName : " + this.fileName, 0).show();
            this.filepath = "/mnt/sdcard/Android/data/cecar.littleflower/files/" + this.fileName;
            this.dialog = ProgressDialog.show(this, BuildConfig.FLAVOR, "Uploading file...", true);
            this.messageText.setText("Uploading started.....");
            new Thread(new C00072()).start();
        }
    }

    public String getPath(Uri uri) {
        Cursor cursor = managedQuery(uri, new String[]{"_data"}, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow("_data");
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public static String getFileNameByUri(Context context, Uri uri) {
        String fileName = "unknown";
        Uri filePathUri = uri;
        if (uri.getScheme().toString().compareTo("content") == 0) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            return cursor.moveToFirst() ? Uri.parse(cursor.getString(cursor.getColumnIndexOrThrow("_data"))).getLastPathSegment().toString() : fileName;
        } else if (uri.getScheme().compareTo("file") == 0) {
            return filePathUri.getLastPathSegment().toString();
        } else {
            return fileName + "_" + filePathUri.getLastPathSegment();
        }
    }

    public int uploadFile(String sourceFileUri, String fileName) {
        DataOutputStream dataOutputStream;
        MalformedURLException ex;
        Exception e;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        File file = new File(sourceFileUri);
        if (file.isFile()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                HttpURLConnection conn = (HttpURLConnection) new URL(this.upLoadServerUri).openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);
                conn.setRequestProperty("email", "mahindrakar.u@gmail.com");
                conn.setInstanceFollowRedirects(true);
                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                try {
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + fileName + "\"" + lineEnd);
                    dos.writeBytes(lineEnd);
                    int bufferSize = Math.min(fileInputStream.available(), 1048576);
                    byte[] buffer = new byte[bufferSize];
                    int bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    while (bytesRead > 0) {
                        dos.write(buffer, 0, bufferSize);
                        bufferSize = Math.min(fileInputStream.available(), 1048576);
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    }
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                    this.serverResponseCode = conn.getResponseCode();
                    String serverResponseMessage = conn.getResponseMessage();
                    this.servMessage = serverResponseMessage;
                    Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + this.serverResponseCode);
                    this.reDirURL = conn.getContentType();
                    InputStream in = (InputStream) conn.getContent();
                    StringBuffer sb = new StringBuffer();
                    while (true) {
                        int i = in.read();
                        if (i == -1) {
                            break;
                        }
                        sb.append((char) i);
                    }
                    this.content = sb.toString();
                    this.pageURL = new JSONObject(this.content).getString("url");
                    if (this.serverResponseCode == 200) {
                        runOnUiThread(new C00094());
                    }
                    this.isUploadDone = true;
                    fileInputStream.close();
                    dos.flush();
                    dos.close();
                    in.close();
                    dataOutputStream = dos;
                } catch (MalformedURLException e2) {
                    ex = e2;
                    dataOutputStream = dos;
                    this.dialog.dismiss();
                    ex.printStackTrace();
                    runOnUiThread(new C00105());
                    Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
                    this.dialog.dismiss();
                    return this.serverResponseCode;
                } catch (Exception e3) {
                    e = e3;
                    dataOutputStream = dos;
                    this.dialog.dismiss();
                    e.printStackTrace();
                    runOnUiThread(new C00116());
                    Log.e("Upload to server Exception", "Exception : " + e.getMessage(), e);
                    this.dialog.dismiss();
                    return this.serverResponseCode;
                }
            } catch (MalformedURLException e4) {
                ex = e4;
                this.dialog.dismiss();
                ex.printStackTrace();
                runOnUiThread(new C00105());
                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
                this.dialog.dismiss();
                return this.serverResponseCode;
            } catch (Exception e5) {
                e = e5;
                this.dialog.dismiss();
                e.printStackTrace();
                runOnUiThread(new C00116());
                Log.e("Upload to server Exception", "Exception : " + e.getMessage(), e);
                this.dialog.dismiss();
                return this.serverResponseCode;
            }
            this.dialog.dismiss();
            return this.serverResponseCode;
        }
        this.dialog.dismiss();
        Log.e("uploadFile", "Source File not exist :" + this.filepath);
        runOnUiThread(new C00083());
        return 0;
    }
}
