/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.readbio.readbs.pipeline;

import com.readbio.readbs.pipeline.OSValidator;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author xie186
 */
public class OSValidatorTester {
    private Object emptyDict;
    
    public OSValidatorTester() {
    }
    @Before
    public void setUp() {
        String String = "Windows";
    }
    

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testWin() {
        assertEquals("Testing operation system", true, OSValidator.isWindow());
    }
    
    
}
