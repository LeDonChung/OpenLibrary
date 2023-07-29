package com.open.library.utils;

import lombok.Builder;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Builder
public class ValidateUtils {
    private Object value;
    private boolean required;
    private Integer maxLength;
    private Integer minLength;
    private String fieldName;
    private String regex;
    private boolean onlyNumber;
    private boolean isInteger;
    private boolean isEmail;
    private boolean isPhone;
    private boolean isIsbn;
    private Long max;
    private Long min;
    private String message;
    private final String ONLY_NUMBER = "[0-9]+";
    private final String REGEX_EMAIL = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
    private final String REGEX_PHONE = "^(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})$";
    /**
     * // Test Case 1:
     * String str1 = "978-1-45678-123-4"; -> true
     * <p>
     * // Test Case 2:
     * String str2 = "1-56619-909-3"; -> true
     * <p>
     * // Test Case 3:
     * String str3 = "1207199818865"; -> true
     * <p>
     * // Test Case 4:
     * String str4 = "978-1-12345-909-4 2"; -> false
     * <p>
     * // Test Case 5:
     * String str5 = "ISBN446877428FCI"; -> false
     */
    private final String REGEX_ISBN = "^(?=(?:[^0-9]*[0-9]){10}(?:(?:[^0-9]*[0-9]){3})?$)[\\d-]+$";

    public Map validate() {
        Map<String, String> errors = new HashMap<>();

        //check field is required
        if (required && ObjectUtils.isEmpty(value) && !ObjectUtils.isEmpty(fieldName)) {
            errors.put(fieldName, fieldName + " là bắt buộc.");
        }

        //check max length of field
        if (!ObjectUtils.isEmpty(maxLength)
                && !ObjectUtils.isEmpty(value)
                && value.toString().length() > maxLength.intValue()
                && !ObjectUtils.isEmpty(fieldName)
        ) {
            errors.put(fieldName, fieldName + " phải từ 0 - " + maxLength + " kí tự.");
        }

        //check min length of field
        if (!ObjectUtils.isEmpty(minLength)
                && !ObjectUtils.isEmpty(value)
                && value.toString().length() < minLength.intValue()
                && !ObjectUtils.isEmpty(fieldName)) {
            errors.put(fieldName, fieldName + " phải từ " + minLength + " kí tự.");
        }

        //check field name is only number
        if (onlyNumber && !ObjectUtils.isEmpty(value) && !ObjectUtils.isEmpty(fieldName)) {
            Pattern patternOnlyNumber = Pattern.compile(ONLY_NUMBER);
            Matcher matcher = patternOnlyNumber.matcher(value.toString());
            if (!matcher.matches()) {
                errors.put(fieldName, fieldName + " chỉ được chứa số.");
            }
        }

        //check field name is integer
        if (isInteger && !ObjectUtils.isEmpty(value) && !ObjectUtils.isEmpty(fieldName)) {
            try {
                Integer.parseInt(value.toString());
            } catch (Exception e) {
                errors.put(fieldName, fieldName + " phải là số nguyên");
            }
        }


        //check max and min of field value
        if (!ObjectUtils.isEmpty(max)
                && !ObjectUtils.isEmpty(value)
                && !ObjectUtils.isEmpty(min)
                && !ObjectUtils.isEmpty(fieldName)) {
            try {
                Long valueCheck = Long.valueOf(value.toString());
                if (valueCheck < min || valueCheck > max) {
                    errors.put(fieldName, fieldName + " phải dao động từ " + min + " đến " + max);
                }
            } catch (Exception e) {
                errors.put(fieldName, fieldName + " là loại dữ liệu không hợp lệ.");
            }
        }

        //check field name is email
        if (isEmail && !ObjectUtils.isEmpty(value) && !ObjectUtils.isEmpty(fieldName)) {
            Pattern patternEmail = Pattern.compile(REGEX_EMAIL);
            Matcher matcher = patternEmail.matcher(value.toString());
            if (!matcher.matches()) {
                errors.put(fieldName, "Email không hợp lệ.");
            }
        }

        //check field name is phone
        if (isPhone && !ObjectUtils.isEmpty(value) && !ObjectUtils.isEmpty(fieldName)) {
            Pattern patternNumberPhone = Pattern.compile(REGEX_PHONE);
            Matcher matcher = patternNumberPhone.matcher(value.toString());
            if (!matcher.matches()) {
                errors.put(fieldName, "Số điện thoại không hợp lệ.");
            }
        }
        //check field name is isbn
        if (isIsbn && !ObjectUtils.isEmpty(value) && !ObjectUtils.isEmpty(fieldName)) {
            Pattern patternIsbn = Pattern.compile(REGEX_ISBN);
            Matcher matcher = patternIsbn.matcher(value.toString());
            if (!matcher.matches()) {
                errors.put(fieldName, "ISBN không hợp lệ.");
            }
        }
        return errors;
    }
}
