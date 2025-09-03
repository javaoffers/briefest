package com.javaoffers.brief.modelhelper.fun.crud;

import com.javaoffers.brief.modelhelper.fun.ExecutFun;
import com.javaoffers.brief.modelhelper.fun.GetterFun;
import com.javaoffers.brief.modelhelper.fun.crud.impl.OnFunImpl;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OnFunTest {

    @Test
    public void testOnFunInterfaceInheritance() {
        assertFalse(ExecutFun.class.isAssignableFrom(OnFun.class),
            "OnFun should NOT extend ExecutFun");
    }
    
    @Test
    public void testOnFunImplDoesNotHaveExMethods() {
        Class<OnFunImpl> onFunImplClass = OnFunImpl.class;
        
        assertFalse(ExecutFun.class.isAssignableFrom(onFunImplClass),
            "OnFunImpl should NOT implement ExecutFun");
    }
    
    @Test
    public void testOnFunHasRequiredMethods() {
        Class<OnFun> onFunClass = OnFun.class;
        
        try {
            onFunClass.getMethod("oeq", GetterFun.class, GetterFun.class);
            onFunClass.getMethod("where");
            onFunClass.getMethod("stream", Consumer.class);

            assertTrue(true, "OnFun should have required methods");
        } catch (NoSuchMethodException e) {
            fail("OnFun should have required methods: " + e.getMessage());
        }
    }
    
    @Test
    public void testOnFunDoesNotHaveExMethods() {
        Class<OnFun> onFunClass = OnFun.class;
        
        try {
            onFunClass.getMethod("ex");
            fail("OnFun should NOT have ex() method");
        } catch (NoSuchMethodException e) {
            assertTrue(true, "OnFun should NOT have ex() method");
        }
        
        try {
            onFunClass.getMethod("exs");
            fail("OnFun should NOT have exs() method");
        } catch (NoSuchMethodException e) {
            assertTrue(true, "OnFun should NOT have exs() method");
        }
    }
}
