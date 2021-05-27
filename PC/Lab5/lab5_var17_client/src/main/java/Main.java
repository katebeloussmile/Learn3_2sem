import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Locale;

public class Main {

    public static void main(String[] args) throws IOException {
        Socket clientSocket = new Socket("localhost", 1337);
        List<MenuItem> menuItemsList;
        //initialize input output streams
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

        ObjectMapper objectMapper = new ObjectMapper();
        //get and read menu sent by a server
        String menuItems = in.readLine();
        menuItemsList = objectMapper.readValue(menuItems, new TypeReference<List<MenuItem>>(){});
        //print menu
        System.out.println("Available menu items: ");
        for (MenuItem item: menuItemsList) {
            System.out.printf("%d: %s%n", item.Id, item.Name);
        }
        while(true){
            //write order
            System.out.print("Input meal number: ");
            out.write(reader.readLine() + "\n");
            out.flush();
        }
    }
}
