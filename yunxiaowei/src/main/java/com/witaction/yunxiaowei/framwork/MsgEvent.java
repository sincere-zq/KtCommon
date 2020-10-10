package com.witaction.yunxiaowei.framwork;

public interface MsgEvent {
    class RefreshHomeDataEvent {
        private boolean refresh;

        public RefreshHomeDataEvent(boolean refresh) {
            this.refresh = refresh;
        }

        public boolean isRefresh() {
            return refresh;
        }
    }

    class UpdateUserInfoEvent {
        private boolean updated;

        public UpdateUserInfoEvent(boolean updated) {
            this.updated = updated;
        }

        public boolean isUpdated() {
            return updated;
        }
    }
}
