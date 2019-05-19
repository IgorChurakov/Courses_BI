package bell.courses.controller;

import bell.courses.view.response.ResponseView;
import bell.courses.view.response.ResultView;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ControllerResponseBodyAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object object, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (object instanceof Boolean){
            if ((Boolean) object){
                return new ResultView("success");
            }
        }
        if (object instanceof List) {
            List<Object> responseList = new ArrayList<>();
            for (Object node: (List)object) {
                ResponseView data = (ResponseView) node;
                responseList.add(data.wrapInListView());
            }
            return responseList;
        }
        if (object instanceof ResponseView) {
            ResponseView office = (ResponseView) object;
            return office.wrapInView();
        }
        return object;
    }
}
