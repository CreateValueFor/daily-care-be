package com.example.dailycarebe;

import com.example.dailycarebe.hash.HashIds;
import com.example.dailycarebe.util.HashidsUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Initiator {

    private final HashIds hashIds;

    @EventListener({ContextRefreshedEvent.class})
    public void handleContextStarted() {
        HashidsUtil.setDefaultHashids(hashIds);
    }
}
