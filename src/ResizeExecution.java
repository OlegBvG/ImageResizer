import java.io.File;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ResizeExecution {

    private ConcurrentLinkedQueue<File> queue;
    private long start;
    private String dstFolder;
    int NTHREADS;

    public ResizeExecution(ConcurrentLinkedQueue<File> queue, String dstFolder, long startQ, int NTHREADS) {

        this.queue = queue;
        this.dstFolder = dstFolder;
        this.start = startQ;
        this.NTHREADS = NTHREADS;
        ExecutorService exec = Executors.newFixedThreadPool(NTHREADS);
        while (queue.size()>0){
            File fQueue = queue.poll();
            Runnable r = () -> new ImageResizerQ(fQueue, dstFolder, startQ);
        exec.execute(r);
        }
        exec.shutdown();
    }
}
