package com.zhaoming.client.platform;


import com.zhaoming.bean.platfrom.document.DocumentInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author zm
 */
@FeignClient("zm-test-platform")
public interface TestPlatformClient {

    /**
     * APP 接口文档
     *
     * @return
     */
    @GetMapping("/doc")
    ResponseEntity<DocumentInfo> getDocument();
}
