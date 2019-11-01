import java.io.File;
import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Main
{
    public static void main(String[] args) throws InterruptedException {
//        String srcFolder = "/users/sortedmap/Desktop/src";
//        String dstFolder = "/users/sortedmap/Desktop/dst";

        String srcFolder = "d:/src";
        String dstFolder = "d:/dst";

        int amountProcessors =  Runtime.getRuntime().availableProcessors();
        System.out.println("Количество ядер: " + amountProcessors);

        File srcDir = new File(srcFolder);

        long start = System.currentTimeMillis();

        System.out.println("\nResize in amount of Processors Threads");

        File[] files = srcDir.listFiles();
        int amountFiles = files.length;
        int middle = amountFiles/amountProcessors;

        for (int i = 0; i < amountProcessors-1; i++){
            File[] filesN = new File[middle];
            System.arraycopy(files, i*middle, filesN, 0, filesN.length );

            new  ImageResizer(filesN, dstFolder,  start).start();
        }
        File[] filesN = new File[amountFiles-middle*(amountProcessors-1)];
        System.arraycopy(files, middle*(amountProcessors-1), filesN, 0, filesN.length );
        new  ImageResizer(filesN, dstFolder,  start).start();


        System.out.println("Duration: " + Thread.currentThread().getName() + " => " + (System.currentTimeMillis() - start));
        System.out.println("Wait for 15 seconds in Thread-main");
        Thread.currentThread().join(15000);

        System.out.println("\nResize with Executor");
        Executor exec = Executors.newFixedThreadPool(amountProcessors);

        ConcurrentLinkedQueue<File> queue = null;
        queue = new ConcurrentLinkedQueue<File>(Arrays.asList(srcDir.listFiles()));
        long startQ = System.currentTimeMillis();

        new ResizeExecution(queue, dstFolder, startQ, amountProcessors);

    }
}

