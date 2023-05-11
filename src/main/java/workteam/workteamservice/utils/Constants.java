package workteam.workteamservice.utils;

public class Constants {
    public static class API {
        public static final String root = "/api";
        public static final String auth = "/auth";

        public static class User {
            public static final String root = API.root + "/user";
            public static final String userDetails = "/userDetails";
        }
    }

    public static class SECURITY {
        public static String auth = API.auth;
        public static String userDetails = API.root + API.User.root + API.User.userDetails;
    }
}
