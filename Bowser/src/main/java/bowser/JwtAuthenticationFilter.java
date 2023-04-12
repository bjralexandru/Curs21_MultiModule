package bowser;


import bjr.spring.login.Security.jwt.JwtUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;



@Component
public class JwtAuthenticationFilter extends ZuulFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();

        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            if (jwtUtils.validateJwtToken(token)) {
                context.addZuulRequestHeader("Authorization", "Bearer " + token);
            } else {
                context.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
                context.setResponseBody("Invalid token");
                context.setSendZuulResponse(false);
            }
        } else {
            context.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            context.setResponseBody("Missing token");
            context.setSendZuulResponse(false);
        }

        return null;
    }
}