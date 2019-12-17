package com.test.vendor.postgres;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import com.jsql.model.InjectionModel;
import com.jsql.model.exception.JSqlException;
import com.jsql.view.terminal.SystemOutTerminal;

@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.CONCURRENT)
public class PostgresBlindGetTestSuite extends ConcretePostgresTestSuite {

    @Override
    public void setupInjection() throws Exception {
        
        InjectionModel model = new InjectionModel();
        this.injectionModel = model;

        model.addObserver(new SystemOutTerminal());

        model.getMediatorUtils().getParameterUtil().initQueryString("http://localhost:8080/greeting-blind");
        model.getMediatorUtils().getParameterUtil().initRequest("");
        model.getMediatorUtils().getParameterUtil().setQueryString(Arrays.asList(
            new SimpleEntry<>("tenant", "postgres"),
            new SimpleEntry<>("name", "1'")
        ));
        
        model.getMediatorUtils().getConnectionUtil().setMethodInjection(model.QUERY);
        model.getMediatorUtils().getConnectionUtil().setTypeRequest("GET");
        
        model.setIsScanning(true);
        model.setStrategy(model.BLIND);
        model.mediatorVendor.setVendorByUser(model.mediatorVendor.POSTGRESQL);
        model.beginInjection();
    }
    
    @Ignore
    @Override
    @Test
    public void listDatabases() throws JSqlException {
        LOGGER.info("Ignore: too slow");
    }
    
    @Ignore
    @Override
    @Test
    public void listTables() throws JSqlException {
        LOGGER.info("Ignore: too slow");
    }
    
    @Ignore
    @Override
    @Test
    public void listColumns() throws JSqlException {
        LOGGER.info("Ignore: too slow");
    }
    
}
