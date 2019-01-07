package com.risepu.ftk.server.service;

import java.util.List;

import net.lc4ever.framework.remote.annotation.Remote;

/**
 * @author q-wang
 */
@Remote(path = "/sample")
public interface SampleService {
    List<String> sample1();
}
