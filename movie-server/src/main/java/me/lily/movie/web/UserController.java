package me.lily.movie.web;

import me.lily.movie.vo.ResultVo;
import me.lily.movie.domain.Audience;
import me.lily.movie.domain.User;
import me.lily.movie.service.UserService;
import me.lily.movie.utils.JwtHelper;
import me.lily.movie.utils.ResultVoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RequestMapping
@RestController
public class UserController {
    private UserService userService;
    private Audience audience;

    @Autowired
    public UserController(UserService userService, Audience audience) {
        this.userService = userService;
        this.audience = audience;
    }

    @CrossOrigin
    @RequestMapping("/api/user/sign.do")
    public ResultVo userAdd(User user) {
        System.out.println("userAdd");
        User user1 = userService.save(user);
        if (null != user1 && user.getPassword().equals(user1.getPassword())) {
            user1.setPassword(null);

            String jwtToken = JwtHelper.createJWT(user1.getUserName(),
                    String.valueOf(user1.getId()),
//                query_user.getRole().toString(),
                    "",
                    audience.getClientId(),
                    audience.getName(),
                    audience.getExpiresSecond() * 1000,
                    audience.getBase64Secret());

            String resultStr = "bearer;" + jwtToken;
            return ResultVoUtil.success(resultStr);
        }
        return null;
    }

    @CrossOrigin
    @RequestMapping("/api/user/validate.do")
    @ResponseBody
    public boolean userValidate(String userName) {
        User user = userService.findByUserName(userName);
        return user == null;
    }

    @CrossOrigin
    @PostMapping("/api/user/login.do")
    public ResultVo login(User user) {
        System.out.println("sdsad");
        User queryUser = userService.findByUserName(user.getUserName());
        if (queryUser == null) {
            return ResultVoUtil.error(400, "用户名错误");
        }
        //验证密码
        if (!user.getPassword().equals(queryUser.getPassword())) {
            return ResultVoUtil.error(400, "密码错误");
        }
        String jwtToken = JwtHelper.createJWT(queryUser.getUserName(),
                String.valueOf(queryUser.getId()),
//                query_user.getRole().toString(),
                "",
                audience.getClientId(),
                audience.getName(),
                audience.getExpiresSecond() * 1000,
                audience.getBase64Secret());

        String resultStr = "bearer;" + jwtToken;
        return ResultVoUtil.success(resultStr);
    }
}
