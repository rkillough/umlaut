package umluat.com.app;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Button;


/**
 * Created by Kyle on 11/03/2015.
 *
 * This class is currently an inner class in MainActivity.java, should be separated out to this class when I figure out how to manipulate an instance of an Activity from another class
 */
class ApiRequest extends AsyncTask <Void, Void, String> {
    protected String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException {
        InputStream in = entity.getContent();

        StringBuffer out = new StringBuffer();
        int n = 1;
        while (n>0) {
            byte[] b = new byte[4096];
            n =  in.read(b);

            if (n>0) {
                out.append(new String(b, 0, n));
            }
        }

        return out.toString();
    }

    @Override
    protected String doInBackground(Void... params) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpGet httpGet = new HttpGet("Request location...");
        String text = null;
        try {
            HttpResponse response = httpClient.execute(httpGet, localContext);
            HttpEntity entity = response.getEntity();

            text = getASCIIContentFromEntity(entity);

        } catch (Exception e) {
            return e.getLocalizedMessage();
        }

        return text;
    }

    protected void onPostExecute(String results) {

        if (results!=null) {
            EditText et = (EditText)findViewById(R.id.my_edit);

            et.setText(results);
        }

        Button b = (Button)findViewById(R.id.my_button);


        b.setClickable(true);
    }

}
