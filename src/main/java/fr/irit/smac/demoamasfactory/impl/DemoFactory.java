/*
 * #%L
 * demo-amas-factory
 * %%
 * Copyright (C) 2016 IRIT - SMAC Team and Brennus Analytics
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */
package fr.irit.smac.demoamasfactory.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import fr.irit.smac.amasfactory.impl.AmasFactory;
import fr.irit.smac.demoamasfactory.infrastructure.IDemoInfrastructure;
import fr.irit.smac.demoamasfactory.infrastructure.impl.DemoInfrastructure;
import fr.irit.smac.demoamasfactory.service.IMyServices;

public class DemoFactory<T extends IMyServices<A>,A> extends AmasFactory<T,A> {

    static final Logger LOGGER = Logger.getLogger(DemoFactory.class.getName());

    @Override
    public IDemoInfrastructure<T> createInfrastructure(
        InputStream configuration) {

        LOGGER.info("Begin of the initialization of the infrastructure");
        IDemoInfrastructure<T> infrastructure = null;
        try {
            infrastructure = (IDemoInfrastructure<T>) super.createInfrastructure(configuration);
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        LOGGER.info("End of the initialization of the infrastructure");

        return (IDemoInfrastructure<T>) infrastructure;
    }

    @Override
    public IDemoInfrastructure<T> createInfrastructure() {
        return new DemoInfrastructure<>();
    }

}