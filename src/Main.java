import java.io.File;

public class Main
{
    public static void main(String[] args)
    {
//        String srcFolder = "/users/sortedmap/Desktop/src";
//        String dstFolder = "/users/sortedmap/Desktop/dst";

        String srcFolder = "d:/src";
        String dstFolder = "d:/dst";

        int amountProcessors =  Runtime.getRuntime().availableProcessors();
        System.out.println("Количество ядер: " + amountProcessors);

        File srcDir = new File(srcFolder);

        long start = System.currentTimeMillis();

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



        System.out.println("Duration: " + (System.currentTimeMillis() - start));
    }
}
