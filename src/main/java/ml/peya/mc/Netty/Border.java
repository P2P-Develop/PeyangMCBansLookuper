package ml.peya.mc.Netty;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Border
{

    public static String post(String postUrl, String contentType, ArrayList<BodyElement> contents)
    {
        HttpURLConnection con = null;
        StringBuilder result = new StringBuilder();

        try
        {
            con = (HttpURLConnection) new URL(postUrl).openConnection();
            StringBuilder contentBuilder = new StringBuilder();
            for (BodyElement bodyElement : contents)
            {
                if (contentBuilder.toString().equals(""))
                    contentBuilder.append(bodyElement.name).append("=").append(bodyElement.value);
                else
                    contentBuilder.append("&").append(bodyElement.name).append("=").append(bodyElement.value);
            }
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", contentType);
            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
            out.write(contentBuilder.toString());
            out.close();
            con.connect();

            final int status = con.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK)
            {
                final InputStream in = con.getInputStream();
                String encoding = con.getContentEncoding();
                if(encoding == null)
                    encoding = "UTF-8";
                final InputStreamReader inReader = new InputStreamReader(in, encoding);
                final BufferedReader bufReader = new BufferedReader(inReader);
                String line;
                while((line = bufReader.readLine()) != null)
                    result.append(line);
                bufReader.close();
                inReader.close();
                in.close();
            }
            else
                System.out.println(status);
        }
        catch (Exception e1)
        {
            e1.printStackTrace();
        }
        finally
        {
            if (con != null)
                con.disconnect();
        }
        return result.toString();
    }


    public static String get(String uri)
    {
        try
        {
            HttpURLConnection http = (HttpURLConnection) new URL(uri).openConnection();
            http.setRequestMethod("GET");
            http.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
            StringBuilder str = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null)
                str.append(line);
            reader.close();
            return str.toString();
        }
        catch(Exception e)
        {
            return "";
        }
    }
}
