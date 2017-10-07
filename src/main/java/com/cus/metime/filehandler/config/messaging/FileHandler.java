package com.cus.metime.filehandler.config.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * Created by C-US on 9/29/2017.
 */
public interface FileHandler {

    @Input
    SubscribableChannel fileInput();

}
