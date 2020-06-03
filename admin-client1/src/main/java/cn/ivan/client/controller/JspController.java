package cn.ivan.client.controller;

import cn.ivan.client.config.Log;
import com.sun.javafx.collections.MappingChange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author : yanqi
 * @version : 1.0
 * 2018/11/15
 */
@Slf4j
@Controller
public class JspController {


    @GetMapping("/testf")
    @ResponseBody
    public String testFutter(String name){
        if(name.equals("1234")){
            throw new RuntimeException("=================name 错误=========");
        }
        return "123";
    }

    @GetMapping("/test")
    @Log(description = "123")
    public String test(Model model, HttpServletRequest request){
        String s = request.getRequestURL().toString();

        log.info("===========requestUrl ,{}" ,s );

        log.info("===========requesrUri ,{}" ,request.getRequestURI());

        log.info("==========调用============");
        model.addAttribute("test","test112312");
        return "test1";
    }

    @GetMapping("/testReadFileInJar")
    @ResponseBody
    public String testReadFileInJar() throws IOException {


//        URL resource = JspController.class.getClassLoader().getResource("1.txt");
        //System.out.println(resource.getPath());
        //读jar里的配置文件要用getResourceAsStream
        InputStream resourceAsStream = JspController.class.getClassLoader().getResourceAsStream("1.txt");
        String s = FileCopyUtils.copyToString(new InputStreamReader(resourceAsStream));
        System.out.println(s);
        return s;
    }

    @GetMapping("/testForward")
    public void testForward(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("request");
       // request.getRequestDispatcher("/testReadFileInJar").forward(request,response);
        response.sendRedirect("/client/test");
    }


    @GetMapping("/view/pdf")
    public void viewPdf(String urlStr, HttpServletResponse response) throws Exception {
        /*byte[] bytes = Base64Utils.decodeFromUrlSafeString(urlStr);
        String s = new String(bytes, StandardCharsets.UTF_8);*/
        log.info("urlstr = {}",urlStr);
        //String s = URLDecoder.decode(urlStr, "iso-8859-1");
        //log.info("url = {}" ,s);
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setConnectTimeout(5000);
        InputStream inputStream = connection.getInputStream();
    /*    byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        byte[] data = bos.toByteArray();*/
        FileCopyUtils.copy(inputStream,response.getOutputStream());
    }

    @PostMapping("/view/pdf/post")
    public void viewPdfPost(String urlStr, HttpServletResponse response) throws Exception {
        log.info("url post = {}" ,urlStr);
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setConnectTimeout(5000);
        InputStream inputStream = connection.getInputStream();
        FileCopyUtils.copy(inputStream,response.getOutputStream());
    }


    public static void main(String[] args) throws UnsupportedEncodingException {
        String url = "http://test.storage.jd.com/shops.import.file/77935_Contract_businessType_1_businessKey_41296384_templateId_527.pdf?Expires=3473492402&AccessKey=mpqzLOX6kPJD9lje&Signature=Y%2FHnU2hlLJqGo3Ic7S7K3l4b8B8%3D";
        String encode = Base64Utils.encodeToUrlSafeString(url.getBytes(StandardCharsets.UTF_8));
        System.out.println(encode);
        String encode1 = URLEncoder.encode(url, "utf-8");
        System.out.println(encode1);

        String e = "http%3A%2F%2Ftest.storage.jd.com%2Fshops.import.file%2F77937_Contract_businessType_1_businessKey_41296385_templateId_527.pdf%3FExpires%3D3473493947%26AccessKey%3DmpqzLOX6kPJD9lje%26Signature%3DpMi%252B8UjlYVfadZwsQnt7s2gG0po%253D";

        String decode = URLDecoder.decode(e, "iso-8859-1");
        System.out.println(decode);
        String decode1 = URLDecoder.decode(e, "utf-8");
        System.out.println(decode1);

    }

}
