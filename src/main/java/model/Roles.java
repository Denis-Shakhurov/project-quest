package model;

import io.javalin.security.RouteRole;

public enum Roles implements RouteRole {
    ADMIN,
    GUEST,
    USER
}
