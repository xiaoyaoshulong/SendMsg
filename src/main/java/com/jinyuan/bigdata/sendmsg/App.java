package com.jinyuan.bigdata.sendmsg;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.json.*;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        SendMsg sg = new SendMsg();
        /*
        String strJsonToken = sg.getToken();

        JSONObject js = new JSONObject(strJsonToken);

        String strToken = js.getString("token");
        System.out.println( strToken );
        */
        sg.getResultByUri( sg.m_strJsonToken );
    }
}


class SendMsg {

    public SendMsg() {
    }

    public static String m_strJsonToken = "";

    static {

        try {
            String url = "http://10.32.71.162:12900/users/admin/tokens/icinga?pretty=true";
            //String url = "http://10.32.71.162:12900/users/chenyl/tokens/icinga?pretty=true";

            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            String userpass = "admin" + ":" + "log4jyzq";
            //String userpass = "chenyl" + ":" + "Yaron518";
            String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes("UTF-8"));
            System.out.println(basicAuth);
            conn.setRequestProperty("Authorization", basicAuth);

            InputStream inputStream = conn.getInputStream();
            byte[] a = new byte[1000];
            inputStream.read(a);
            //new InputStreamReader(conn.getInputStream());

            System.out.println(new String(a));
            String strJsonToken = new String(a);
            JSONObject js = new JSONObject(strJsonToken);

            String Token = js.getString("token");
            System.out.println( Token );
            m_strJsonToken = Token;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public  String getToken() {
        try {
            String url = "http://10.32.71.162:12900/users/admin/tokens/icinga?pretty=true";
            //String url = "http://10.32.71.162:12900/users/chenyl/tokens/icinga?pretty=true";

            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            String userpass = "admin" + ":" + "log4jyzq";
            //String userpass = "chenyl" + ":" + "Yaron518";
            String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes("UTF-8"));
            System.out.println(basicAuth);
            conn.setRequestProperty("Authorization", basicAuth);

            InputStream inputStream = conn.getInputStream();
            byte[] a = new byte[1000];
            inputStream.read(a);
            //new InputStreamReader(conn.getInputStream());

            System.out.println(new String(a));
            String strToken = new String(a);
            return strToken;
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public void getResultByUri(String strToken) {
        try {
            //String strUrl = "http://10.32.71.163:12900/search/universal/absolute?query=_exists_%3ARealFrom&from=2017-11-18T00%3A00%3A00.000Z&to=2017-11-18T23%3A59%3A59.999Z";
            //               http://10.32.71.162:12900/search/universal/absolute?query=*&from=2018-01-01T00%253A00%253A00.000Z&to=2018-03-15T00%253A00%253A00.000Z
            //              http://10.32.71.162:12900/search/universal/absolute?query=_exists_%3ARealFrom%20AND%20RealFrom%3A%22yanjj%40jyzq.cn%22&from=2017-01-01T00%3A00%3A00.000Z&to=2018-01-01T00%3A00%3A00.000Z
            //String strReqUrl = String.format("http://%s:token@%s", strToken, strUrl);

            //strReqUrl = "http://14a3cgnjhhshptrvo5hol2fh459nn231k3rs84te6egbulms8omd:token@10.32.71.162:12900/search/universal/absolute?query=*&from=2018-01-01T00%3A00%3A00.000Z&to=2018-03-15T00%3A00%3A00.000Z&limit=50";
            //strReqUrl = "http://10.32.71.162:12900/search/universal/absolute?query=*&from=2018-01-01T00%3A00%3A00.000Z&to=2018-03-15T00%3A00%3A00.000Z&limit=50";
            String strUrl = "https://10.32.71.128/server/search/universal/absolute?query=_exists_%3ARealFrom&from=2017-11-18T00%3A00%3A00.000Z&to=2018-11-18T23%3A59%3A59.999Z";

            System.out.printf("strReqUrl:%s \r\n", strUrl);
            URL url = new URL(strUrl);


            SSLSocketFactory oldSocketFactory = null;
            HostnameVerifier oldHostnameVerifier = null;

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Accept", "application/json");

            String userpass = strToken + ":" + "token";
            String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes("UTF-8"));

            httpURLConnection.setRequestProperty("Authorization", basicAuth);

            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) httpURLConnection;
            oldSocketFactory = trustAllHosts(httpsURLConnection);
            oldHostnameVerifier = httpsURLConnection.getHostnameVerifier();
            httpsURLConnection.setHostnameVerifier(DO_NOT_VERIFY);




            /*
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            StringBuffer buffer = new StringBuffer();
            String line = " ";
            while ((line = in.readLine()) != null){
                 buffer.append(line);
            }
             */

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuffer strRes = new StringBuffer();
            String line = " ";
            while ((line = reader.readLine()) != null) {
                strRes.append(line);
            }
            //System.out.printf("%s \n",strRes);

 /*           JsonParserparser = new JsonParser();

            JsonObjectobj = (JsonObject) parser.parse(new FileReader("test.json"));

            System.out.println("cat=" +obj.get("cat").toString());

            System.out.println("pop=" +obj.get("pop").toString());

            JsonArrayarray = obj.get("languages").getAsJsonArray();

            for(int i = 0;i<array.size();i++)

            {

                System.out.println("---------");

                JsonObjectsub Object = array.get(i).getAsJsonObject();

                System.out.println("id="+subObject.get("id"));

                System.out.println("name="+subObject.get("name"));

                System.out.println("ide="+subObject.get("ide"));

            }*/
            JSONObject js = new JSONObject(strRes.toString());

            JSONArray array = js.getJSONArray("messages");
            //String strMessages = js.getString("messages");
            //System.out.println( strMessages );
            String var_time;
            String var_sendfrom;
            String var_sendto;
            String var_sendtoall;
            String var_flag;
            String var_id;

            for (int i = 0; i < array.length(); i++)

            {

                System.out.println("---------");

                JSONObject subObject = array.getJSONObject(i);

                JSONObject subObject_message = subObject.getJSONObject("message");

                System.out.printf("%s \r\n", subObject_message, toString());

                var_time = subObject_message.getString("timestamp");
                var_sendfrom = subObject_message.getString("RealFrom").toString();
                var_sendto = subObject_message.getString("RealTo").toString();
                var_sendtoall = subObject_message.getString("To").toString();
                var_flag = subObject_message.getString("Flag").toString();
                var_id = subObject_message.getString("id").toString();

                System.out.printf("%s \r\n", var_time);
                System.out.printf("%s \r\n", var_sendfrom);
                System.out.printf("%s \r\n", var_sendto);

            }
            /*
            InputStream inputStream =  httpURLConnection.getInputStream();
            byte[] a = new byte[100000];
            inputStream.read(a);
            String strRes = new String(a);
            System.out.println(strRes);
            */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 覆盖java默认的证书验证
     */
    private static final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[]{};
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }
    }};

    /**
     * 设置不验证主机
     */
    private static final HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    /**
     * 信任所有
     * @param connection
     * @return
     */
    private static SSLSocketFactory trustAllHosts(HttpsURLConnection connection) {
        SSLSocketFactory oldFactory = connection.getSSLSocketFactory();
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            SSLSocketFactory newFactory = sc.getSocketFactory();
            connection.setSSLSocketFactory(newFactory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return oldFactory;
    }
    /*
    public void getResult(String strToken){

        try{
            System.out.println( strToken );

            String userpass = strToken + ":" + "token";
            String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes("UTF-8"));

            HttpClient client = new HttpClient();

            GetMethod get = new GetMethod("http://10.32.71.163:12900/search/universal/absolute");
            get.addRequestHeader("Content-Type", "application/json:charset=gbk");//在头文件中设置转码
            //post.addRequestHeader("Authorization:Basic", "1m53lmse9sqcm91ebr98ir2187dnl9pqo62pb3u1p8s94g1r8sp4");
            get.addRequestHeader("Authorization:Basic", basicAuth);
            NameValuePair[] data = {
                    //new NameValuePair("query", "source_input%5a34c1813a0a6f2e11c6603d"),
                    new NameValuePair("query", "*"),
                    new NameValuePair("from", "2017-01-01T00:00:00.000Z"),
                    new NameValuePair("to", "2018-03-13T00:00:00.000Z"),
                    //new NameValuePair("to", "2017-11-18%2013%3A33%3A15"),
                    new NameValuePair("limit", "50")
                    //new NameValuePair("","")

            };
            // post.setRequestBody(data);
            get.setQueryString(data);

            client.executeMethod(get);
            Header[] headers = get.getResponseHeaders();
            int statusCode = get.getStatusCode();
            System.out.println("statusCode:" + statusCode);
            for (Header h : headers) {
                System.out.println(h.toString());
            }
            String result = new String(get.getResponseBodyAsString());
            get.releaseConnection();
            System.out.println("result =" + result);
            //return result.trim();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    */
     /*
    public void getResultGet(String strToken){

        try{
            String strUrl = "10.32.71.162:12900/search/universal/absolute?query=*&from=2018-01-01T00%3A00%3A00.000Z&to=2018-03-15T00%3A00%3A00.000Z&limit=50";
            String strReqUrl = String.format("http://%s:token@%s",strToken, strUrl);
            System.out.printf("strReqUrl:%s",strReqUrl);

            //String url = "http://10.32.71.162:12900/users/admin/tokens/icinga?pretty=true";
            //String url = "http://10.32.71.162:12900/users/chenyl/tokens/icinga?pretty=true";

            URL obj = new URL(strReqUrl);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");

            OutputStream outputStream = conn.getOutputStream();
            outputStream.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String output;
            System.out.print("addResource result is : ");
            while ((output = reader.readLine()) != null) {
                System.out.print(output);
            }
            System.out.print("\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
        */
/*
        try{

            HttpClient httpClient = new DefaultHttpClient();

            String strUrl = "10.32.71.162:12900/search/universal/absolute?query=*&from=2018-01-01T00%3A00%3A00.000Z&to=2018-03-15T00%3A00%3A00.000Z&limit=50";
            String strReqUrl = String.format("http://%s:token@%s",strToken, strUrl);
            System.out.printf("strReqUrl:%s \r\n",strReqUrl);

            HttpGet httpGet = new HttpGet(strReqUrl);
*/
    //GetMethod get = new GetMethod(strReqUrl);
    //get.addRequestHeader("Content-Type", "application/json:charset=gbk");//在头文件中设置转码
    //post.addRequestHeader("Authorization:Basic", "1m53lmse9sqcm91ebr98ir2187dnl9pqo62pb3u1p8s94g1r8sp4");
    //get.addRequestHeader("Authorization:Basic", strToken);
            /*
            NameValuePair[] data = {
                    //new NameValuePair("query", "source_input%5a34c1813a0a6f2e11c6603d"),
                    new NameValuePair("query", "*"),
                    new NameValuePair("from", "2018-01-01T00:00:00.000Z"),
                    new NameValuePair("to", "2018-03-13T00:00:00.000Z"),
                    //new NameValuePair("to", "2017-11-18%2013%3A33%3A15"),
                    new NameValuePair("limit", "50")
                    //new NameValuePair("","")

            };
            */
    // post.setRequestBody(data);
    //get.setQueryString(data);

    /*
                HttpResponse response = httpClient.execute(httpGet);

                if (response.getStatusLine().getStatusCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : "
                            + response.getStatusLine().getStatusCode());
                }

                String json = EntityUtils.toString(response.getEntity());
                System.out.print("addResource result is : " + json + "\n");

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    */
    /*
    public static HttpClient getHttpClient() {
        HttpParams params = new BasicHttpParams();
        params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        int CONNECTION_TIMEOUT = 3000;
        try {
            CONNECTION_TIMEOUT = 3000;//单位毫秒
        } catch (Exception ex) {
        }
        params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT); // 3000ms

        try {
            String userpass = m_strJsonToken + ":" + "token";
            String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes("UTF-8"));
            params.setParameter("Authorization", basicAuth);
        } catch (Exception ex) {
        }

        return new DefaultHttpClient(cm, params);

    }

    public void getResultByGet(String strToken) {
        try {

            HttpClient httpClient = getHttpClient();
            //HttpGet getRequest = new HttpGet("http://10.32.71.162:12900/search/universal/absolute");
            //String strUrl = "http://10.32.71.163:12900/search/universal/absolute?query=_exists_%3ARealFrom&from=2017-11-18T00%3A00%3A00.000Z&to=2017-11-18T23%3A59%3A59.999Z";
            //String strUrl = "http://10.32.71.163:12900/search/universal/absolute?query=_exists_%3ARealFrom&from=2017-11-18T00%3A00%3A00.000Z&to=2017-11-18T23%3A59%3A59.999Z&limit=50";
            String strUrl = "https://10.32.71.128/search/universal/absolute?query=_exists_%3ARealFrom&from=2017-11-18T00%3A00%3A00.000Z&to=2017-11-18T23%3A59%3A59.999Z&limit=50";

            HttpGet getRequest = new HttpGet(strUrl);
            //getRequest.addHeader("Content-Type", "application/json:charset=gbk");
            //getRequest.addHeader("Authorization:Basic", strToken);

            HttpResponse response = httpClient.execute(getRequest);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            httpClient.getConnectionManager().shutdown();

        } catch (ClientProtocolException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public static void release() {
        if (cm != null) {
            cm.shutdown();
        }
    }
    */
/*
    public void getResultByPost(String strToken){

    }


    public void  getResult2(String strToken){
        try {

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost("http://10.32.71.162:12900/search/universal/absolute");
            postRequest.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=gbk");//在头文件中设置转码
            postRequest.addHeader("Authorization:Basic", strToken);
            StringEntity input = new StringEntity("{\"query\":\"source_input%3A59bb95e59a0dfb26a4064f79\",\"from\":\"1970-01-01T00:00:00.000Z\",\"to\":\"2018-01-01T00:00:00.000Z\"}");
            input.setContentType("application/json");
            postRequest.setEntity(input);

            HttpResponse response = httpClient.execute(postRequest);

            if (response.getStatusLine().getStatusCode() != 201) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            httpClient.getConnectionManager().shutdown();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
*/
}