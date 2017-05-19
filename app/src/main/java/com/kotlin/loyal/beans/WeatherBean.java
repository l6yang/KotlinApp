package com.kotlin.loyal.beans;

import java.io.Serializable;
import java.util.List;

public class WeatherBean implements Serializable {

    /**
     * desc : OK
     * status : 1000
     * data : {"wendu":"13","ganmao":"昼夜温差很大，易发生感冒，请注意适当增减衣服，加强自我防护避免感冒。","forecast":[{"fengxiang":"无持续风向","fengli":"微风级","high":"高温 17℃","type":"晴","low":"低温 -1℃","date":"15日星期三"},{"fengxiang":"无持续风向","fengli":"微风级","high":"高温 12℃","type":"多云","low":"低温 -3℃","date":"16日星期四"},{"fengxiang":"无持续风向","fengli":"微风级","high":"高温 12℃","type":"晴","low":"低温 -2℃","date":"17日星期五"},{"fengxiang":"无持续风向","fengli":"微风级","high":"高温 17℃","type":"晴","low":"低温 0℃","date":"18日星期六"},{"fengxiang":"无持续风向","fengli":"微风级","high":"高温 18℃","type":"晴","low":"低温 3℃","date":"19日星期天"}],"yesterday":{"fl":"微风","fx":"无持续风向","high":"高温 13℃","type":"晴","low":"低温 -4℃","date":"14日星期二"},"aqi":"71","city":"兰州"}
     */

    private String desc;
    private int status;
    private DataBean data;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * wendu : 13
         * ganmao : 昼夜温差很大，易发生感冒，请注意适当增减衣服，加强自我防护避免感冒。
         * forecast : [{"fengxiang":"无持续风向","fengli":"微风级","high":"高温 17℃","type":"晴","low":"低温 -1℃","date":"15日星期三"},{"fengxiang":"无持续风向","fengli":"微风级","high":"高温 12℃","type":"多云","low":"低温 -3℃","date":"16日星期四"},{"fengxiang":"无持续风向","fengli":"微风级","high":"高温 12℃","type":"晴","low":"低温 -2℃","date":"17日星期五"},{"fengxiang":"无持续风向","fengli":"微风级","high":"高温 17℃","type":"晴","low":"低温 0℃","date":"18日星期六"},{"fengxiang":"无持续风向","fengli":"微风级","high":"高温 18℃","type":"晴","low":"低温 3℃","date":"19日星期天"}]
         * yesterday : {"fl":"微风","fx":"无持续风向","high":"高温 13℃","type":"晴","low":"低温 -4℃","date":"14日星期二"}
         * aqi : 71
         * city : 兰州
         */

        private String wendu;
        private String ganmao;
        private String city;
        private List<ForecastBean> forecast;

        public String getWendu() {
            return wendu;
        }

        public void setWendu(String wendu) {
            this.wendu = wendu;
        }

        public String getGanmao() {
            return ganmao;
        }

        public void setGanmao(String ganmao) {
            this.ganmao = ganmao;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public List<ForecastBean> getForecast() {
            return forecast;
        }

        public void setForecast(List<ForecastBean> forecast) {
            this.forecast = forecast;
        }

        public static class ForecastBean {

            /**
             * fengxiang : 无持续风向
             * fengli : 微风级
             * high : 高温 17℃
             * type : 晴
             * low : 低温 -1℃
             * date : 15日星期三
             */

            private String fengxiang;
            private String fengli;
            private String high;
            private String type;
            private String low;
            private String date;

            public String getFengxiang() {
                return fengxiang;
            }

            public void setFengxiang(String fengxiang) {
                this.fengxiang = fengxiang;
            }

            public String getFengli() {
                return fengli;
            }

            public void setFengli(String fengli) {
                this.fengli = fengli;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            @Override
            public String toString() {
                return "{\"fengxiang\":" + (null == fengxiang ? null : "\"" + fengxiang + "\"") +
                        ",\"fengli\":" + (null == fengli ? null : "\"" + fengli + "\"") +
                        ",\"high\":" + (null == high ? null : "\"" + high + "\"") +
                        ",\"type\":" + (null == type ? null : "\"" + type + "\"") +
                        ",\"low\":" + (null == low ? null : "\"" + low + "\"") +
                        ",\"date\":" + (null == date ? null : "\"" + date + "\"") +
                        "}";
            }
        }

        @Override
        public String toString() {
            return "{\"wendu\":" + (null == wendu ? null : "\"" + wendu + "\"") +
                    ",\"ganmao\":" + (null == ganmao ? null : "\"" + ganmao + "\"") +
                    ",\"city\":" + (null == city ? null : "\"" + city + "\"") +
                    ",\"forecast\":" + forecast +
                    "}";
        }
    }

    @Override
    public String toString() {
        return "{\"desc\":" + (null == desc ? null : "\"" + desc + "\"") +
                ",\"status\":" + status +
                ",\"data\":" + data +
                "}";
    }
}
