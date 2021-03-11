package os.arcadiadevs.playerservers.hubcore.utils;

import lombok.SneakyThrows;
import os.arcadiadevs.playerservers.hubcore.database.structures.PingInfoStructure;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PingUtil {

    @SneakyThrows
    public boolean isOnline(String address, String port) {
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<Boolean> future = executor.submit(() -> {

            boolean online = false;
            try {
                Socket s = new Socket(address, Integer.parseInt(port));
                s.close();
                online = true;
            } catch (IOException ignored) {
            }
            return online;
        });
        return future.get();
    }

    @SneakyThrows
    public PingInfoStructure getData(int port) {
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<PingInfoStructure> future = executor.submit(() -> {

            try {
                Socket sock = new Socket("127.0.0.1", port);

                DataOutputStream out = new DataOutputStream(sock.getOutputStream());
                DataInputStream in = new DataInputStream(sock.getInputStream());

                out.write(0xFE);

                int b;
                StringBuilder str = new StringBuilder();
                while ((b = in.read()) != -1) {
                    if (b > 16 && b != 255 && b != 23 && b != 24) {
                        str.append((char) b);
                    }
                }

                String[] data = str.toString().split("§");

                System.out.println(Arrays.toString(data));

                String serverMotd = data[0];
                int onlinePlayers = Integer.parseInt(data[1]);
                int maxPlayers = Integer.parseInt(data[2]);

                return new PingInfoStructure(onlinePlayers, maxPlayers, serverMotd);

            } catch (IOException e) {
                return null;
            }
        });
        return future.get();
    }
}
