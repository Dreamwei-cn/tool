package com.ufc.channel.common.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName channel_app_interface
 */
@TableName(value ="channel_app_interface")
@Data
public class ChannelAppInterface implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 接口名称
     */
    private String apiname;

    /**
     * 接口地址
     */
    private String apiurl;

    /**
     * 请求方法 0 get  1 post 2put 
     */
    private Integer method;

    /**
     * 回调地址
     */
    private String backurl;

    /**
     * 回调方法 0 get  1 post 2put 
     */
    private Integer backmethod;

    /**
     * 第三方id
     */
    private Long appid;

    /**
     * 接口说明
     */
    private String remark;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ChannelAppInterface other = (ChannelAppInterface) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getApiname() == null ? other.getApiname() == null : this.getApiname().equals(other.getApiname()))
            && (this.getApiurl() == null ? other.getApiurl() == null : this.getApiurl().equals(other.getApiurl()))
            && (this.getMethod() == null ? other.getMethod() == null : this.getMethod().equals(other.getMethod()))
            && (this.getBackurl() == null ? other.getBackurl() == null : this.getBackurl().equals(other.getBackurl()))
            && (this.getBackmethod() == null ? other.getBackmethod() == null : this.getBackmethod().equals(other.getBackmethod()))
            && (this.getAppid() == null ? other.getAppid() == null : this.getAppid().equals(other.getAppid()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getApiname() == null) ? 0 : getApiname().hashCode());
        result = prime * result + ((getApiurl() == null) ? 0 : getApiurl().hashCode());
        result = prime * result + ((getMethod() == null) ? 0 : getMethod().hashCode());
        result = prime * result + ((getBackurl() == null) ? 0 : getBackurl().hashCode());
        result = prime * result + ((getBackmethod() == null) ? 0 : getBackmethod().hashCode());
        result = prime * result + ((getAppid() == null) ? 0 : getAppid().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", apiname=").append(apiname);
        sb.append(", apiurl=").append(apiurl);
        sb.append(", method=").append(method);
        sb.append(", backurl=").append(backurl);
        sb.append(", backmethod=").append(backmethod);
        sb.append(", appid=").append(appid);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}