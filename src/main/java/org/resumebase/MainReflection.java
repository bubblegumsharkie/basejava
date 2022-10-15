package org.resumebase;

import org.resumebase.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException {
        Resume r = new Resume("Stub Name");
        Field declaredField = r.getClass().getDeclaredFields()[0];
        declaredField.setAccessible(true);
        System.out.println(Arrays.toString(r.getClass().getDeclaredMethods()));
        System.out.println(r.getClass().getDeclaredMethods()[2].invoke(r));

    }
}
