package com.djx.poem.manager;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.djx.poem.util.IpRegionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dongjingxi
 * @date 2023/5/6
 */
@Slf4j
@Component
public class WeatherManager {


    final RestTemplate restTemplate;

    static final String URL_WEATHER = "https://restapi.amap.com/v3/weather/weatherInfo?";
    static final String URL_GEO = "https://restapi.amap.com/v3/geocode/geo?";

    static final String KEY = "fc960581cbd7670ebd6cc458d60a0a29";


    final ConcurrentHashMap<String, String> addressAndCode = new ConcurrentHashMap<>(16);

    public WeatherManager(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * 获取实时天气
     */
    public String getRealWeather(String ip) {
        return getWeather(getCityCode(ip), "base");
    }


    /**
     * 获取未来天气预报
     */
    public String getFutureWeather(String ip) {
        return getWeather(getCityCode(ip), "all");
    }


    /**
     * 从ip信息中获取地区编号
     */
    private String getCityCode(String ip) {

        // 解析ip 为地区信息
        String address = IpRegionUtil.region(ip);

        // 解析地区信息为地区编号
        return getCityCodeCache(address);
    }

    /**
     * 获取天气
     *
     * @param city      城市编码
     * @param baseOrAll 查询的是实时还是预测天气
     * @return 天气信息
     */
    private String getWeather(String city, String baseOrAll) {
        String url = URL_WEATHER + "city=" + city + "&key=" + KEY + "&extensions=" + baseOrAll;
        return sendHttpRequest(url);
    }


    /**
     * 查询缓存获取城市编码
     */
    private String getCityCodeCache(String address) {

        String code = addressAndCode.get(address);

        if (StringUtils.isBlank(code)) {
            code = getCityCodeRemote(address);
            addressAndCode.put(address, code);
        }
        return code;
    }

    /**
     * 调用接口获取城市编码
     */
    private String getCityCodeRemote(String address) {
        String url = URL_GEO + "address=" + address + "&key=" + KEY;
        String response = sendHttpRequest(url);

        if (response == null) {
            return null;
        }

        JSONObject jsonObject = JSONUtil.parseObj(response);
        String status = jsonObject.getStr("status");
        if (!Objects.equals(status, "1")) {
            return null;
        }
        return jsonObject.getJSONArray("geocodes").getJSONObject(0).getStr("adcode");
    }

    /**
     * 发送一个http请求
     */
    private String sendHttpRequest(String url) {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        String body = responseEntity.getBody();
        log.debug("body: {}", body);
        return body;
    }


}
