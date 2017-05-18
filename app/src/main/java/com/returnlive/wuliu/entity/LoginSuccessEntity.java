package com.returnlive.wuliu.entity;

/**
 * @author 张梓彬
 * Data : 2017/5/18 0018
 * Time : 下午 1:15
 * Describe : 登录成功的实体类封装
 */
public class LoginSuccessEntity {

    /**
     * state : success
     * id : 7
     * z_session_id : 2055c9663e47375d2c97fefc3e196af5
     */

    private String state;
    private String id;
    private String z_session_id;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZ_session_id() {
        return z_session_id;
    }

    public void setZ_session_id(String z_session_id) {
        this.z_session_id = z_session_id;
    }
}
