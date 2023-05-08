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
 * @TableName channel_app_interface_log
 */
@TableName(value ="channel_app_interface_log")
@Data
public class ChannelAppInterfaceLog implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long inid;

    /**
     * 接口
     */
    private Long interfaceid;

    /**
     * 第三方id
     */
    private Long appid;

    /**
     * 接口接入时间
     */
    private Date createdate;

    /**
     * 接口接收内容
     */
    private String content;

    /**
     * 解密数据
     */
    private String data;

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
        ChannelAppInterfaceLog other = (ChannelAppInterfaceLog) that;
        return (this.getInid() == null ? other.getInid() == null : this.getInid().equals(other.getInid()))
            && (this.getInterfaceid() == null ? other.getInterfaceid() == null : this.getInterfaceid().equals(other.getInterfaceid()))
            && (this.getAppid() == null ? other.getAppid() == null : this.getAppid().equals(other.getAppid()))
            && (this.getCreatedate() == null ? other.getCreatedate() == null : this.getCreatedate().equals(other.getCreatedate()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getData() == null ? other.getData() == null : this.getData().equals(other.getData()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getInid() == null) ? 0 : getInid().hashCode());
        result = prime * result + ((getInterfaceid() == null) ? 0 : getInterfaceid().hashCode());
        result = prime * result + ((getAppid() == null) ? 0 : getAppid().hashCode());
        result = prime * result + ((getCreatedate() == null) ? 0 : getCreatedate().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getData() == null) ? 0 : getData().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", inid=").append(inid);
        sb.append(", interfaceid=").append(interfaceid);
        sb.append(", appid=").append(appid);
        sb.append(", createdate=").append(createdate);
        sb.append(", content=").append(content);
        sb.append(", data=").append(data);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}