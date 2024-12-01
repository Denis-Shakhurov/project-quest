package utils;

public class NamedRoutes {
    public static String startPath() {
        return "/";
    }

    public static String statisticPath() {
        return "/statistic";
    }

    public static String userPath(String id) {
        return "/users/" + id;
    }

    public static String userPath(Long id) {
        return userPath(String.valueOf(id));
    }

    public static String gamePath(String id) {
        return "/games/" + id;
    }

    public static String gamePath(Long id) {
        return gamePath(String.valueOf(id));
    }
}
