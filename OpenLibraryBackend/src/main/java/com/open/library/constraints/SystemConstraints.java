package com.open.library.constraints;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class SystemConstraints {
    public static final String SOMETHING_WENT_WRONG = "Đã xảy ra sự cố.";
    public static final Object ACCOUNT_ALREADY_EXISTS = "Tài khoản đã tồn tại.";
    public static final Object REGISTER_ACCOUNT_SUCCESS = "Đăng kí tài khoản thành công.";
    public static final Object PASSWORD_NOT_MATCHES = "Mật khẩu nhập lại không chính xác.";
    public static final String INVALID_USERNAME_OR_PASSWORD = "Tài khoản hoặc mật khẩu không chính xác.";
    public static final String ACCESS_DENIED = "Bạn không có quyền này.";
    public static final String CATEGORY = "category";
    public static final String AUTHOR = "author";
    public static final String PUBLISHER = "publisher";

    public static final String SEARCH_CATEGORY = "search_category";
    public static final String SEARCH_AUTHOR = "search_author";
    public static final String SEARCH_PUBLISHER = "search_publisher";
    public static final String SEARCH_BOOK = "search_book";
    public static final String SEARCH_ALL = "search_all";
}
