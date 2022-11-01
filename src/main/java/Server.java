import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8484);) {
            System.out.println("Сервер запущен!");
            String currentCity = null;

            while (true) {
                try (Socket clientSocket = serverSocket.accept(); // ждем подключения
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {
                    String newCity;

                    if (currentCity == null) { //Проверка условия при первом поключении клиена
                        out.println("???");
                        newCity = in.readLine();
                        out.println("OK");
                        currentCity = newCity;
                        System.out.println("Текущее название города: " + currentCity);
                    } else { // проверка условия при подключении последующих клиентов
                        out.println(currentCity);
                        newCity = in.readLine();
                        if (currentCity.toLowerCase().charAt(currentCity.length() - 1) == newCity.toLowerCase().charAt(0)) { // сравнение и проверка города на повтор
                            currentCity = newCity;
                            out.println("OK");
                            System.out.println("Текущее название города: " + currentCity);
                        } else { // при поврорении
                            out.println("NOT OK");
                            System.out.println("Название города введено неверно, текущее название города: " + currentCity);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }
}