package org.gantry.apiserver.domain;

import com.github.dockerjava.api.async.ResultCallbackTemplate;

public class LogCallback extends ResultCallbackTemplate {
    @Override
    public void onNext(Object object) {
        System.out.print(object+" : "+object.toString());
    }
}
