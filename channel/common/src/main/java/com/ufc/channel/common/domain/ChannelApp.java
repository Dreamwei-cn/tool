package com.ufc.channel.common.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName channel_app
 */
@TableName(value ="channel_app")
@Data
public class ChannelApp {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 第三方名称
     */
    private String appName;

    /**
     * 第三方appKey
     */
    private String appKey;

    /**
     * 1 国密  2rsa
     */
    private Integer secType;

    /**
     * 私钥
     */
    private String priKey;

    /**
     * 公钥
     */
    private String pulKey;

    /**
     * 状态 1 可用 0停用
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 应用描述
     */
    private String remark;

    //分页查询
    private String pageSize;
    private String pageNum;

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
        ChannelApp other = (ChannelApp) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getAppName() == null ? other.getAppName() == null : this.getAppName().equals(other.getAppName()))
            && (this.getAppKey() == null ? other.getAppKey() == null : this.getAppKey().equals(other.getAppKey()))
            && (this.getSecType() == null ? other.getSecType() == null : this.getSecType().equals(other.getSecType()))
            && (this.getPriKey() == null ? other.getPriKey() == null : this.getPriKey().equals(other.getPriKey()))
            && (this.getPulKey() == null ? other.getPulKey() == null : this.getPulKey().equals(other.getPulKey()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreateDate() == null ? other.getCreateDate() == null : this.getCreateDate().equals(other.getCreateDate()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getAppName() == null) ? 0 : getAppName().hashCode());
        result = prime * result + ((getAppKey() == null) ? 0 : getAppKey().hashCode());
        result = prime * result + ((getSecType() == null) ? 0 : getSecType().hashCode());
        result = prime * result + ((getPriKey() == null) ? 0 : getPriKey().hashCode());
        result = prime * result + ((getPulKey() == null) ? 0 : getPulKey().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreateDate() == null) ? 0 : getCreateDate().hashCode());
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
        sb.append(", appname=").append(appName);
        sb.append(", appkey=").append(appKey);
        sb.append(", sectype=").append(secType);
        sb.append(", prikey=").append(priKey);
        sb.append(", pulkey=").append(pulKey);
        sb.append(", status=").append(status);
        sb.append(", createdate=").append(createDate);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}