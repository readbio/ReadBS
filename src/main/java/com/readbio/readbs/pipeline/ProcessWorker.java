/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.readbio.readbs.pipeline;

import java.awt.List;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

/**
 *
 * @author xie186
 */
public class ProcessWorker extends SwingWorker<Boolean, String> {
    private final String[] commondLine;
    private JTextArea textArea;
    private JProgressBar jProgressBar;
    private JButton jButtonStart;
    private String orijButtonStartText;
    
    public ProcessWorker(String[] command, JTextArea textArea, JProgressBar jProgressBar){
        this.commondLine = command;
        this.textArea = textArea;
        this.jProgressBar = jProgressBar;    
    }
    
    public ProcessWorker(String[] command, JTextArea textArea, JProgressBar jProgressBar, JButton jButtonStart){
        this.commondLine = command;
        this.textArea = textArea;
        this.jProgressBar = jProgressBar;  
        this.jButtonStart = jButtonStart;
        this.orijButtonStartText = new String(jButtonStart.getText());
    }
    
    @Override
    protected Boolean doInBackground() throws Exception {
        setRunButton();
        ProcessBuilder pb = new ProcessBuilder(commondLine);
        jProgressBar.setIndeterminate(true);
        pb.redirectErrorStream(true); 
        Process p = pb.start();
        
        //Information of statistics
        InputStream inputStream = p.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        while((line = reader.readLine()) != null){
            //System.out.println(line);
            publish(line);
        }
        reader.close();
        
        p.waitFor();
        
        
        if (p.exitValue() == 0) {
            System.out.println("Command Successful");
            return true;
        } else {
            System.out.println("Command Failure");
            return false;
        }
        
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
            Logger.getLogger(ProcessWorker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(ProcessWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
       
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
