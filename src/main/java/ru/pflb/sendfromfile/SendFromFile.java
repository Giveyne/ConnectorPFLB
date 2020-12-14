package ru.pflb.sendfromfile;

import ru.pflb.mq.dummy.exception.DummyException;
import ru.pflb.mq.dummy.implementation.ConnectionImpl;
import ru.pflb.mq.dummy.interfaces.Destination;
import ru.pflb.mq.dummy.interfaces.Producer;
import ru.pflb.mq.dummy.interfaces.Session;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SendFromFile {

    protected static BufferedReader getReader(String str) throws IOException {
        return new BufferedReader(new FileReader(str));
    }

    public static void main(String[] args){
        String path = "";
        if(args.length !=0) {
            path = args[0];
        }

        String message;
        BufferedReader bufferedReader;

        try(ConnectionImpl connection = new ConnectionImpl();
            //создадим сессию
            Session session = connection.createSession(true)){
            connection.start();

            Destination destination = session.createDestination("myQueue");
            //очередь
            Producer producer = session.createProducer(destination);

            while (true) {
                bufferedReader = getReader(path);
                while ((message = bufferedReader.readLine()) != null) {
                    Thread.sleep(2000);
                    producer.send(message);
                }
                bufferedReader.close();
            }
        }
        catch (DummyException | InterruptedException e){
            System.out.println("что то не работает");
            e.printStackTrace();
        }
        catch (IOException e){
            System.out.println("Нет такого файла!");
        }

    }
}
