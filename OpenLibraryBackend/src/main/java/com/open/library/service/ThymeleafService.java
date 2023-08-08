package com.open.library.service;

import java.util.Map;

public interface ThymeleafService {
    String createContent(String template, Map<String, Object> variables);
}
