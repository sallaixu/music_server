package com.sallai.music.module.vo;

/**
 * @ClassName QrUrlPojo
 * @Description TODO
 * @Author sallai
 * @Email sallai@aliyun.com
 * @Date 10:23 AM 3/16/2024
 * @Version 1.0
 **/

public class QrUrlPojo {

    public static class Data {

        private String qrCodeReturnUrl;
        private String tempUserId;

        public void setQrCodeReturnUrl(String qrCodeReturnUrl) {
            this.qrCodeReturnUrl = qrCodeReturnUrl;
        }
        public String getQrCodeReturnUrl() {
            return qrCodeReturnUrl;
        }

        public void setTempUserId(String tempUserId) {
            this.tempUserId = tempUserId;
        }
        public String getTempUserId() {
            return tempUserId;
        }

    }


    private int errcode;
    private Data data;
    private String message;
    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }
    public int getErrcode() {
        return errcode;
    }

    public void setData(Data data) {
        this.data = data;
    }
    public Data getData() {
        return data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

}



