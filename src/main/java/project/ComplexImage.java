package project;
import mt.Image;

// import net.imagej.ops.Ops.Math;
import java.lang.Math;

import org.apache.commons.lang.ArrayUtils;


public class ComplexImage extends Image{
    protected mt.Image real; //= new Image(getWidth(), getHeight(), getName());    //Image object to store real part
    protected mt.Image imag; //= new Image(getWidth(), getHeight(), getName());    //Image object to store imaginary part
    protected String name;      //Name of the image
    protected int width;
    protected int height;

    public ComplexImage (int width, int height, String name){
        // System.out.printf("Execution here at CIC 1 \n");
        super(width, height, name);
        this.real = new Image(width, height, name);
        this.imag = new Image(width, height, name);
        this.width = width;
        this.height = height;
        this.name = name;
        // System.out.printf(String.valueOf(this.height));
    }

    public ComplexImage(int width, int height, String name, float[] bufferReal, float[] bufferImag) {
        // System.out.printf("Execution here at CIC 2 \n");
        super(width, height, name, ArrayUtils.addAll(bufferReal, bufferImag));
        this.real = new Image(width, height, name, bufferReal);
        this.imag = new Image(width, height, name, bufferImag);
        this.height = height;
        this.width = width;
    }
    // public ComplexImage(int width, int height, String name, float[] bufferReal, float[] bufferImag, int inputWidth, int inputHeight){
    //     super(width, height, name);
    //     this.real = new Image(width, height, name, bufferReal);
    //     this.imag = new Image(width, height, name, bufferImag);
    //     this.height = height;
    //     this.width = width;
    //     this.real.setBufferFromCenterArea(width, height, bufferReal, inputWidth, inputHeight);
    //     this.imag.setBufferFromCenterArea(width, height, bufferImag, inputWidth, inputHeight);
    // }

    public ComplexImage(int width, int height, String name, Image real , Image imag, int inputWidth, int inputHeight){
        super(width, height, name);
        this.real = new Image(width, height, name);
        this.imag = new Image(width, height, name);
        this.height = height;
        this.width = width;
        this.real = real.setBufferFromCenterArea(width, height, inputWidth, inputHeight);
        this.imag = imag.setBufferFromCenterArea(width, height, inputWidth, inputHeight);
    }
    private Image calculateMagnitude(boolean logFlag){
        Image mag = new Image(getWidth(), getHeight(), "Magnitude");

        for (int y = 0; y < mag.height(); y++) {
            for (int x = 0; x < mag.width(); x++) {
                double mag_val = Math.sqrt(Math.pow(real.atIndex(x, y), 2) + Math.pow(imag.atIndex(x, y), 2));
                if (logFlag) {
                    mag.setAtIndex(x, y, (float) Math.log(mag_val));
                }
                else{
                    mag.setAtIndex(x, y, (float) mag_val);
                }
            }
        }
        return mag;
    }
    private Image calculatePhase(){
        Image phase = new Image(getWidth(), getHeight(), "Phase");
        for (int y = 0; y < phase.height(); y++) {
            for (int x = 0; x < phase.width(); x++) {
                double phase_val = Math.atan2(imag.atIndex(x,y), real.atIndex(x,y));
                phase.setAtIndex(x, y, (float) phase_val);
            }
        }
        return phase;
    }
    public void fftShift(){
        this.real = swapQuadrants(this.real);
        this.imag = swapQuadrants(this.imag);
    }
    private Image swapQuadrants(Image input){
        Image output = new Image(input.width(), input.height(), input.name());
        int swap_point_x = input.width() / 2 ;
        int swap_point_y = input.height() / 2 ;
        for (int y = 0; y < swap_point_y; y++) {
            for (int x = 0; x < swap_point_x; x++) {
                output.setAtIndex(x,y, input.atIndex(x+swap_point_x,y+swap_point_y));
                output.setAtIndex(x+swap_point_x, y+swap_point_y,input.atIndex(x,y));
                }
            }
        for (int y = 0; y < swap_point_y; y++) {
            for (int x = swap_point_x; x < input.width(); x++) {
                output.setAtIndex(x,y, input.atIndex(x-swap_point_x,y+swap_point_y));
//                if (y+swap_point_y > 256 ){System.out.printf((y)+"\n");}

                output.setAtIndex(x-swap_point_x, y+swap_point_y,input.atIndex(x,y));
                }
            }
        return output;
    }
    public void setOuterToZero(int lines, int axis){
        if(axis==1){
            for (int x=0; x < getWidth(); x++){
                for (int y = 0; y <= lines; y++) {
                    // System.out.printf((x)+"\n");
                    // System.out.printf((y)+"\n");
                    this.real.setAtIndex(x, y, 0);
                    this.imag.setAtIndex(x, y, 0);
                }
            
                for (int y = getHeight()-1;y > (getWidth()-lines); y--) {
                    // System.out.printf((x)+"\n");
                    // System.out.printf((y)+"\n");
                    this.real.setAtIndex(x, y, 0);
                    this.imag.setAtIndex(x, y, 0);
                }
            }  
        }
        else if(axis==0){
            for (int y=0; y < getHeight(); y++){
                for (int x = 0; x <= lines; x++) {
                    // System.out.printf((x)+"\n");
                    // System.out.printf((y)+"\n");
                    this.real.setAtIndex(x, y, 0);
                    this.imag.setAtIndex(x, y, 0);
                }
            
                for (int x = getWidth()-1;x > (getWidth()-lines); x--) {
                    // System.out.printf((x)+"\n");
                    // System.out.printf((y)+"\n");
                    this.real.setAtIndex(x, y, 0);
                    this.imag.setAtIndex(x, y, 0);
                }
            }                
                       
        }        
    }



    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public String getName() {
        return name;
    }
    public float[] getMagnitude(){
        return this.calculateMagnitude(false).buffer();
    }
    public float[] getLogMagnitude(){
        return this.calculateMagnitude(true).buffer();
    }
    public float[] getPhase(){
        return this.calculatePhase().buffer();
    }
}
