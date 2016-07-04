package com.shushanfx.uploadspring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by shushanfx on 2016/7/4.
 */
@Controller
public class UploadController {
    @RequestMapping(value = "/upload.php", method = RequestMethod.POST)
    public void upload(@RequestParam MultipartFile[] upload, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<String> fileNames = new LinkedList<String>();
        if(upload!=null){
            for(MultipartFile file : upload){
                String fileName = file.getName();
                if(fileName!=null){
                    fileNames.add(fileName + "|" + file.getOriginalFilename() + "|" + file.getSize());
                    file.transferTo(new File(file.getOriginalFilename()));
                }
            }
            req.setAttribute("fileNames", fileNames);
        }
        upload(req, resp);
    }

    @RequestMapping(value="/upload.php", method = RequestMethod.GET)
    public void upload(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<html>");
        out.println("<body>");
        out.println("<form id=\"upload-form\" action=\"upload.php\" method=\"post\" enctype=\"multipart/form-data\">\n" +
                "    <input type=\"file\" id=\"upload\" name=\"upload\" />\n" +
                "    <input type=\"submit\" value=\"Upload\" />\n" +
                "</form>");
        if(req.getAttribute("fileNames")!=null){
            out.println("fileNames -> ");
            List<String> list = (List<String>) req.getAttribute("fileNames");
            for(String item : list){
                out.print(item + "&nbsp;");
            }
            out.println("<br />");
        }
        out.println("</body>");
        out.println("</html>");
    }
}
