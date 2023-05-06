package com.djx.poem.util;

import org.lionsoul.ip2region.xdb.Searcher;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * @author dongjingxi
 * @date 2023/5/6
 */
public class IpRegionUtil {

    public static void main(String[] args) {
        System.out.println(IpRegionUtil.evaluate("171.110.176.133"));
        System.out.println(IpRegionUtil.region("171.110.176.133"));

    }

    static Searcher searcher = null;

    static {
        try (InputStream resourceAsStream = IpRegionUtil.class.getResourceAsStream("/ip2region.xdb")) {

            if (resourceAsStream != null) {
                final byte[] buff = new byte[15000000];
                resourceAsStream.read(buff);
                searcher = Searcher.newWithBuffer(buff);
            }
        } catch (IOException e) {
            System.out.println("failed to create searcher with ");
        }
    }


    static public String evaluate(String ip) {
        //根据ip获取地址
        try {

            if (searcher == null) {
                return "ip解析数据库加载错误";
            }
            // 2、查询
            return searcher.search(ip);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return "此ip地址在离线数据库中不存在";
        }
    }


    /**
     * 获取国内省市区信息
     */
    static public String region(String ip) {
        String region = evaluate(ip);
        region = region.replace("0", "");

        String[] split = region.split("\\|");

        if (!Objects.equals(split[0], "中国")) {
            // todo 抛出异常
            throw new RuntimeException("此ip地址不在中国");
        }

        return split[2] + split[3] + split[1];
    }


}
