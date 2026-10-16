public class ChickenOrEgg {

    // Флаг: кто произнёс последнее слово (true — курица, false — яйцо)
    private static volatile boolean lastWasChicken = false;

    public static void main(String[] args) {
        // Поток «Курица»
        Thread chicken = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("Курица");
                lastWasChicken = true;
                // Небольшая пауза, чтобы потоки чередовались
                try { Thread.sleep(50); } catch (InterruptedException e) { }
            }
        });

        // Поток «Яйцо»
        Thread egg = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("Яйцо");
                lastWasChicken = false;
                try { Thread.sleep(50); } catch (InterruptedException e) { }
            }
        });

        // Запускаем оба потока
        chicken.start();
        egg.start();

        // Ждём завершения обоих потоков
        try {
            chicken.join();
            egg.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Проверяем, кто из потоков ещё жив (на всякий случай)
        if (chicken.isAlive() || egg.isAlive()) {
            System.out.println("Спор не окончен — какой-то поток ещё работает.");
            return;
        }

        // Определяем победителя по последнему сказанному слову
        System.out.println("Спор окончен.  Победила " + (lastWasChicken ? "Курица" : "Яйцо"));
    }
}