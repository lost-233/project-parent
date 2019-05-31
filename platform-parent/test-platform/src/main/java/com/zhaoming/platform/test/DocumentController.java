package com.zhaoming.platform.test;

import com.zhaoming.bean.platfrom.document.DocumentInfo;
import com.zhaoming.bean.platfrom.enums.Versions;
import com.zhaoming.bean.platfrom.util.DocumentUtil;
import com.zhaoming.bean.platfrom.util.ProcessorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * @author hhx
 */

@RestController
public class DocumentController {

    @Autowired
    private ServiceSelector selector;

    @Autowired
    Environment environment;

    private static DocumentInfo doc = null;

    @GetMapping("/doc")
    public ResponseEntity<DocumentInfo> getDocument() {
        if (true) {
            Versions ver = ProcessorUtil.getMaxVersion(Arrays.asList(Versions.values()));
            String address = "测试环境 : http://api-dev.lost-2112.top/" + ver.getVersion()
                    + ", 正式环境 : http://api-online.lost-2112.top/" + ver.getVersion();
            doc = DocumentUtil.createDocument(address, "*", selector.getProcessors());
        }
        return ResponseEntity.ok(doc);
    }
}
