import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosophers {
    private static final int NUM_PHILOSOPHERS = 5;
    private static final int NUM_EATS = 3;

    public static void main(String[] args) {
        Philosopher[] philosophers = new Philosopher[NUM_PHILOSOPHERS];

        // Создание вилок для философов
        Object[] forks = new Object[NUM_PHILOSOPHERS];
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            forks[i] = new Object();
        }

        // Создание и запуск потоков философов
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            Object leftFork = forks[i];
            Object rightFork = forks[(i + 1) % NUM_PHILOSOPHERS];
            philosophers[i] = new Philosopher(i, leftFork, rightFork);
            Thread thread = new Thread(philosophers[i]);
            thread.start();
        }
    }

    static class Philosopher implements Runnable {
        private final int id;
        private final Object leftFork;
        private final Object rightFork;
        private int eatsCount;

        public Philosopher(int id, Object leftFork, Object rightFork) {
            this.id = id;
            this.leftFork = leftFork;
            this.rightFork = rightFork;
            this.eatsCount = 0;
        }

        @Override
        public void run() {
            try {
                while (eatsCount < NUM_EATS) {
                    // Размышления философа
                    think();
                    // Берет вилки
                    pickupForks();
                    // Ест
                    eat();
                    // Освобождение вилок
                    putdownForks();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        private void think() throws InterruptedException {
            System.out.println("Философ " + id + " размышляет.");
            Thread.sleep((long) (Math.random() * 1000));
        }

        private void pickupForks() throws InterruptedException {
            synchronized (leftFork) {
                System.out.println("Философ " + id + " взял левую вилку.");
                synchronized (rightFork) {
                    System.out.println("Философ " + id + " взял правую вилку.");
                }
            }
        }

        private void eat() throws InterruptedException {
            System.out.println("Философ " + id + " ест.");
            Thread.sleep((long) (Math.random() * 1000));
            eatsCount++;
        }

        private void putdownForks() throws InterruptedException {
            System.out.println("Философ " + id + " положил вилки на стол.");
        }
    }
}
