/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.readbio.readbs.pipeline;

import java.awt.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

/**
 * Process multiple commands one by one. Commands can be passed using List<String[]>.
 * @author xie186
 */
public class ProcessWorkerMul extends SwingWorker<Boolean, String> {
    private final ArrayList<String[]> commondLines;
    private final JTextArea textArea;
    private final JProgressBar jProgressBar;
    private final JButton jButtonStart;
    private final String orijButtonStartText;
    
    /**
     * Constructor
     * @param commondLines ArrayList<String[]> of all commands need to be execeuted. 
     * @param textArea 
     * @param jProgressBar
     * @param jButtonStart
     */
    public ProcessWorkerMul(ArrayList<String[]> commondLines, JTextArea textArea, JProgressBar jProgressBar, JButton jButtonStart){
        this.commondLines = commondLines;
        this.textArea = textArea;
        this.jProgressBar = jProgressBar;  
        this.jButtonStart = jButtonStart;
        this.orijButtonStartText = jButtonStart.getText();
    }
    
    @Override
    protected Boolean doInBackground() throws Exception {
        setRunButton();
        return runCMD();
    }

    @Override
    protected void process(java.util.List<String> chunks) {
        jProgressBar.setIndeterminate(true);
        for(String str:chunks){
            textArea.append(str + "\n");
        }
    }
    
    @Override
    protected void done() {
        try {
            boolean status = get();  // get the return value of doInBackground()
            textArea.append("Completed with status:" + status + "!\n");
            jProgressBar.setIndeterminate(false);
            setReadyButton();
            System.out.println("Test:" + orijButtonStartText);
            JOptionPane.showMessageDialog(null, "Task Completed");
            
        } catch (InterruptedException ex) {
            Logger.getLogger(ProcessWorkerMul.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(ProcessWorkerMul.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }    
    
    public Boolean runCMD() throws IOException, InterruptedException{
        Boolean moniPexitValue = true; 
        for(String[] commondLine:commondLines){
            ProcessBuilder pb = new ProcessBuilder(commondLine);
            jProgressBar.setIndeterminate(true);
            pb.redirectErrorStream(true); 
            Process p = pb.start();
            //Information of statistics
            InputStream inputStream = p.getInputStream();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line = null;
                while((line = reader.readLine()) != null){
                    publish(line);
                }
            }
            
            p.waitFor();
            if (p.exitValue() == 0) {
                System.out.println("Command Successful: " +  Arrays.toString(commondLine));
            } else {
                moniPexitValue = false;
                System.out.println("Command Failure: " + Arrays.toString(commondLine));
            }
        }
        return moniPexitValue;
    }
    
    private void setRunButton(){
        jButtonStart.setText("Running");
        jButtonStart.setEnabled(false);
    }
    
    private void setReadyButton(){
        jButtonStart.setText(this.orijButtonStartText);
        jButtonStart.setEnabled(true);
    }
}