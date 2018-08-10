package com.demon.com.materialdesign.test;

import com.demon.com.materialdesign.frame.guard.BaseJobGuardService;

public class JobGuardService extends BaseJobGuardService {
    @Override
    protected Class guardService() {
        return MessagerService.class;
    }
}
