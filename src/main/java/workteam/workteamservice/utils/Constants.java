package workteam.workteamservice.utils;

public class Constants {
    public static class API {

        public static final String auth = "/auth";

        public static final String root = "/api";

        public static class User {
            public static final String root = API.root + "/user";
            public static final String userDetails = "/userDetails";
            public static final String colleagues = "/colleagues";
        }

        public static class Team {
            public static final String root = API.root + "/team";
            public static final String teams = "/teams";
        }
    }
}
