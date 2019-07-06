package cn.ivan.client.util;

import com.jzj.jslauncher.entity.PhotoUpload;
import org.springframework.web.client.RestTemplate;

public class TestUploadPhoto {


    public static void main(String[] args) {

        System.out.println(new PhotoUpload().toJsonString());


    }
}
