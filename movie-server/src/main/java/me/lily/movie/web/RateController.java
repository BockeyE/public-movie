package me.lily.movie.web;

import io.jsonwebtoken.Claims;
import me.lily.movie.domain.Audience;
import me.lily.movie.domain.Rate;
import me.lily.movie.service.RateService;
import me.lily.movie.utils.JwtHelper;
import me.lily.movie.utils.ResultVoUtil;
import me.lily.movie.vo.ResultVo;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class RateController {
    private RateService rateService;

    @Autowired
    public RateController(RateService rateService) {
        this.rateService = rateService;
    }

    @CrossOrigin
    @RequestMapping("/api/jwt/rate/save.do")
    public ResultVo save(Long movieId, Double rate, HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("claims");
        if (claims == null) {
            claims = quickHelper(request);
        }
        if (claims == null) {
            return ResultVoUtil.error(500, "bad req");
        }
        Rate rate1 = new Rate();
        rate1.setMovieId(movieId);
        rate1.setRate(rate);
        rate1.setUserId(Long.parseLong(String.valueOf(claims.get("userid"))));
        Rate save = rateService.save(rate1);
        return ResultVoUtil.success(save);
    }

    @CrossOrigin
    @RequestMapping("/api/jwt/rate/list.do")
    public ResultVo findByUserId(HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("claims");
        List<Rate> rateList = rateService.findByUserId(Long.parseLong(String.valueOf(claims.get("userid"))));
        return ResultVoUtil.success(rateList);
    }

    @CrossOrigin
    @RequestMapping("/api/rate/all.do")
    public ResultVo findAllGroupByMovieId() {
        List<?> rateList = rateService.findAllGroupByMovieId();
        return ResultVoUtil.success(rateList);
    }


    private Claims quickHelper(HttpServletRequest request) {
        final String authHeader = request.getHeader("authorization");
        if (authHeader == null || !authHeader.startsWith("bearer;")) {
            return null;
        }
        final String token = authHeader.substring(7);
        BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        Audience audience = (Audience) factory.getBean("audience");
        return JwtHelper.parseJWT(token, audience.getBase64Secret());

    }
}
