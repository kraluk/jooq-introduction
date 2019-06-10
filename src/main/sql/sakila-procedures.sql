DROP ALIAS IF EXISTS SHOW_LATEST_ACTOR;

CREATE ALIAS SHOW_LATEST_ACTOR AS $$
String showLatestActor(java.sql.Connection conn) throws Exception {
    String resultValue = null;

    java.sql.ResultSet rs = conn.createStatement().executeQuery(
    " SELECT concat(first_name, ' ', last_name) FROM actor WHERE actor_id = (SELECT MAX(actor_id) FROM actor) ");

    while(rs.next()) {
        resultValue = rs.getString(1);
    }

    return resultValue;
}
$$;

DROP ALIAS IF EXISTS SHOW_VERSION;

CREATE ALIAS SHOW_VERSION FOR "org.h2.engine.Constants.getVersion"
