package com.djx.poem.controller;

import com.djx.poem.manager.WeatherManager;
import com.djx.poem.model.entity.PoemInfoEntity;
import com.djx.poem.repository.PoemInfoRepository;
import com.djx.poem.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

/**
 * @author dongjingxi
 * @date 2023/5/6
 */
@Slf4j
@RestController
@RequestMapping("/poem")
public class PoemController {


    private final PoemInfoRepository poemInfoRepository;
    private final WeatherManager weatherManager;
    private final Random random = new Random();

    @Autowired
    public PoemController(PoemInfoRepository poemInfoRepository, WeatherManager weatherManager) {
        this.poemInfoRepository = poemInfoRepository;
        this.weatherManager = weatherManager;
    }


    /**
     * 随机获取一条poem数据
     */
    @GetMapping("/random")
    public String random() {

        int id = random.nextInt(340812);

        log.info("id: {}", id);

        PoemInfoEntity poemInfoEntity = poemInfoRepository.findById(id).orElse(new PoemInfoEntity());

        return poemInfoEntity.toString();
    }


    /**
     * 获取实时天气
     */
    @GetMapping("/weather/real")
    public String realWeather(HttpServletRequest request) {
        String ipAddr = IpUtil.getIpAddr(request);
        return weatherManager.getRealWeather("58.212.134.219");
    }


    /**
     * 获取天气预报
     */
    @GetMapping("/weather/forecast")
    public String forecastWeather(HttpServletRequest request) {
        String ipAddr = IpUtil.getIpAddr(request);
        return weatherManager.getFutureWeather("58.212.134.219");
    }


}
