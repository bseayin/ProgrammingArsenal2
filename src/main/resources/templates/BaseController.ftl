package ${basePackage};

import cn.hutool.core.util.StrUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * Controller公共组件
 * @author  ${author}
 */
public abstract class BaseController {

    protected HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    protected HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
    }

    /**
     * 从请求中获取token
     */
    protected String getToken(String tokenHeader,String tokenHead){
        String authHeader = getRequest().getHeader(tokenHeader);
        if(StrUtil.isNotBlank(authHeader)){
            return authHeader.substring(tokenHead.length()).trim();
        }
        return null;
    }

}
