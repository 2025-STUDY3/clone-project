package com.ll.clone_coding.domain.anonymous.service;

import com.ll.clone_coding.domain.user.entity.User;
import org.springframework.stereotype.Service;

@Service
public class AnonymousService {

    private static final String ANONYMOUS_NAME = "익명";

    public String getDisplayNickname(User user, boolean isAnonymous) {
        return isAnonymous ? ANONYMOUS_NAME : user.getNickname();
    }
}