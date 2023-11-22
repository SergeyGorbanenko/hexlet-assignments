package exercise.util;

public class NamedRoutes {

    public static String rootPath() {
        return "/";
    }

    // BEGIN
    public static String postsPath() {
        return "/posts";
    }

    public static String postPath(Long id) {
        return postPath(String.valueOf(id));
    }

    public static String postPath(String id) {
        return "/posts/" + id;
    }

    public static String postsPageablePath(Long pageNumber) {
        return postsPageablePath(String.valueOf(pageNumber));
    }

    public static String postsPageablePath(String pageNumber) {
        return "/posts?page=" + pageNumber;
    }
    // END
}
