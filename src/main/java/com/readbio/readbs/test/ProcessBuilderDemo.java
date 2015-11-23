/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.readbio.readbs.test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 *
 * @author Shaojun
 */
public class ProcessBuilderDemo {
    public ProcessBuilderDemo(String s) throws IOException, InterruptedException {
        runPWD(s);
    }

    public void runPWD(String s) throws IOException, InterruptedException{
        ProcessBuilder ps = new ProcessBuilder(s);
        Process prSelexaQA = ps.start();
        prSelexaQA.waitFor() ;
        if (prSelexaQA.exitValue() == 0) {
            System.out.println("Command Successful");
        } else {
            System.out.println("Command Failure");
        }
        getProcessOutput(prSelexaQA);
        getProcessInput(prSelexaQA);
    }

    private void getProcessOutput(Process p){
        System.out.println("OutputStream");
        OutputStream outputStream = p.getOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        printStream.println();
        printStream.flush();
        printStream.close();
        System.out.println("");
    }

    private void getProcessInput(Process p) throws IOException{
        System.out.println("InputStream");
        InputStream inputStream = p.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        while((line = reader.readLine()) != null){
            System.out.println(line);
        }
        reader.close();
        System.out.println("");
    }

    private void getProcessErr(Process p) throws IOException{
        System.out.println("ErrorStream");
        InputStream inputStream = p.getErrorStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        while((line = reader.readLine()) != null){
            System.out.println(line);
        }
        reader.close();
        System.out.println("");
    }

    public static void main(String[] args) throws IOException, InterruptedException{
        ProcessBuilderDemo processBuilderDemo = new ProcessBuilderDemo(args[0]);
    }
}
