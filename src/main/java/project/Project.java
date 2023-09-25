/*
 * Copyright (C) 2022 Bruno Riemenschneider <bruno.riemenschneider@fau.de>; Zhengguo Tan <zhengguo.tan@fau.de>; Jinho Kim <jinho.kim@fau.de>
 *
 * Distributed under terms of the GPLv3 license.
 */
package project;
import mt.Image;
import mt.SharpeningFilter2d;

import java.util.Arrays;
import mt.LinearImageFilter;
import lme.DisplayUtils;
public class Project {
    public static void main(String[] args) {
        (new ij.ImageJ()).exitWhenQuitting(true);
        ComplexImage kSpace = ProjectHelpers.LoadKSpace("kdata.h5");

        /* Implement your code based on the project description */

        /* kSpace visualisation*/  

      //  DisplayUtils.showImage(kSpace.real.buffer(),"Real Part",kSpace.real.width());
      //  DisplayUtils.showImage(kSpace.imag.buffer(),"Imaginary Part",kSpace.imag.width());
      //  DisplayUtils.showImage(kSpace.getMagnitude(),"Magnitude",kSpace.getWidth());
      //  DisplayUtils.showImage(kSpace.getLogMagnitude(),"Magnitude-Log",kSpace.getWidth());
      //  DisplayUtils.showImage(kSpace.getPhase(),"Phase",kSpace.getWidth());


       /* 1D FFT Shift*/  

      //  ComplexSignal complex1d = new ComplexSignal(256,"Sample Complex Signal");
      //  complex1d.generateSine(3);
      //  complex1d.getMagnitude().show();
      //  ComplexSignal FFT = ProjectHelpers.FFT1D(complex1d);
      //  FFT.fftShift1d();
      //  FFT.getMagnitude().show();
      //  FFT.fftShift1d();
      //  FFT.getMagnitude().show();


       /* MR Reconstruction and back to kSpace*/  

        // kSpace.fftShift();
        // ComplexImage ifft_2d1 = ProjectHelpers.InverseFFT2D(kSpace);
        // ifft_2d1.fftShift();
        // DisplayUtils.showImage(ifft_2d1.getMagnitude(),"Magnitude",ifft_2d1.getWidth());
        // DisplayUtils.showImage(ifft_2d1.getPhase(),"Phase",ifft_2d1.getWidth());
        // DisplayUtils.showImage(ifft_2d1.real.buffer(),"Real Part",ifft_2d1.real.width());
        // DisplayUtils.showImage(ifft_2d1.imag.buffer(),"Imaginary Part",ifft_2d1.imag.width());
        // ifft_2d1.fftShift();
        // ComplexImage fft_2d = ProjectHelpers.FFT2D(ifft_2d1);
        // fft_2d.fftShift();
        // DisplayUtils.showImage(fft_2d.getLogMagnitude(),"LogMag",kSpace.real.width());
        // DisplayUtils.showImage(fft_2d.getPhase(),"Phase",kSpace.real.width());


        /* Filters */

        // Sinc

         //   kSpace.fftShift();
         //   ComplexImage ifft_2d = ProjectHelpers.InverseFFT2D(kSpace);
         //   ifft_2d.fftShift();
        //    ComplexImage mrImage = ifft_2d;
        //    LinearImageFilter linFilter = new LinearImageFilter(2,2,"aaaa");
         //   SincFilter2d realFilter = new SincFilter2d(31, 4.0f);
         //   var complexFilter = new LinearComplexImageFilter(realFilter);
         //   ComplexImage filteredImage = complexFilter.apply(kSpace);

        // Image linfil = realFilter.apply(kSpace.real);
        // linfil.show();
         //   DisplayUtils.showImage(filteredImage.getLogMagnitude(),"Mag",kSpace.real.width());



        //Set Outer to Zero
        // kSpace.setOuterToZero(124, 0);
        // kSpace.setOuterToZero(124,1);
        // DisplayUtils.showImage(kSpace.getLogMagnitude(),"LogMag",kSpace.real.width());
        // kSpace.fftShift();
        // ComplexImage ifft_2d = ProjectHelpers.InverseFFT2D(kSpace);
        // ifft_2d.fftShift();
        // DisplayUtils.showImage(ifft_2d.getMagnitude(),"Magnitude 124",ifft_2d.getWidth());


        // Crop kSpace

        // ComplexImage cropped = new ComplexImage(128,128,"cropeed", 
        // kSpace.real, kSpace.imag,kSpace.getHeight(), kSpace.getWidth());
        // DisplayUtils.showImage(cropped.getLogMagnitude(),"LogMag cropeed kSpace",cropped.getWidth());
        // cropped.fftShift();
        // ComplexImage ifft_2d_crp = ProjectHelpers.InverseFFT2D(cropped);
        // ifft_2d_crp.fftShift();
        // DisplayUtils.showImage(ifft_2d_crp.getMagnitude()," Reconstructioncropeed kSpace",ifft_2d_crp.getWidth());

        // Maxpool

        kSpace.fftShift();
        ComplexImage ifft_2d = ProjectHelpers.InverseFFT2D(kSpace);
        ifft_2d.fftShift();
        DisplayUtils.showImage(ifft_2d.getMagnitude(),"Magnitude",ifft_2d.getWidth());
        ComplexImage mrImage = ifft_2d;
        float[] mag = mrImage.getMagnitude();
        Image mrMagImage = new Image(mrImage.getWidth(), mrImage.getHeight(), "magnitude of mrImage");
        mrMagImage.setBuffer(mag);
        MaxPooling2d mp = new MaxPooling2d(4, 4, 4, 4);
        Image mrMagImage_MP = mp.apply(mrMagImage);
        mrMagImage_MP.show();

    }
}
