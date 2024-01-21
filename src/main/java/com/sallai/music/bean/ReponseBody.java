package com.sallai.music.bean;

//旧版云之声的现已废弃
@Deprecated
public class ReponseBody {

    public final static String CHAT_OPEN = "{\"code\":\"ANSWER\",\"originIntent\":{\"nluSlotInfos\":[]},\"history\":\"cn.yunzhisheng.chat\",\"source\":\"nlu\",\"uniCarRet\":{\"result\":{},\"returnCode\":609,\"message\":\"aios-home.hivoice.cn\"},\"asr_recongize\":\"打开夜灯。\",\"rc\":0,\"general\":{\"style\":\"faq\",\"text\":\"已开启\",\"type\":\"T\"},\"returnCode\":0,\"audioUrl\":\"http://192.168.199.124:3333/trafficRouter/r/GN3WZW\",\"retTag\":\"nlu\",\"service\":\"cn.yunzhisheng.chat\",\"nluProcessTime\":\"121\",\"text\":\"打开夜灯\",\"responseId\":\"d8d34946b4e64c6090df66b4e923e324\"}";
    public final static String CHAT_ClOSE = "{\"code\":\"ANSWER\",\"originIntent\":{\"nluSlotInfos\":[]},\"history\":\"cn.yunzhisheng.chat\",\"source\":\"nlu\",\"uniCarRet\":{\"result\":{},\"returnCode\":609,\"message\":\"aios-home.hivoice.cn\"},\"asr_recongize\":\"关闭夜灯。\",\"rc\":0,\"general\":{\"style\":\"faq\",\"text\":\"已关闭\",\"type\":\"T\"},\"returnCode\":0,\"audioUrl\":\"http://192.168.199.124:3333/trafficRouter/r/GN3WZW\",\"retTag\":\"nlu\",\"service\":\"cn.yunzhisheng.chat\",\"nluProcessTime\":\"121\",\"text\":\"关闭夜灯\",\"responseId\":\"d8d34946b4e64c6090df66b4e923e324\"}";
    public final static String CHAT_OK = "{\"code\":\"ANSWER\",\"originIntent\":{\"nluSlotInfos\":[]},\"history\":\"cn.yunzhisheng.chat\",\"source\":\"nlu\",\"uniCarRet\":{\"result\":{},\"returnCode\":609,\"message\":\"aios-home.hivoice.cn\"},\"asr_recongize\":\"完成。\",\"rc\":0,\"general\":{\"style\":\"faq\",\"text\":\"好\",\"type\":\"T\"},\"returnCode\":0,\"audioUrl\":\"http://192.168.199.124:3333/trafficRouter/r/GN3WZW\",\"retTag\":\"nlu\",\"service\":\"cn.yunzhisheng.chat\",\"nluProcessTime\":\"121\",\"text\":\"完成操作\",\"responseId\":\"d8d34946b4e64c6090df66b4e923e324\"}";

    public static String speak(String text){
        return  "{\"code\":\"ANSWER\",\"originIntent\":{\"nluSlotInfos\":[]},\"history\":\"cn.yunzhisheng.chat\",\"source\":\"nlu\",\"uniCarRet\":{\"result\":{},\"returnCode\":609,\"message\":\"aios-home.hivoice.cn\"},\"asr_recongize\":\"完成。\",\"rc\":0,\"general\":{\"style\":\"faq\",\"text\":\""+text+"\",\"type\":\"T\"},\"returnCode\":0,\"audioUrl\":\"http://192.168.199.124:3333/trafficRouter/r/GN3WZW\",\"retTag\":\"nlu\",\"service\":\"cn.yunzhisheng.chat\",\"nluProcessTime\":\"121\",\"text\":\"完成操作\",\"responseId\":\"d8d34946b4e64c6090df66b4e923e324\"}";
    }

    public static String Audio(){
        return "{\"semantic\":{\"intent\":{\"language\":\"zh\",\"tag\":\"经典\",\"keyword\":\"中文 经典\"}},\"code\":\"SEARCH_CATEGORY\",\"data\":{\"result\":{\"total\":\"1000\",\"totalTime\":417,\"pagesize\":\"30\",\"errorCode\":0,\"page\":\"1\",\"source\":1,\"dataSourceName\":\"喜马拉雅\",\"playlist\":\"\"}},\"originIntent\":{\"nluSlotInfos\":[]},\"history\":\"cn.yunzhisheng.audio\",\"source\":\"nlu\",\"uniCarRet\":{\"result\":{},\"returnCode\":609,\"message\":\"aios-home.hivoice.cn\"},\"asr_recongize\":\"华语经典。\",\"rc\":0,\"general\":{\"actionAble\":\"true\",\"quitDialog\":\"true\",\"text\":\"为您播放经典\", \"type\":\"T\"},\"returnCode\":0,\"audioUrl\":\"http:1127.0.0.1:3333/trafficRouter/rlwXKKy8\",\"retTag\":\"nlu\",\"service\":\"cn.yunzhisheng.audio\",\"nluProcessTime\":\"486\",\"text\":\"华语经典\",\"responseld\":\"1c095630470549e8b29e025a1ac98ee7\"}";
    }

    public static String MyBroadCast(String data){
//        data = "\"data\":{\"controls\":[],\"data\":[{\"provider\":{\"name\":\"小雅os\"},\"stream\":{\"linkFlag\":0,\"url\":\"http://live.xmcdn.com/live/328/64.m3u8\"},\"author\":\"\",\"imageUrl\":\"http://imagev2.xmcdn.com/group78/M07/A6/A1/wKgO4F6EVv7gP1QhAABQuiUyuzo039.png!op_type=3&columns=60&rows=60\",\"title\":\"中国之声\",\"token\":\"1065\"}],\"style\":{\"bgImageShow\":1,\"subTitleShow\":1,\"barShow\":1,\"lyricShow\":1,\"titleShow\":1,\"authorShow\":1,\"playShow\":1,\"providerShow\":1,\"stopShow\":1,\"imagePositionShow\":1,\"nextShow\":1,\"prevShow\":1,\"controlsShow\":1,\"imageShow\":1},\"templateCode\":\"VI-DEFAULT\",\"token\":\"fafce653e0b047b19a9739513a95e691\"},";
        return "{\"semantic\":{\"intent\":{\"station\":\"辽宁广播电台\",\"channelType\":\"radio\"}},\"code\":\"SEARCH\","+data+" \"originIntent\":{\"nluSlotInfos\":[]},\"history\":\"cn.yunzhisheng.broadcast\",\"source\":\"nlu\",\"uniCarRet\":{\"result\":{},\"returnCode\":609,\"message\":\"aios-home.hivoice.cn\"},\"asr_recongize\":\"辽宁广播电台。\",\"rc\":0,\"returnCode\":0,\"audioUrl\":\"http://127.0.0.1:3333/trafficRouter/r/jpeD78\",\"retTag\":\"nlu\",\"service\":\"cn.yunzhisheng.broadcast\",\"nluProcessTime\":\"146\",\"text\":\"辽宁广播电台\",\"responseId\":\"1f7d5241a5e64e48be4820af94358ebf\"}";
    }

}
