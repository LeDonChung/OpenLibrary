package com.open.library.service;

import com.open.library.POJO.Feedback;
import com.open.library.utils.request.FeedbackDTO;

public interface MailService {
    void sendResponseFeedback(Feedback feedback, String content, boolean defaultEmail);
}
