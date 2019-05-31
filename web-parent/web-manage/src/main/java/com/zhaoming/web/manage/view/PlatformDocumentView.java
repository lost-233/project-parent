package com.zhaoming.web.manage.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhaoming.bean.platfrom.document.DocumentInfo;
import com.zhaoming.bean.platfrom.document.GroupDocument;
import com.zhaoming.client.platform.TestPlatformClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

/**
 * @author hhx
 */
@RestController
public class PlatformDocumentView extends BaseView{

    public static final String APP = "/platform/app";

    @Autowired
    private TestPlatformClient platformClient;

    private static final String GIT_NAME = "gitName";

    @GetMapping(APP)
    public ModelAndView app(Model model, ObjectMapper objectMapper) throws IOException {
        model.addAttribute(GIT_NAME, "ims-app-platform");
        return getModelAndView(model, objectMapper, platformClient.getDocument());
    }


    private ModelAndView getModelAndView(Model model, ObjectMapper objectMapper, ResponseEntity<DocumentInfo> document) throws JsonProcessingException {
        DocumentInfo documentInfo = document.getBody();
        List<GroupDocument> data = documentInfo.getList();
        model.addAttribute("documents",data);
        model.addAttribute("address",documentInfo.getAddress());
        return toView("doc/document",model);
    }

}
