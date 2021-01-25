package me.lily.movie.web;

import io.jsonwebtoken.Claims;
import me.lily.movie.utils.ResultVoUtil;
import me.lily.movie.vo.ResultVo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ApiTest {
    @CrossOrigin
    @RequestMapping("/api/jwt/v.do")
    public ResultVo show(HttpServletRequest request){
        Claims claims = (Claims) request.getAttribute("claims");
        return ResultVoUtil.success(claims.get("userid"));
    }
    @CrossOrigin
    @RequestMapping("/ping")
    public String show(){
        return "I`m Live";
    }

}
