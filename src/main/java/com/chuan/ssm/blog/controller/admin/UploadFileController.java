package com.chuan.ssm.blog.controller.admin;

import com.chuan.ssm.blog.dto.JsonResult;
import com.chuan.ssm.blog.dto.UploadFileVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Calendar;

/**
 * @author chuan
 * @date 2021/4/20 18:38
 */
@Slf4j
@RestController // 相当于@ResponseBody+@Controller
@RequestMapping("/admin/upload")
public class UploadFileController {
    /**
     * 文件保存目录：物理路径
     */
    public final String rootPath = "D:\\研究生文件\\code\\ssmBlog\\uploads";

    public final String allowSuffix = ".bmp.jpg.jpeg.png.gif.pdf.doc.zip.rar.gz";

    /**
     * 上传文件。
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/img", method = RequestMethod.POST)
    public JsonResult uploadFile(@RequestParam("file") MultipartFile file) {
        // 1. 文件后缀过滤，只允许部分后缀
        String filename = file.getOriginalFilename();
        String name = filename.substring(0, filename.indexOf("."));
        String suffix = filename.substring(filename.lastIndexOf("."));
        if (allowSuffix.indexOf(suffix) == -1) {
            return new JsonResult().fail("不允许上传该后缀的文件！");
        }
        // 2. 创建文件目录
        Calendar date = Calendar.getInstance();
        File dateDirs = new File(date.get(Calendar.YEAR) + File.separator + (date.get(Calendar.MONTH) + 1));
        File descFile = new File(rootPath + File.separator + dateDirs + File.separator + filename);
        int i = 1;
        String newFilename = filename;
        while (descFile.exists()) {
            newFilename = name + '(' + i + ')' + suffix;
            String parentPath = descFile.getParent();
            descFile = new File(parentPath + File.separator + newFilename);
            ++i;
        }
        // 判断目标文件所在的目录是否存在
        if (!descFile.getParentFile().exists()) {
            // 如果目标文件所在的目录不存在，则创建父目录
            descFile.getParentFile().mkdirs();
        }
        // 3. 存储文件
        try {
            file.transferTo(descFile);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("上传失败，cause:{}", e);
        }
        String fileUrl = "/uploads/" + dateDirs + '/' + newFilename;
        // 4. 返回URL
        UploadFileVO uploadFileVO = new UploadFileVO();
        uploadFileVO.setTitle(filename);
        uploadFileVO.setSrc(fileUrl);
        return new JsonResult().ok(uploadFileVO);
    }
}
