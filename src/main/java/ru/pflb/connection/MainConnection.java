package ru.pflb.connection;

import ru.pflb.mq.dummy.exception.DummyException;
import ru.pflb.mq.dummy.implementation.ConnectionImpl;
import ru.pflb.mq.dummy.interfaces.Destination;
import ru.pflb.mq.dummy.interfaces.Producer;
import ru.pflb.mq.dummy.interfaces.Session;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainConnection {
    public static void main(String[] args) {
        List <String> list = new ArrayList<>();
        list.add("Раз");
        list.add("Два");
        list.add("Три");

        try(ConnectionImpl connection = new ConnectionImpl();
            //создадим сессию
            Session session = connection.createSession(true)){

            connection.start();

        //место назначения
            Destination destination = session.createDestination("myQueue");
        //очередь
            Producer producer = session.createProducer(destination);
            Iterator<String> iterator = list.iterator();
            while (iterator.hasNext()){
                Thread.sleep(2000);
                producer.send(iterator.next());
            }

        }
        catch (DummyException | InterruptedException e){
            System.out.println("что то не работает");
            e.printStackTrace();
        }

    }
}
