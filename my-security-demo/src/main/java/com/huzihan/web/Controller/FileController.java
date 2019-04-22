package com.huzihan.web.Controller;

import com.huzihan.dto.FileInfo;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.nio.ch.IOUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;

@RestController
public class FileController {

    String folder = "D:\\ideaWork2\\DemoSecurity\\my-security-demo\\src\\main\\java\\com\\huzihan\\web\\Controller";

    @PostMapping("/file")
    public FileInfo upload(MultipartFile file) throws IOException {
        System.out.println(file.getName());
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());

        File localFile = new File(folder,new Date().getTime() + ".txt");

        file.transferTo(localFile);
        return new FileInfo(localFile.getAbsolutePath());
    }

    @GetMapping("/file/{id}")
    public void download(@PathVariable String id, HttpServletRequest request, HttpServletResponse response){
        try(
                InputStream inputStream = new FileInputStream(new File(folder,id + ".txt"));
                OutputStream outputStream = response.getOutputStream();
                ){

            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition","attachment;filename=test.txt");
            IOUtils.copy(inputStream,outputStream);
            outputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
