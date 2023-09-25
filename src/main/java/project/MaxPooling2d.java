package project;
import mt.Image;

public class MaxPooling2d {

    protected int block_width = 0;
    protected int block_height = 0;
    protected int stride_width = 0;
    protected int stride_height = 0;
    protected String name = "MaxPooling2d";

    public MaxPooling2d(int block_width, int block_height, int stride_width, int stride_height) {

        this.block_width = block_width;
        this.block_height = block_height;
        this.stride_width = stride_width;
        this.stride_height = stride_height;
    }

    public Image apply(Image input) {
    /* your code here */
        var out_height = (int)(input.height() - block_height) / stride_height + 1;
        var out_width = (int)(input.width() - block_width) / stride_width + 1;
        Image out = new Image(out_width, out_height, "Maxpool");
        for (int y=0; y<out_height ;y++){
            for (int x=0; x<out_width ;x++){
                int startCol = y * stride_height;
                // System.out.println("startRow:"+startCol);
                int startRow = x * stride_width;
                // System.out.println("startCol:"+startRow);
                float maxVal = -9999999999.0f;

                for (int r = startRow; r < startRow + block_height; r++) {
                    for (int c = startCol; c < startCol + block_width; c++) {
                        // System.out.println("r:"+r);
                        // System.out.println("c:"+c);
                        maxVal = Math.max(maxVal, input.atIndex(r, c));
                        // System.out.println(maxVal);
                    }
                }

                out.setAtIndex(x, y, maxVal);

            }

        }
            
        return out;
    }
}
