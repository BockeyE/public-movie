package me.lily.movie.fliter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 过滤器
 */
@WebFilter
@Slf4j
//@Configuration
//过滤器在配置了@ServletComponentScan情况下不能用@Order注解指定执行顺序，默认根据过滤器的名称从A-Z排序
public class MyFilterConfiguration implements Filter {

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        log.info("----------------过滤器正在启动-----------------");
    }

    @Override
    public void destroy() {
        log.info("----------------过滤器销毁-----------------");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        System.out.println(req.getServerName());
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        //解决跨域问题
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, request.getHeader("Origin"));
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "*");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Origin, X-Requested-With, Content-Type, Accept, token, api-version");
        //将req替换成自定义RepeatableHttpServletRequest对象，解决流不可重复读取问题
        chain.doFilter(request, response);
    }

}
