import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        try (Socket clientSocket = new Socket("127.0.0.1", 8484);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); // записываем данные
        ) {
            String currentCity = in.readLine(); // считываем название города с сервера
            if (currentCity.equals("???")) {
                System.out.println("Введите название города");
            } else {
                System.out.println("Текущее название города: " + currentCity);
                System.out.println("Введите название города начинающееся на букву: " + currentCity.toLowerCase().charAt(currentCity.length() - 1));
            }
            Scanner scanner = new Scanner(System.in);
            String newCity = scanner.nextLine();//вводим название города
            out.println(newCity); // выводим введеное название города
            currentCity = in.readLine(); // считываем название города

            if (currentCity.equals("OK")) {
                // TODO: 01.11.2022 Проверяем отправленный город начинался на букву на которую заканчивался последний город
                System.out.println("Ваше название принято");
            } else if (currentCity.equals("NOT OK")) {
                // TODO: 01.11.2022 Город назван неверно
                System.out.println("Вы ввели неверное название");
            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }
}