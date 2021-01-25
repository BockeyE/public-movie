package me.lily.movie.web;


import io.jsonwebtoken.Claims;
import me.lily.movie.utils.Sha256Utils;
import me.lily.movie.domain.Movie;
import me.lily.movie.service.MovieService;
import me.lily.movie.service.impl.CommonFileServiceImpl;
import me.lily.movie.service.impl.RecommendServiceImpl;
import me.lily.movie.utils.ResultVoUtil;
import me.lily.movie.vo.ResultVo;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

@RestController
public class MovieController {
    private MovieService movieService;
    @Autowired
    private CommonFileServiceImpl fileService;

    @Value("${file.fileFtpPath}")
    private String fileFtpPath;
    private RecommendServiceImpl recommendService;

    @Value("${recommend.size}")
    private Integer recommendSize;

    @Autowired
    public MovieController(MovieService movieService, RecommendServiceImpl recommendService) {
        this.movieService = movieService;
        this.recommendService = recommendService;
    }

    @CrossOrigin
    @RequestMapping("/api/jwt/movie/upload.do")
    public ResultVo upload(MultipartFile file, String movieName) throws IOException {
        if (file.isEmpty()) {
            return ResultVoUtil.error(400, "error");
        }
        String imgName = "";

        Movie movie = new Movie();
        movie.setMovieName(movieName);
        movie.setImgName(imgName);
        movie.setImgUrl(imgName);


        String origName = file.getOriginalFilename();
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
            movie.setImgUrl("/common/file/view/" + hash);
            fileService.insertOne(origName, filurl, hash);
            movieService.save(movie);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultVoUtil.success(imgName);
    }

    public String currentDir() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        return "" + year + "/" + month + "/";
    }

    @CrossOrigin
    @RequestMapping("/api/movie/list.do")
    public ResultVo list() {
        List<Movie> movieList = movieService.findAll();
        return ResultVoUtil.success(movieList);
    }

    @CrossOrigin
    @RequestMapping("/api/movie/listPage.do")
    public ResultVo list(Integer page, Integer size) {
        Page<Movie> movieList = movieService.findAll(page, size);
        return ResultVoUtil.success(movieList);
    }

    @CrossOrigin
    @RequestMapping("/api/movie/info.do")
    public ResultVo list(Long id) {
        Movie movie = movieService.getOne(id);
        return ResultVoUtil.success(movie);
    }

    @CrossOrigin
    @RequestMapping("/api/jwt/movie/recommend.do")
    public ResultVo recommend(HttpServletRequest request) throws TasteException {
        Claims claims = (Claims) request.getAttribute("claims");
        Object userid = claims == null ? "1000" : claims.get("userid");
        List<RecommendedItem> recommendedItems = recommendService.recommenderItem(Long.parseLong(String.valueOf(userid)), recommendSize);
        return ResultVoUtil.success(recommendedItems);
    }
}
