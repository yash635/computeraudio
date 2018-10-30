/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * 
 Program Name: DFT

 Author Name: Yash Patel

 Course Title: Computer Audio

 Date: 29th October, 2018

 Assignment Number: 2

 Description of Program: 
 */
package dft;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yash
 */
class Fourier 
{ 
    public static double[] discreteFT(double[] fourier_data, int N) 
    {
        // Declare required variables
        double omega;
        int k, img, real, n;
        double X[] = new double[2 * N];
        
        //compute the omega that need in DFT equation
        omega = 2.0 * Math.PI / N;

        // Loop to compute the real and imaginary part of DFT
        for (k = 0; k < N; k++) 
        {
            real = 2 * k;
            img = 2 * k + 1;
            X[real] = 0.0;
            X[img] = 0.0;
            for (n = 0; n < N; ++n) 
            {
                X[real] = X[real] + fourier_data[2 * n] * Math.cos(omega * n * k) ;
                X[img] = X[img] + (-fourier_data[2 * n] * Math.sin(omega * n * k));
            }
            X[real] /= N;
            X[img] /= N;
        }
        return X;
    }
    
}
public class DFT {
    public static void main(String args[]) throws IOException {
        // Declaring variables for Size of DFT and time interval
        int N = 512;        
        double T = 2.0;
        double fk, Fs = 44100;
        
        //reading  amplitude values from the file
        BufferedReader in = new BufferedReader(new FileReader("D:\\Assignment-2\\src\\spectrum.txt"));
        List<String> lines = new ArrayList<String>();
        String line;
        while(( line = in.readLine()) != null) {
                lines.add(line);
        }
        String temp[] = new String[lines.size()];
        temp= lines.toArray(temp);
        double temp1[] = new double[temp.length];
        for(int a=0;a<temp.length;a++) 
        {
            temp1[a]= Double.parseDouble(temp[a]);
            double b =  (temp1[a]/20);
            temp1[a] = Math.pow(10,b);
        }
        NumberFormat formatter = new DecimalFormat("#0.00000000000000");
        double fourier_data[] = new double[2* N];
        double after[] = new double[2* N];
        double freq[] = new double[2* N];
        for (int i = 0; i < N-1; ++i) {
            fourier_data[2 * i] = temp1[i];
            fourier_data[2 * i + 1] = 0.0;
        }
        double X[] = Fourier.discreteFT(fourier_data, N);
        
        /* Loop to print the real and imaginery part of DFT,
           Calculate the square root of sum of squares,
           Calculate the frequency
        
        */
        for (int k = 0; k < N; ++k) {
            fk = k / T;
        //    System.out.println("------------------------------------------------\n ");
        //    System.out.println("f["+k+"] = "+fk+"|| Xr["+k+"] = "+formatter.format(X[2*k])+ "    Xi["+k+"] = "+formatter.format(X[2*k + 1])) ;
            after[k] = Math.sqrt(((X[2*k] * X[2*k])+ (X[2*k +1] * X[2*k +1])));
        //    System.out.println("\nSUm of square of root: " + formatter.format(after[k]));
            freq[k] = (after[k] * Fs) / N;
            System.out.println("freq[" + k + "]: " + freq[k]);
        }        
    }
}
