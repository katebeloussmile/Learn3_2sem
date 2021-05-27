package com.parallel_labs;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Server
public class Main {
    static final int port = 1337;
    static final List<MenuItem> menuItems
            = Arrays.asList(
            new MenuItem(1, "Cheeseburger"),
            new MenuItem(2, "Big Mac menu"),
            new MenuItem(3, "Ice cream"));
    static final List<MenuItem> selectedItems = new ArrayList<MenuItem>();
    public static void main(String[] args) throws IOException {
        //initialize socket
        ServerSocket serverSocket = new ServerSocket(port);
        //accept client connection
        Socket clientSocket = serverSocket.accept();
        //initialize input and output streams
        BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        //put menu items to output stream (to send over to client)
        String serializedMenuItems = toString(menuItems) + "\n";
        out.write(serializedMenuItems);
        out.flush();
        while(true){
            //read request from client with id of item in menu and menu item name
            int mealId = Integer.parseInt(input.readLine());
            MenuItem menuItem = menuItems.stream().filter(x -> x.Id == mealId)
                    .findFirst().orElse(null);
            selectedItems.add(menuItem);
            saveToFile(selectedItems, "lab5_var17.json");
        }
    }

    private static void writeLineToStream(BufferedWriter stream, String s){
        try {
            stream.write(s + "\n");
            stream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveToFile(List<MenuItem> menuItems, String fileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new FileOutputStream(fileName), menuItems);
    }

    private static String toString(List<MenuItem> menuItems) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        StringWriter stringWriter = new StringWriter();
        mapper.writeValue(stringWriter, menuItems);
        return stringWriter.toString();
    }

}
