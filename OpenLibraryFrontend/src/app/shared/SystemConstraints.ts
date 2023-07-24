export class SystemConstraints {

    // MESSAGE
    public static SOMETHING_WENT_WRONG:string = "Đã xảy ra sự cố.";
    public static ACCOUNT_ALREADY_EXISTS:string = "Tài khoản đã tồn tại.";
    public static REGISTER_ACCOUNT_SUCCESS:string = "Đăng kí tài khoản thành công.";
    public static PASSWORD_NOT_MATCHES:string = "Mật khẩu nhập lại không chính xác.";
    public static INVALID_USERNAME_OR_PASSWORD:string = "Tài khoản hoặc mật khẩu không chính xác.";
    public static ACCESS_DENIED:string = "Bạn không có quyền truy cập tài nguyên này.";
    // REGEX
    public static REGEX_EMAIL: string = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$";
    public static REGEX_PHONE:string = "^(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})$";
    // Varible
    public static ERROR:string = "error";
    // Enviroment
    public static API_URL: string = 'http://localhost:8080';
}