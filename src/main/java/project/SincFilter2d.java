package project;

import mt.Image;
import mt.LinearImageFilter;
import org.apache.commons.math3.analysis.function.Sinc;

public class SincFilter2d extends LinearImageFilter{
    float downScale;
    public SincFilter2d(int filterSize, float downScale) {
        
        super(filterSize, filterSize, "Sinc2d (" + filterSize + ", " + downScale + ")");

        var s = new Sinc(true);
        this.downScale = downScale;

        // normalize();
    }
    public void apply(Image image, Image result){
        for (int y = 0; y < result.height(); ++y) {
            for (int x = 0; x < result.width(); ++x) {
                float val = 0;
    			for (int yPrime = -minIndexY(); yPrime < maxIndexY(); ++yPrime) {
                        var s = new Sinc(true);
                        float interval = image.atIndex(x, yPrime)/this.downScale;
                        val = (float)s.value(interval);
                        result.setAtIndex(x, y, val);
					
                }
                for (int xPrime = -minIndexX(); xPrime < maxIndexX(); ++xPrime) {
                        var s = new Sinc(true);
                        float interval = image.atIndex(xPrime, y)/this.downScale;
                        val = (float)s.value(interval);
                        result.setAtIndex(x, y, val);
                }
                // var s = new Sinc(true);
                // float interval = image.atIndex(x, y)/this.downScale;
                // float val = (float)s.value(interval);
                // result.setAtIndex(x, y, val);
                System.out.println(result.atIndex(x, y));
            }
        }
           // public void normalize(){
	// 	double sum = sum();
	// 	for (int i = 0; i < buffer.length; i++) {
	// 		buffer[i] /= sum;
	// 	}
    // }
    } 
}
// }

// public class SincFilter2d extends LinearImageFilter{

//     public SincFilter2d(int filterSize, float downScale) {

//         super(filterSize, filterSize, "Sinc2d (" + filterSize + ", " + downScale + ")");
//         // this.downScale = downScale;
//         // var s = new Sinc(true);
    
//         /* your code here, get inspiration in exercise 4 if you don't remember */
//         // @Override
//         public void apply(Image image, Image result){
//             for (int y = 0; y < result.height(); ++y) {
//                 for (int x = 0; x < result.width(); ++x) {
//                     float sum = 54;
//                     result.setAtIndex(x, y, image.atIndex(x, y));
//                     System.out.println(result.atIndex(x, y));
//             }
//         }
//         // normalize();
//     } 

//     // public void normalize(){
// 	// 	double sum = sum();
// 	// 	for (int i = 0; i < buffer.length; i++) {
// 	// 		buffer[i] /= sum;
// 	// 	}
//     // }
// }

