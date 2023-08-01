package easyquery.entity;


import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.Table;
import com.mybatisflex.annotation.EnumValue;

import java.time.LocalDateTime;

/**
 * create time 2023/4/26 22:57
 * 文件说明
 *
 * @author xuejiaming
 */
@Table(value = "tb_account")
public class EasyQueryAccount {
    @Column(primaryKey = true,increment = true)
    private Long id;
    private String userName;

    private String password;

    private String salt;

    private String nickname;

    private String email;

    private String mobile;

    private String avatar;

    private Integer type;

    private Integer status;

    private LocalDateTime created;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
