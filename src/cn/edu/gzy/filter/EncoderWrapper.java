package cn.edu.gzy.filter;

import org.owasp.encoder.Encode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Optional;

/**
 * 请求封装器
 * 过滤用户的输入，比如去掉特殊的字符。
 */
public class EncoderWrapper extends HttpServletRequestWrapper {
    //必须调用父类的构造函数，传入HttpServletRequest实例
    public EncoderWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(String name) {
        return Optional.ofNullable(getRequest().getParameter(name)).map(Encode::forHtml).orElse(null);
    }
}
