package com.dhy.common.lib.taskproxy.test;

import lombok.Data;


@Data
public class RequestContext {
    private String sessionId;
    private String dbKey;
}