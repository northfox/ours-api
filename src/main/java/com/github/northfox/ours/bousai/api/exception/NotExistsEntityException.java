package com.github.northfox.ours.bousai.api.exception;


public class NotExistsEntityException extends ApplicationException {

    public static enum Entities {
        PROJECT("プロジェクト"),
        STATUS("ステータス"),
        TODO("Todo"),
        VTODO("Todo");

        private String whatOfMessage;

        private Entities(String whatOfMessage) {
            this.whatOfMessage = whatOfMessage;
        }

        public String getWhatOfMessage() {
            return whatOfMessage;
        }
    }

    public NotExistsEntityException(Entities entity, Integer projectId) {
        super(generateMessage(entity, projectId));
    }

    private static String generateMessage(Entities entity, Integer id) {
        return String.format("[%s]には、指定されたID(%s)を持つデータが存在しません。", entity.getWhatOfMessage(), id);
    }
}
