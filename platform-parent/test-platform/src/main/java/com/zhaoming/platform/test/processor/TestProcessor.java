package com.zhaoming.platform.test.processor;

import com.zhaoming.base.annotation.Param;
import com.zhaoming.bean.platfrom.ServiceProcessor;
import com.zhaoming.bean.platfrom.ServiceResponse;
import com.zhaoming.bean.platfrom.annotation.Processor;
import com.zhaoming.bean.platfrom.constant.ServiceParameter;
import com.zhaoming.bean.platfrom.enums.Versions;
import com.zhaoming.bean.platfrom.enums.Service;
import com.zhaoming.bean.platfrom.util.PlatformServiceUtil;
import com.zhaoming.client.test.CategoryClient;
import com.zhaoming.platform.test.response.TestResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.zhaoming.bean.platfrom.constant.ServiceParameter.getServiceParameter;


/**
 * 控制器
 *
 * @author zm
 * @date 2018-12-05 15:43:51
 */
@Processor(desc = "test列表", service = Service.PLATFORM, method = "zm.test",level = Versions.V1,response = TestResponse.class)
public class TestProcessor implements ServiceProcessor {

    private final CategoryClient client;

    @Param(desc = "test")
    private static final ServiceParameter TEST = getServiceParameter("test", false, null);

    @Autowired
    public TestProcessor(CategoryClient client) {
        this.client = client;
    }

    @Override
    public List<ServiceParameter> parameter() {
        return Collections.singletonList(TEST);
    }

    /**
     * 处理
     */
    @Override
    public ServiceResponse process(Map<String, String> map) throws ParseException {
        return PlatformServiceUtil.returnResult(client.list(),
                TestResponse.Result.class,
                (r, d) -> new TestResponse(r, d.orElse(null)));
    }
}