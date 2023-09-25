package project;

import mt.Image;
import mt.Signal;
import java.util.Objects;

import org.apache.commons.lang.ArrayUtils;

public class ComplexSignal extends Signal{
    protected mt.Signal real;    //Image object to store real part
    protected mt.Signal imag;    //Image object to store imaginary part
    protected int size;
    protected String name;      //Name of the image
    public ComplexSignal(int length, String name) {
        super(length,name);
        if (isPowerOfTwo(length)){
            this.size = length;
            this.real = new Signal(this.getSize(), name);
            this.imag = new Signal(this.getSize(), name);
            this.name = name;
        }
        else{
            throw new RuntimeException("Size must be power off 2!");
        }
    }

    public ComplexSignal(float[] signalReal, float[] signalImag, String name) {
        super(ArrayUtils.addAll(signalReal, signalImag),name);
        this.real = new Signal(signalReal, name);
        this.imag = new Signal(signalImag, name);
        this.name = name;
    }
    public static boolean isPowerOfTwo(int n)
    {
        if (n == 0)
            return false;

        while (n != 1) {
            if (n % 2 != 0)
                return false;
            n = n / 2;
        }
        return true;
    }

    public void generateSine(int numWaves){
//        var numWaves = 5;
//        this.real = new Signal(this.getSize(), name);
//        this.imag = new Signal(this.getSize(), name);
        var result = new mt.SineWave(1 * numWaves, this.getSize())
                .plus(new mt.SineWave(2 * numWaves, this.getSize()).times(-1.0f/2.0f))
                .plus(new mt.SineWave(3 * numWaves, this.getSize()).times(+1.0f/3.0f))
                .plus(new mt.SineWave(4 * numWaves, this.getSize()).times(-1.0f/4.0f))
                .plus(new mt.SineWave(5 * numWaves, this.getSize()).times(+1.0f/5.0f));
        for (int y = 0; y < result.size(); y++) {
            this.real.setAtIndex(y, result.buffer()[y]);
            this.imag.setAtIndex(y,0);
        }
    }
    private Signal calculateMagnitude(){
        Signal mag = new Signal(getSize(), "Magnitude");

        for (int y = 0; y < getSize(); y++) {
                double mag_val = Math.sqrt(Math.pow(this.real.atIndex(y), 2) + Math.pow(this.imag.atIndex(y), 2));
                mag.setAtIndex(y, (float) mag_val);
            }
        return mag;
        }
    private Signal swap(Signal input) {
            int swap_point = input.size() / 2 ;
            Signal result = new Signal(input.size(), "Reversed");
            for (int i = 0; i < swap_point; i++) {
                result.setAtIndex(i, input.atIndex(i+swap_point));
                result.setAtIndex(i+swap_point, input.atIndex(i));
            }
            return result;
    }
    public void fftShift1d(){
        Signal shifted_real = swap(this.real);
        Signal shifted_imag = swap(this.imag);
        for (int i = 0; i < shifted_real.size(); i++) {
            this.real.setAtIndex(i, shifted_real.atIndex(i));
        }
        for (int i = 0; i < shifted_imag.size(); i++) {
            this.imag.setAtIndex(i, shifted_imag.atIndex(i));
        }
    }

    public float[] getReal(){
        return this.real.buffer();
    } // get the buffer of the real
    public float[] getImag() {
        return this.imag.buffer();
    }// get the buffer of the imag
    public String getName(){
        return this.name;
    }
    public int getSize(){
        return this.size;
    }
    public Signal getMagnitude(){
        return this.calculateMagnitude();
    }
}
