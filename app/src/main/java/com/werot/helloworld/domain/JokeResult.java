package com.werot.helloworld.domain;

import java.util.List;

public class JokeResult {
    List<Joke> result;

    public List<Joke> getResult() {
        return result;
    }

    public void setResult(List<Joke> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "JokeResult{" +
                "result=" + result +
                '}';
    }

    public static class  Joke{
        private String _id;
        private String hashId;
        private int unixtime;
        private String updatetime;

        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public String toString() {
            return "Joke{" +
                    "_id='" + _id + '\'' +
                    ", hashId='" + hashId + '\'' +
                    ", unixtime=" + unixtime +
                    ", updatetime='" + updatetime + '\'' +
                    ", content='" + content + '\'' +
                    '}';
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getHashId() {
            return hashId;
        }

        public void setHashId(String hashId) {
            this.hashId = hashId;
        }

        public int getUnixtime() {
            return unixtime;
        }

        public void setUnixtime(int unixtime) {
            this.unixtime = unixtime;
        }

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }
    }
}
