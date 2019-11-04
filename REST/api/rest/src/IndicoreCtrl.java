package com.fxcm.btutil;

import java.util.List;

public interface IndicoreCtrl {
    public void addIndicator(String name, byte[] data) throws Exception;
    public List<String> getIndicators() throws Exception;
    public void deleteIndicator(String id) throws Exception;

    public void addStrategy(String name, byte[] data) throws Exception;
    public List<String> getStrategies() throws Exception;
    public void deleteStrategy(String id) throws Exception;
}