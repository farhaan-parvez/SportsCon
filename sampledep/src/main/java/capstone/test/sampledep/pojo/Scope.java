package capstone.test.sampledep.pojo;

import java.util.Objects;

public enum Scope {

        PUBLIC("public", 0),
        PRIVATE("private", 1);

        private final String key;
        private final Integer value;

        Scope(String key, Integer value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }
        public Integer getValue() {
            return value;
        }

        public static Scope findScopeByKey(String key) throws Exception{
            for(Scope scope : Scope.values()) {
                if (Objects.equals(key, scope.getKey()))
                    return scope;
            }
            throw new Exception("No scope with key : " + key);
        }
}
