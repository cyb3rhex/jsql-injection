package com.test.preferences;

import com.jsql.model.InjectionModel;
import com.jsql.model.exception.JSqlException;
import com.jsql.view.terminal.SystemOutTerminal;
import com.test.vendor.mysql.ConcreteMySqlTestSuite;
import org.junitpioneer.jupiter.RetryingTest;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;

public class CheckAllHeaderTestSuite extends ConcreteMySqlTestSuite {

    @Override
    public void setupInjection() throws Exception {
        
        InjectionModel model = new InjectionModel();
        this.injectionModel = model;

        model.subscribe(new SystemOutTerminal());

        model.getMediatorUtils().getParameterUtil().initializeQueryString("http://localhost:8080/header?tenant=mysql");
        model.getMediatorUtils().getParameterUtil().setListHeader(Arrays.asList(
            new SimpleEntry<>("fake1", ""),
            new SimpleEntry<>("name", ""),
            new SimpleEntry<>("fake2", "")
        ));
        
        model.setIsScanning(true);
        
        model
        .getMediatorUtils()
        .getPreferencesUtil()
        .withCheckingAllHeaderParam();
        
        model
        .getMediatorUtils()
        .getConnectionUtil()
        .setMethodInjection(model.getMediatorMethod().getHeader());
        
        model.beginInjection();
    }
    
    @Override
    @RetryingTest(3)
    public void listDatabases() throws JSqlException {
        super.listDatabases();
    }
}
