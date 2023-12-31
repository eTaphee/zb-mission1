package service;

import dto.PublicWifiDto;
import dto.PublicWifiInfoResponseDto;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public final class PublicWifiInfoService {

    private final OkHttpClient client;
    private final Gson gson;
    private final String apiKey;
    private final int MAX_COUNT_PER_REQUEST = 1000;

    public PublicWifiInfoService() throws IOException {
        client = new OkHttpClient();
        gson = new Gson();

        Properties prop = new Properties();
        try (InputStream stream = PublicWifiInfoService.class.getClassLoader()
            .getResourceAsStream("application.properties")) {
            prop.load(stream);
            apiKey = prop.getProperty("apiKey");
        } catch (Exception e) {
            throw e;
        }
    }

    public List<PublicWifiDto> getAllPublicWifi() {
        List<PublicWifiDto> result = new ArrayList<>();
        int totalCount = 0;
        int start = 1;
        int end = MAX_COUNT_PER_REQUEST;

        do {
            String responseBody = getResponseBody(getRequest(start, end));
            if (responseBody == null) {
                break;
            }

            PublicWifiInfoResponseDto response = gson.fromJson(responseBody,
                PublicWifiInfoResponseDto.class);
            totalCount = response.getPublicWifiInfo().getListTotalCount(); // 중복 초기화
            result.addAll(response.getPublicWifiInfo().getPublicWifiList());
            start += MAX_COUNT_PER_REQUEST;
            end += MAX_COUNT_PER_REQUEST;
        } while (result.size() < totalCount);

        return result;
    }

    private Request getRequest(int start, int end) {
        return new Request
            .Builder()
            .url(String.format("http://openapi.seoul.go.kr:8088/%s/json/TbPublicWifiInfo/%d/%d",
                apiKey, start, end))
            .build();
    }

    private String getResponseBody(Request request) {
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            }
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        PublicWifiInfoService service = new PublicWifiInfoService();
        List<PublicWifiDto> list = service.getAllPublicWifi();
        System.out.println(list);
    }
}
