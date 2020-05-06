package xyz.theasylum.showmeme.storage.domain;

public class Constants {
    public class SecurityConstants {
        public static final String SECRET = "internalJWTOnly";
        public static final long EXPIRATION_TIME = 864_000_000; // 10 days
        public static final String TOKEN_PREFIX = "Bearer ";
        public static final String HEADER_STRING = "Authorization";
    }
}
