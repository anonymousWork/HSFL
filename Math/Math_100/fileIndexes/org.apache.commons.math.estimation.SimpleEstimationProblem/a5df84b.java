/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.commons.math.estimation;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Simple implementation of the {@link EstimationProblem
 * EstimationProblem} interface for boilerplate data handling.
 * <p>This class <em>only</em> handles parameters and measurements
 * storage and unbound parameters filtering. It does not compute
 * anything by itself. It should either be used with measurements
 * implementation that are smart enough to know about the
 * various parameters in order to compute the partial derivatives
 * appropriately. Since the problem-specific logic is mainly related to
 * the various measurements models, the simplest way to use this class
 * is by extending it and using one internal class extending
 * {@link WeightedMeasurement WeightedMeasurement} for each measurement
 * type. The instances of the internal classes would have access to the
 * various parameters and their current estimate.</p>
 */
public class SimpleEstimationProblem implements EstimationProblem {

    /**
     * Build an empty instance without parameters nor measurements.
     */
    public SimpleEstimationProblem() {
        parameters   = new ArrayList();
        measurements = new ArrayList();
    }

    public EstimatedParameter[] getAllParameters() {
        return (EstimatedParameter[]) parameters.toArray(new EstimatedParameter[parameters.size()]);
    }

    public EstimatedParameter[] getUnboundParameters() {

        // filter the unbound parameters
        ArrayList unbound = new ArrayList(parameters.size());
        for (Iterator iterator = parameters.iterator(); iterator.hasNext();) {
            EstimatedParameter p = (EstimatedParameter) iterator.next();
            if (! p.isBound()) {
                unbound.add(p);
            }
        }

        // convert to an array
        return (EstimatedParameter[]) unbound.toArray(new EstimatedParameter[unbound.size()]);
        
    }

    public WeightedMeasurement[] getMeasurements() {
        return (WeightedMeasurement[]) measurements.toArray(new WeightedMeasurement[measurements.size()]);
    }

    protected void addParameter(EstimatedParameter p) {
        parameters.add(p);
    }

    /**
     * Add a new measurement to the set.
     * @param m measurement to add
     */
    protected void addMeasurement(WeightedMeasurement m) {
        measurements.add(m);
    }

    /** Estimated parameters. */
    private ArrayList parameters;

    /** Measurements. */
    private ArrayList measurements;

}
