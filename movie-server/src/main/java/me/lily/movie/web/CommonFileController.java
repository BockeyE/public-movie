package me.lily.movie.web;

import lombok.extern.slf4j.Slf4j;
import me.lily.movie.domain.CommonFile;
import me.lily.movie.service.impl.CommonFileServiceImpl;
import me.lily.movie.utils.Sha256Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.Calendar;
import java.util.Map;

/**
 * 通用文件管理
 * @author: bockey
 */
@Slf4j
@RestController
public class CommonFileController {

    @Autowired
    private CommonFileServiceImpl fileService;

    @Value("${file.fileFtpPath}")
    private String fileFtpPath;

    /**
     * 单文件上传
     *
     * @param file
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/common/file/upload", headers = "api-version=1.0.0", method = RequestMethod.POST)
    public String fileUpload(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return "0";
        }
        // 获取原始名字
        String origName = file.getOriginalFilename();
        if (origName.length() > 120) {
            return "0";
        }
        String fileName = file.getOriginalFilename();
        // 获取后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        byte[] bytes = file.getBytes();

        // 文件保存路径
        String filePath = fileFtpPath + currentDir();
        String hash = Sha256Utils.getSha256ByBytes(bytes);
        fileName = hash + suffixName;
        // 文件对象
        String filurl = filePath + fileName;
        File dest = new File(filurl);
        // 判断路径是否存在，如果不存在则创建
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            // 保存到服务器中
            file.transferTo(dest);
            fileService.insertOne(origName, filurl, hash);
        } catch (Exception e) {
            return "0";
        }
        return "1";
    }

    @CrossOrigin
    @RequestMapping(value = "/common/file/upbase64", headers = "api-version=1.0.0", method = RequestMethod.POST)
    public String up64(@RequestBody Map file) {
        String base64 = (String) file.get("file");
        String fileName = (String) file.get("fileName");
        String hash;
        try {
            byte[] bytes = Base64.getDecoder().decode(base64);
            // 获取原始名字
            String origName = fileName;
            // 获取后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            // 文件保存路径
            String filePath = fileFtpPath + currentDir();
            hash = Sha256Utils.getSha256ByBytes(bytes);
            fileName = hash + suffixName;
            // 文件对象
            String filurl = filePath + fileName;
            File dest = new File(filurl);
            // 判断路径是否存在，如果不存在则创建
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(dest);
            fos.write(bytes);
            fos.flush();
            fos.close();
            fileService.insertOne(origName, filurl, hash);
        } catch (Exception e) {
            return "0";
        }
        return "1";
    }

    /**
     * 通过md5值，以附件形式下载文件
     *
     * @param response
     * @return
     * @throws IOException
     */
    @CrossOrigin
    @GetMapping(value = "/common/file/down")
    public String downLoadFileByMd5(String hash, HttpServletResponse response) throws IOException {
        log.debug("文件下载开始执行，入参参数为：{}", hash);
        try {
            CommonFile byHash = fileService.getByHash(hash);
            if (byHash == null) {
                return "0";
            }
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(byHash.getFileName(), "UTF-8"));
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(filetobyte(new File(byHash.getFileUrl())));
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            return "0";
        }
        return "1";
    }

    @CrossOrigin
    @GetMapping(value = "/common/file/view/{hash}")
    public String previewFileByMd5(@PathVariable String hash, HttpServletResponse response) throws IOException {

        log.debug("文件下载开始执行，入参参数为：{}", hash);
        try {
            CommonFile byHash = fileService.getByHash(hash);
            if (byHash == null) {
                return "0";
            }
            byte[] filetobyte = filetobyte(new File(byHash.getFileUrl()));
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(filetobyte);
            outputStream.close();
        } catch (Exception e) {
            return "0";
        }
        return "1";
    }

    /**
     * 将文件转换成byte数组
     *
     * @param tradeFile
     * @return
     */
    public byte[] filetobyte(File tradeFile) {
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(tradeFile);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    public String currentDir() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        return "" + year + "/" + month + "/";

    }
}
