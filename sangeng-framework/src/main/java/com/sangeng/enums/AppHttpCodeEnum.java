package com.sangeng.enums;

/**
 * @author frank
 *
 * 状态枚举类
 */
public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200,"操作成功"),
    // 登录
    NEED_LOGIN(401,"需要登录后才能操作"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    SYSTEM_ERROR(500,"出现错误"),
    USERNAME_EXIST(501,"用户名己存在"),
    PHONENUMBER_EXIST(502,"手机号已存在"),
    EMAIL_EXIST(503,"邮箱己存在"),
    REQUIRE_USERNAME(504,"必需填写用户名"),
    LOGIN_ERROR(505,"用户名或密码错误"),
    CONTENT_NOT_NULL(506, "评论不能为空"),
    USERNAME_NOT_NULL(507,"用户名不能为空"),
    NICKNAME_NOT_NULL(508,"昵称不能为空"),
    PASSWORD_NOT_NULL(509,"密码不能为空"),
    EMAIL_NOT_NULL(510,"邮箱不能为空"),
    NICKNAME_EXIST(511,"昵称已存在"),
    FILE_TYPE_ERROR(512,"文件类型错误"),
    FIELD_NOT_NULL(513,"字段不能为空"),
    MENU_PARENT_ID_ERROR(514,"修改菜单失败，上级菜单不能选择自己"),
    EXIST_MENU_CHILDREN(515,"存在子菜单，不允许删除" );

    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode(){
        return code;
    }

    public String getMsg(){
        return msg;
    }
}
