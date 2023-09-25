package project;

public class LinearComplexImageFilter{
    public SincFilter2d aFilter2d;
    public LinearComplexImageFilter(SincFilter2d real_f){
        this.aFilter2d = real_f;
    }

    public ComplexImage apply(ComplexImage input){
        ComplexImage output = new ComplexImage(input.width(), input.height(), input.name());
        input.real.show();
        output.real = aFilter2d.apply(input.real);
        output.real.show();
        output.imag = aFilter2d.apply(input.imag);
        return output;
    }
}
