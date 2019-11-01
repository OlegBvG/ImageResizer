import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

public  class ImageResizerQ {

    private File file;
    private long start;
    private String dstFolder;

    public ImageResizerQ(File fileN, String dstFolder, long startQ) {

        this.file = fileN;
        this.dstFolder = dstFolder;
        this.start = startQ;

        try {

                BufferedImage image = ImageIO.read(file);

                int newWidth = 300;
                int newHeight = (int) Math.round(
                        image.getHeight() / (image.getWidth() / (double) newWidth)
                );

                newWidth *= 2;
                newHeight *= 2;

                BufferedImage newImage = new BufferedImage(
                        newWidth, newHeight, BufferedImage.TYPE_INT_RGB
                );


                int widthStep = image.getWidth() / newWidth;
                int heightStep = image.getHeight() / newHeight;

                for (int x = 0; x < newWidth; x++) {
                    for (int y = 0; y < newHeight; y++) {
                        int rgb = image.getRGB(x * widthStep, y * heightStep);
                        newImage.setRGB(x, y, rgb);
                    }
                }


                AffineTransform transform = AffineTransform.getScaleInstance(0.5, 0.5);
//                    AffineTransformOp op = new AffineTransformOp(transform, (AffineTransformOp.TYPE_BILINEAR) );
//                    AffineTransformOp op = new AffineTransformOp(transform, (AffineTransformOp.TYPE_BICUBIC) );
                AffineTransformOp op = new AffineTransformOp(transform, (AffineTransformOp.TYPE_NEAREST_NEIGHBOR) );

                newImage = op.filter(newImage, null);

                File newFile = new File(dstFolder + "/" + file.getName());
                ImageIO.write(newImage, "jpg", newFile);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Duration: " + Thread.currentThread().getName() + " => " + (System.currentTimeMillis() - start));
    }
}