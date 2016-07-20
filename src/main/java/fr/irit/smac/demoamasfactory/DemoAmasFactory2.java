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
package fr.irit.smac.demoamasfactory;

import java.io.IOException;
import java.util.logging.Logger;

import fr.irit.smac.demoamasfactory.impl.DemoFactory;
import fr.irit.smac.demoamasfactory.infrastructure.IDemoInfrastructure;
import fr.irit.smac.demoamasfactory.service.IMyServices;

public class DemoAmasFactory2 {

    static final Logger LOGGER = Logger.getLogger(DemoAmasFactory2.class.getName());

    private DemoAmasFactory2() {

    }

    public static <T extends IMyServices<A>, A> void main(String[] args) throws IOException {

        DemoFactory<T, A> demoFactory = new DemoFactory<>();

        IDemoInfrastructure<T> infra = demoFactory.createInfrastructure(
            ClassLoader.getSystemResourceAsStream("config2.json"));

        infra.getServices().getExecutionService().step();
        infra.getServices().getExecutionService().step();
        infra.getServices().getExecutionService().step();
        infra.getServices().getExecutionService().displaySimpleGui();
    }
}