package com.Hibernate.Util;

import java.util.Map;
import java.util.Properties;

import org.hibernate.service.spi.Configurable;
import org.hibernate.service.jdbc.connections.internal.ProxoolConnectionProvider;

public class ConfigurableProxoolConnectionProvider extends
        ProxoolConnectionProvider implements Configurable {
    @Override
    public void configure(final Map configurationValues) {
        final Map<?,?> configuration = (Map<?,?>) configurationValues;
        final Properties properties = new Properties( );
        for ( final Map.Entry entry : configuration.entrySet() ) {
            properties.setProperty(
                String.valueOf( entry.getKey() ),
                String.valueOf( entry.getValue() ) );
        }       
        super.configure( properties );
    }
}
