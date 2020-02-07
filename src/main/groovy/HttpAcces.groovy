import com.google.gson.Gson
import org.apache.http.HttpEntity
import org.apache.http.HttpHeaders
import org.apache.http.NameValuePair
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpUriRequest
import org.apache.http.client.methods.RequestBuilder
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.util.EntityUtils

class HttpAcces {

    List<String> sendRequest(String url, String method, Map<String,Object> body = null) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(2000)
                .setSocketTimeout(3000)
                .build()
        HttpUriRequest request = null

        if (body){
            StringEntity entity = new StringEntity(new Gson().toJson(body), "UTF-8")
             request = RequestBuilder.create(method)
                    .setConfig(requestConfig)
                    .setUri(url)
                    .setHeader(HttpHeaders.CONTENT_TYPE, "text/html")
                    .setEntity(entity)
                    .build()
        } else {
            request = RequestBuilder.create(method)
                    .setConfig(requestConfig)
                    .setUri(url)
                    .setHeader(HttpHeaders.CONTENT_TYPE, "text/html")
                    .build()
        }

        String req = "REQUEST:" + "\n" + request.getRequestLine() + "\n" + "Headers: " +
                request.getAllHeaders() + "\n" + EntityUtils.toString() + "\n"

        //todo criar um map ou objeto para armazenar a resposta da requisicao

        HttpClientBuilder.create().build().withCloseable {httpClient ->

            httpClient.execute(request).withCloseable {response ->

                String res = "RESPONSE:" + "\n" + response.getStatusLine() + "\n" + "Headers: " +
                        response.getAllHeaders() + "\n" +
                        (response.getEntity() != null ? EntityUtils.toString(response.getEntity()) : "") + "\n"

                System.out.println(req + "\n"  + res )

                return Arrays.asList(req, res)
            }
        }
    }
}