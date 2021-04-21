package com.dhy.common.lib.peakcut.config;

import java.util.List;

/**
 * @Project dhy-common-lib
 * @Description 主要用途描述
 * @Author lvaolin
 * @Date 2021/4/21 6:13 下午
 */
public interface IThreadPoolConfigService {
    public List<ThreadPoolConfigDto> query(ThreadPoolConfigDto dto);
}
