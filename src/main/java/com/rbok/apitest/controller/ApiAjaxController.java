package com.rbok.apitest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLEncoder;
import java.net.URL;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.io.BufferedReader;

@RestController
public class ApiAjaxController {

    @Value("${apikey.datagokr}")
    private String datagokrApiKey;

    @Value("${apikey.dataseoul}")
    private String dataseoulApiKey;

    @Value("${apikey.youthcenter}")
    private String youthcenterApiKey;

    @GetMapping("/api/getApiDataDatagokr.do")
    public String getApiDataDatagokr(ApiAjaxController apiAjaxController,
                                    @RequestParam(name = "callTp", defaultValue = "") String callTp,
                                    @RequestParam(name = "pageNo", defaultValue = "") String pageNo,
                                    @RequestParam(name = "numOfRows", defaultValue = "") String numOfRows,
                                    @RequestParam(name = "srchKeyCode", defaultValue = "") String srchKeyCode,
                                    @RequestParam(name = "searchWrd", defaultValue = "") String searchWrd) throws IOException {
        // 1. URL을 만들기 위한 StringBuilder.
        // * 공공데이터 API는 http를 제공하는데, 그러면 302 리다이렉트 오류가 발생. 이 경우 그냥 https로 수정해서 보내면 됨.
        StringBuilder urlBuilder = new StringBuilder("https://apis.data.go.kr/B554287/NationalWelfareInformations/NationalWelfarelist"); /*URL*/

        String apiKey = "";
        apiKey = datagokrApiKey;

        // 2. 오픈 API의요청 규격에 맞는 파라미터 생성, 발급받은 인증키.
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + apiKey); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("callTp","UTF-8") + "=" + URLEncoder.encode(callTp, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(pageNo, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode(numOfRows, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("srchKeyCode","UTF-8") + "=" + URLEncoder.encode(srchKeyCode, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("searchWrd","UTF-8") + "=" + URLEncoder.encode(searchWrd, "UTF-8"));

        // 3. URL 객체 생성.
        URL url = new URL(urlBuilder.toString());

        // 4. 요청하고자 하는 URL과 통신하기 위한 Connection 객체 생성.
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // 5. 통신을 위한 메소드 SET.
        conn.setRequestMethod("GET");

        // 6. 통신을 위한 Content-type SET.
        conn.setRequestProperty("Content-type", "application/json");

        // 7. 통신 응답 코드 확인.
        // System.out.println("Response code: " + conn.getResponseCode());

        // 8. 전달받은 데이터를 BufferedReader 객체로 저장.
        BufferedReader rd;

        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        // 9. 저장된 데이터를 라인별로 읽어 StringBuilder 객체로 저장.
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        // 10. 객체 해제.
        rd.close();
        conn.disconnect();
        // 11. 전달받은 데이터 확인.
        return xmlToJson(sb.toString());
    }

    @GetMapping("/api/getApiDataYouthcenter.do")
    public String getApiDataYouthcenter(ApiAjaxController apiAjaxController,
                                     @RequestParam(name = "display", defaultValue = "") String display,
                                     @RequestParam(name = "pageIndex", defaultValue = "") String pageIndex,
                                     @RequestParam(name = "query", defaultValue = "") String query,
                                     @RequestParam(name = "srchPolyBizSecd", defaultValue = "") String srchPolyBizSecd) throws IOException {
        // 1. URL을 만들기 위한 StringBuilder.
        // StringBuilder urlBuilder = new StringBuilder("https://www.youthcenter.go.kr/opi/empList.do"); /* API 구 */
        StringBuilder urlBuilder = new StringBuilder("https://www.youthcenter.go.kr/opi/youthPlcyList.do"); /* API 신 */

        String apiKey = "";
        apiKey = youthcenterApiKey;

        // 2. 오픈 API의요청 규격에 맞는 파라미터 생성, 발급받은 인증키.
        urlBuilder.append("?" + URLEncoder.encode("openApiVlak","UTF-8") + "=" + apiKey); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("display","UTF-8") + "=" + URLEncoder.encode(display, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("pageIndex","UTF-8") + "=" + URLEncoder.encode(pageIndex, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("query","UTF-8") + "=" + URLEncoder.encode(query, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("srchPolyBizSecd","UTF-8") + "=" + URLEncoder.encode(srchPolyBizSecd, "UTF-8"));

        // 3. URL 객체 생성.
        URL url = new URL(urlBuilder.toString());

        // 4. 요청하고자 하는 URL과 통신하기 위한 Connection 객체 생성.
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // 5. 통신을 위한 메소드 SET.
        conn.setRequestMethod("GET");

        // 6. 통신을 위한 Content-type SET.
        conn.setRequestProperty("Content-type", "application/json");

        // 7. 통신 응답 코드 확인.
        // System.out.println("Response code: " + conn.getResponseCode());

        // 8. 전달받은 데이터를 BufferedReader 객체로 저장.
        BufferedReader rd;

        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        // 9. 저장된 데이터를 라인별로 읽어 StringBuilder 객체로 저장.
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        // 10. 객체 해제.
        rd.close();
        conn.disconnect();
        // 11. 전달받은 데이터 확인.
        return xmlToJson(sb.toString());
    }

    @GetMapping("/api/getApiDataDetailDatagokr.do")
    public String getApiDataDetail(ApiAjaxController apiAjaxController,
                             @RequestParam(name = "callTp", defaultValue = "") String callTp,
                             @RequestParam(name = "servId", defaultValue = "") String servId) throws IOException {
        // 1. URL을 만들기 위한 StringBuilder.
        // * 공공데이터 API는 http를 제공하는데, 그러면 302 리다이렉트 오류가 발생. 이 경우 그냥 https로 수정해서 보내면 됨.
        StringBuilder urlBuilder = new StringBuilder("https://apis.data.go.kr/B554287/NationalWelfareInformations/NationalWelfaredetailed"); /*URL*/

        String apiKey = "";
        apiKey = datagokrApiKey;


        // 2. 오픈 API의요청 규격에 맞는 파라미터 생성, 발급받은 인증키.
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + apiKey); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("callTp","UTF-8") + "=" + URLEncoder.encode(callTp, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("servId","UTF-8") + "=" + URLEncoder.encode(servId, "UTF-8"));

        // 3. URL 객체 생성.
        URL url = new URL(urlBuilder.toString());

        // 4. 요청하고자 하는 URL과 통신하기 위한 Connection 객체 생성.
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // 5. 통신을 위한 메소드 SET.
        conn.setRequestMethod("GET");

        // 6. 통신을 위한 Content-type SET.
        conn.setRequestProperty("Content-type", "application/json");

        // 7. 통신 응답 코드 확인.
        // System.out.println("Response code: " + conn.getResponseCode());

        // 8. 전달받은 데이터를 BufferedReader 객체로 저장.
        BufferedReader rd;

        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        // 9. 저장된 데이터를 라인별로 읽어 StringBuilder 객체로 저장.
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        // 10. 객체 해제.
        rd.close();
        conn.disconnect();
        // 11. 전달받은 데이터 확인.
        return xmlToJson(sb.toString());
    }

    @GetMapping("/api/getApiDataDetailYouthcenter.do")
    public String getApiDataDetailYouthcenter(ApiAjaxController apiAjaxController,
                                   @RequestParam(name = "srchPolicyId", defaultValue = "") String srchPolicyId) throws IOException {
        // 1. URL을 만들기 위한 StringBuilder.
        // StringBuilder urlBuilder = new StringBuilder("https://www.youthcenter.go.kr/opi/empList.do"); /* API 구 */
        StringBuilder urlBuilder = new StringBuilder("https://www.youthcenter.go.kr/opi/youthPlcyList.do"); /* API 신 */


        String apiKey = "";
        apiKey = youthcenterApiKey;


        // 2. 오픈 API의요청 규격에 맞는 파라미터 생성, 발급받은 인증키.
        urlBuilder.append("?" + URLEncoder.encode("openApiVlak","UTF-8") + "=" + apiKey); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("srchPolicyId","UTF-8") + "=" + URLEncoder.encode(srchPolicyId, "UTF-8"));

        // 3. URL 객체 생성.
        URL url = new URL(urlBuilder.toString());

        // 4. 요청하고자 하는 URL과 통신하기 위한 Connection 객체 생성.
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // 5. 통신을 위한 메소드 SET.
        conn.setRequestMethod("GET");

        // 6. 통신을 위한 Content-type SET.
        conn.setRequestProperty("Content-type", "application/json");

        // 7. 통신 응답 코드 확인.
        // System.out.println("Response code: " + conn.getResponseCode());

        // 8. 전달받은 데이터를 BufferedReader 객체로 저장.
        BufferedReader rd;

        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        // 9. 저장된 데이터를 라인별로 읽어 StringBuilder 객체로 저장.
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        // 10. 객체 해제.
        rd.close();
        conn.disconnect();
        // 11. 전달받은 데이터 확인.
        return xmlToJson(sb.toString());
    }

    public String xmlToJson(String str) {

        String output = "";
        try{
            JSONObject jObject = XML.toJSONObject(str);
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            Object json = mapper.readValue(jObject.toString(), Object.class);
            output = mapper.writeValueAsString(json);
//          System.out.println(output);
        }catch (Exception e) {
            e.printStackTrace();
        }

        return output;
    }


}
