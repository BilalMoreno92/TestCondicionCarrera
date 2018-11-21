import java.util.concurrent.atomic.AtomicInteger;

class Contador {
    public static volatile int cuenta = 0;
}

class Sumador extends Thread {

    public void run() {
        for (int i = 0; i < 1000; i++)
            Contador.cuenta++;
    }
}
class Restador extends Thread {

    public void run() {
        for (int i = 0; i < 1000; i++)
            Contador.cuenta--;
    }
}

class ContadorAtomico {
    public static AtomicInteger cuenta = new AtomicInteger(0);
}

class SumadorAtomico extends Thread {
    public void run( ) {
        for (int i = 0; i < 1000; i++) {
            ContadorAtomico.cuenta.incrementAndGet();
        }
    }
}

class RestadorAtomico extends Thread {
    public void run( ) {
        for (int i = 0; i < 1000; i++) {
            ContadorAtomico.cuenta.decrementAndGet();
        }
    }
}

public class TestCondicionCarrera {

    public static void main(String[] args) throws InterruptedException {

        Sumador s1 = new Sumador();
        Restador r1 = new Restador();
        r1.start();
        s1.start();

        SumadorAtomico s2 = new SumadorAtomico();
        RestadorAtomico r2 = new RestadorAtomico();
        s2.start();
        r2.start();

        s1.join();
        r1.join();
        s2.join();
        r2.join();

        System.out.println("Contador no atómico " + Contador.cuenta);
        System.out.println("Contador atómico " + ContadorAtomico.cuenta);
    }

}