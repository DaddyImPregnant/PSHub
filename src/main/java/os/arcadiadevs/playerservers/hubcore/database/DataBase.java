package os.arcadiadevs.playerservers.hubcore.database;

import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import os.arcadiadevs.playerservers.hubcore.PSHubCore;
import os.arcadiadevs.playerservers.hubcore.database.structures.DBInfoStructure;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DataBase {

    @SneakyThrows
    public boolean containsPort(String port) {
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<Boolean> future = executor.submit(() -> {

            try (Connection connection = DataSource.getConnection()) {
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM PLAYERSERVERS");
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    if (rs.getString("PORT").equals(port))
                        return true;
                }
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        });
        return future.get();
    }

    @SneakyThrows
    public ArrayList<DBInfoStructure> getServersInfo() {

        ExecutorService executor = Executors.newCachedThreadPool();
        Future<ArrayList<DBInfoStructure> > future = executor.submit(() -> {
            ArrayList<DBInfoStructure> output = new ArrayList<>();

            try (Connection connection = DataSource.getConnection()) {
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM PLAYERSERVERS");
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    output.add(new DBInfoStructure(
                            rs.getString("UUID"),
                            rs.getString("SERVERID"),
                            rs.getString("PORT"),
                            rs.getString("NAME"),
                            rs.getString("PLAYERNAME"))
                    );
                }
                return output;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        });
        return future.get();
    }

    @SneakyThrows
    public String getPortByUUID(String UUID) {

        ExecutorService executor = Executors.newCachedThreadPool();
        Future<String> future = executor.submit(() -> {

            try (Connection connection = DataSource.getConnection()) {
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM PLAYERSERVERS WHERE UUID='" + UUID + "'");
                ResultSet rs = stmt.executeQuery();
                rs.next();
                return rs.getString("PORT");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        });
        return future.get();
    }

    public String getServerByUUID(String UUID) {
        try (Connection connection = DataSource.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM PLAYERSERVERS WHERE UUID='" + UUID + "'");
            ResultSet rs = stmt.executeQuery(); rs.next();
            return rs.getString("SERVERID");
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
