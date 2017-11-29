package com.noone.paymentms.common.basemodel;

public class ServerResponse<T> extends BaseResponse<T>
{
    private String nonceToken;

    public String getNonceToken()
    {
        return nonceToken;
    }

    public void setNonceToken(String nonceToken)
    {
        this.nonceToken = nonceToken;
    }
}
